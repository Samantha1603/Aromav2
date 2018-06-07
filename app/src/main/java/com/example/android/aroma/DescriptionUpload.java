package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.aroma.Utils.CreateCategoryHashMap;

import java.util.ArrayList;

public class DescriptionUpload extends AppCompatActivity {

    private static final String TAG = "Description";


    private Button editingredients;
    private TextView selectedIngredients;


    boolean[] checkedItems;
    String[] categoryList;
    ArrayList<Integer> selectedItems =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_upload);
        editingredients=(Button) findViewById(R.id.ingredients);
        selectedIngredients=(TextView) findViewById(R.id.selectedIngredients);
        categoryList=CreateCategoryHashMap.createCategoryList();
        checkedItems=new boolean[categoryList.length];

        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(DescriptionUpload.this, IngredientsUploadActivity.class);
                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DescriptionUpload.this, DescriptionUpload.class);
                startActivity(intent);
            }
        });
    }


}
