package com.example.android.aroma;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroma.Model.IngredientModel;
import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.example.android.aroma.Utils.IngredientsCustomAdapter;

import java.util.ArrayList;

public class IngredientsUploadActivity extends AppCompatActivity {

    private static final String TAG = "IngredientsActivity";


    private Button editingredients;
    private TextView selectedIngredients;
    private Button editingredientsValue;
    private TextView selectedIngredientsValue;
    private ListView listView;


    boolean[] checkedItems;
    String[] ingredients;
    ArrayList<Integer> selectedItems =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_upload);
        editingredients=(Button) findViewById(R.id.ingredients);
        editingredientsValue=(Button) findViewById(R.id.ingredientsValue);
        selectedIngredients=(TextView) findViewById(R.id.selectedIngredients);
        selectedIngredientsValue=(TextView) findViewById(R.id.IngredientListValue);
        ingredients=CreateCategoryHashMap.createCategoryList();
        checkedItems=new boolean[ingredients.length];

        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(IngredientsUploadActivity.this, CategoryAndTime.class);
                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(IngredientsUploadActivity.this, DescriptionUpload.class);
                startActivity(intent);
            }
        });

        editingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(IngredientsUploadActivity.this);
                mBuilder.setTitle("Categories:");

                mBuilder.setMultiChoiceItems(ingredients, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked) {

                                if (!selectedItems.contains(i)) {
                                    selectedItems.add(i);
                                } else {
                                    selectedItems.remove(i);
                                }

                        }
                        else
                        {
                            selectedItems.remove(i);
                        }

                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String item="";
                        for(int k=0;k<selectedItems.size();k++)
                        {
                            item=item+ ingredients[selectedItems.get(k)];
                            if(k!=(selectedItems.size()-1))
                            {
                                item=item+",";
                            }
                        }
                        selectedIngredients.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {

                        for(int i=0;i<checkedItems.length;i++)
                        {
                            checkedItems[i]=false;
                            selectedItems.clear();
                            selectedIngredients.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        editingredientsValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<IngredientModel> ingredientList=new ArrayList<>();
                for(int i=0;i<selectedItems.size();i++)
                {
                    IngredientModel ingredientItem = new IngredientModel();
                    ingredientItem.setName(ingredients[selectedItems.get(i)]);
                    ingredientItem.setMeasure("gms");
                    ingredientItem.setQuantity("100");
                    ingredientList.add(ingredientItem);

                }


                AlertDialog.Builder mBuilder = new AlertDialog.Builder(IngredientsUploadActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.ingredient_list_view, null);
                mBuilder.setView(convertView);
                mBuilder.setTitle("Ingredient Details");

                //ListView lv = (ListView) convertView.findViewById(R.id.ingredient_listview);
                listView=(ListView) convertView.findViewById(R.id.ingredient_listview);

                IngredientsCustomAdapter adapter=new IngredientsCustomAdapter(IngredientsUploadActivity.this,ingredientList);
                listView.setAdapter(adapter);
                mBuilder.show();
            }
        });
    }


}
