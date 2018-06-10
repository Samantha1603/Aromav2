package com.example.android.aroma;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroma.Model.IngredientModel;
import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.example.android.aroma.Utils.IngredientsCustomAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientsUploadActivity extends AppCompatActivity {

    private static final String TAG = "IngredientsActivity";


    private Button editingredients;
    private TextView selectedIngredients;
    private Button editingredientsValue;
    private TextView selectedIngredientsValue;
    private EditText ingredientsSelectedTab;
    private ListView listView;


    boolean[] checkedItems;
    String[] ingredients;
    HashMap<Integer,String> selectedItems =new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_upload);
        editingredients=(Button) findViewById(R.id.ingredients);
        //editingredientsValue=(Button) findViewById(R.id.ingredientsValue);
        selectedIngredients=(TextView) findViewById(R.id.selectedIngredients);
      //  ingredientsSelectedTab=(EditText) findViewById(R.id.ingredientsSelectedTab);
        //selectedIngredientsValue=(TextView) findViewById(R.id.IngredientListValue);
        ingredients=CreateCategoryHashMap.createCategoryList();
        checkedItems=new boolean[ingredients.length];

        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(IngredientsUploadActivity.this,CategoryAndTime.class);
                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listView.getAdapter().getCount()==0)
                {
                    Log.d(TAG,"next screen"+selectedIngredients.getText());
                    Toast.makeText(IngredientsUploadActivity.this,"Select at least one ingredient",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(IngredientsUploadActivity.this, DescriptionUpload.class);
                    Intent intentOld = getIntent();
                    if (intentOld.hasExtra(getString(R.string.selected_image))) {
                        String imgUrl;
                        imgUrl = intentOld.getStringExtra(getString(R.string.selected_image));
                        intent.putExtra("selected_image", imgUrl);

                    } else if (intentOld.hasExtra(getString(R.string.selected_bitmap))) {
                        Bitmap bitmap;
                        bitmap = intentOld.getParcelableExtra(getString(R.string.selected_bitmap));
                        intent.putExtra("selected_bitmap", bitmap);
                    }
                    intent.putExtra("Title", intentOld.getStringExtra("Title"));
                    intent.putExtra("Category", intentOld.getStringExtra("Category"));
                    intent.putExtra("Time Duration", intentOld.getStringExtra("Time Duration"));
                    intent.putExtra("Servings", intentOld.getStringExtra("Servings"));
             //       intent.putExtra("Ingredients", ingredientsSelectedTab.getText().toString());

                    for (int u=0;u<listView.getAdapter().getCount();u++)
                    {

                        Log.d(TAG, "onClick: "+listView.getAdapter().getItem(u).toString());

                    }


                    startActivity(intent);
                }
            }
        });
        final ArrayList<IngredientModel> ingredientList=new ArrayList<>();

        editingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder=new AlertDialog.Builder(IngredientsUploadActivity.this);
                mBuilder.setTitle("Ingredients:");


                mBuilder.setMultiChoiceItems(ingredients, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked) {

                                if (!selectedItems.containsKey(i)) {
                                    selectedItems.put(i,ingredients[i]);
                                   } else {
                                    selectedItems.remove(i);
                                }
                                try {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(IngredientsUploadActivity.this);
                                    // Get the layout inflater
                                    LayoutInflater inflater = IngredientsUploadActivity.this.getLayoutInflater();
                                    final View dialogView = inflater.inflate(R.layout.ingredient_list_item, null);
                                    // Inflate and set the layout for the dialog
                                    // Pass null as the parent view because its going in the dialog layout
                                    TextView title = (TextView) dialogView.findViewById(R.id.ingredientName);
                                    title.setText(selectedItems.get(i));

                                    final int selectedI = i;
                                    builder.setView(dialogView)
                                            // Add action buttons
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // sign in the user ...
                                                    EditText val = (EditText) dialogView.findViewById(R.id.ingredientValue);
                                                    EditText measure = (EditText) dialogView.findViewById(R.id.ingredientMeasure);
                                                    //   TextView title=(TextView) dialogView.findViewById(R.id.ingredientName);

                                                    IngredientModel ingredientItem = new IngredientModel();
                                                    ingredientItem.setName(selectedItems.get(selectedI));
                                                    ingredientItem.setQuantity(val.getText().toString());
                                                    ingredientItem.setMeasure(measure.getText().toString());
                                                    ingredientList.add(ingredientItem);
                                                }
                                            })
                                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //LoginDialogFragment.this.getDialog().cancel();
                                                    selectedItems.remove(selectedI);
                                                    checkedItems[selectedI] = false;
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    System.out.print("MESSAGE:"+e.getMessage());
                                    System.out.print("CAUSE"+e.getCause());

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

//                        LayoutInflater inflater = getLayoutInflater();
//                        View convertView = (View) inflater.inflate(R.layout.activity_ingredients_upload, null);

                        //ListView lv = (ListView) convertView.findViewById(R.id.ingredient_listview);
                        listView=(ListView) findViewById(R.id.ingredient_listview);

                        IngredientsCustomAdapter adapter=new IngredientsCustomAdapter(IngredientsUploadActivity.this,R.layout.activity_ingredients_upload,ingredientList);
                        listView.setAdapter(adapter);
                        listView.setScrollContainer(false);
                        Log.d("LIST VIEW","List view added successfully");
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

      /*  editingredientsValue.setOnClickListener(new View.OnClickListener() {
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
                        selectedIngredientsValue.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                AlertDialog mDialog = mBuilder.create();




                mBuilder.show();
            }
        });*/
    }


}
