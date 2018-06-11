package com.example.android.aroma;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CategoryAndTime extends AppCompatActivity {

    private static final String TAG = "CategoryAndTime";


    private Button editCategories;
    private TextView selectedICategories;
    private ArrayList<Integer> selectedCategoryIds=new ArrayList<>();
    private EditText timeDuration;

    private EditText serves;

    boolean[] checkedItems;
    String[] categoryList;
    HashMap<String,Integer> categoryHashMAp=new HashMap<>();
    HashMap<Integer,Integer> selectedItems =new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_and_time);
        editCategories=(Button) findViewById(R.id.category);
        selectedICategories=(TextView) findViewById(R.id.selectedCategories);
        categoryList=CreateCategoryHashMap.createCategoryList();
        categoryHashMAp=CreateCategoryHashMap.createCategoryHashMapList();

        checkedItems=new boolean[categoryList.length];

        timeDuration=(EditText) findViewById(R.id.timeDuration);
        serves=(EditText) findViewById(R.id.serves);
        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);
        final Intent intentOld = getIntent();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(CategoryAndTime.this,UploadImageActivity.class);
                Intent oldIntent=getIntent();
                intent.putExtra("user",oldIntent.getSerializableExtra("user"));

                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedICategories.getText().toString().trim().length()<=0 || selectedICategories.getText()==null)
                {
                    Log.d(TAG,"next screen"+selectedICategories.getText());
                    Toast.makeText(CategoryAndTime.this,"Select at least one category",Toast.LENGTH_LONG).show();
                }
                else if(timeDuration.getText().toString().trim().length()<=0 || timeDuration.getText()==null)
                {
                    Log.d(TAG,"next screen"+selectedICategories.getText());
                    Toast.makeText(CategoryAndTime.this,"Select Time Duration",Toast.LENGTH_LONG).show();
                }
                else if(serves.getText().toString().trim().length()<=0 || serves.getText()==null)
                {
                    Log.d(TAG,"next screen"+selectedICategories.getText());
                    Toast.makeText(CategoryAndTime.this,"Select serves",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(CategoryAndTime.this, IngredientsUploadActivity.class);

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
                    String x[]=selectedICategories.getText().toString().split(",");
                    for(int i=0;i<x.length;i++)
                    {
                        selectedCategoryIds.add(categoryHashMAp.get(x[i]));
                    }

                    Gson gson = new Gson();

                    String jsonCategory = gson.toJson(selectedCategoryIds);
                    Log.d(TAG, "onClick: Category +"+jsonCategory);
                    intent.putExtra("Category", jsonCategory);
                    intent.putExtra("Time Duration", timeDuration.getText().toString());
                    intent.putExtra("Servings", serves.getText().toString());
                    Intent oldIntent=getIntent();
                    intent.putExtra("user",intentOld.getSerializableExtra("user"));

                    startActivity(intent);
                }
            }
        });


        editCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(CategoryAndTime.this);
                mBuilder.setTitle("Categories:");

                    mBuilder.setMultiChoiceItems(categoryList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                            if (isChecked) {
                                if(selectedItems.size()==5)
                                {
                                    checkedItems[i]=false;
                                    Toast.makeText(getApplicationContext(),"You can select only 5 categories",Toast.LENGTH_SHORT).show();
                                    ((AlertDialog) dialogInterface).getListView().setItemChecked(i, false);
                                }
                                else {
                                    if (!selectedItems.containsKey(i)) {
                                        selectedItems.put(i,i);
                                    } else {
                                        selectedItems.remove(i);
                                    }
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
                            item=item+ categoryList[selectedItems.get(k)];
                            if(k!=(selectedItems.size()-1))
                            {
                                item=item+",";
                            }
                        }
                        selectedICategories.setText(item);
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
                            selectedICategories.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }


}
