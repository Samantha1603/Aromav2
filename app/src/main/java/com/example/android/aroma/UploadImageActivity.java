package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.aroma.Utils.UniversalImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadImageActivity extends AppCompatActivity {

    private static final String TAG = "UploadImageActivity";
    //public String mAppend = "http://";
    public String mAppend = "file://";
    private Intent intent;
    private String imgUrl;
    private Bitmap bitmap;
    private EditText editTitle;
    private EditText editDescription;
    private EditText editIngredients;
    private Spinner editCategories;

    private HashMap<Integer,String> categoryMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Log.d(TAG,"oncreate got the chosen image:"+getIntent().getStringExtra(getString(R.string.selected_image)));
        setImage();
        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);
        editTitle=(EditText) findViewById(R.id.recipeName);
        editCategories=(Spinner) findViewById(R.id.category);
        editDescription=(EditText) findViewById(R.id.description);
        editIngredients=(EditText) findViewById(R.id.ingredients);
        createHashMap();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                finish();
            }
        });

            TextView upload=(TextView) findViewById(R.id.upload);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"next screen");
                    //UPLOAD IMAGE TO FIREBASE
                    formatDataAsJSON();
                }
            });

    }

    private void createHashMap()
    {
        categoryMap.put(1,"vegetarian");
        categoryMap.put(2,"vegan");
        categoryMap.put(3,"gluten free");
        categoryMap.put(4,"dairy free");
        categoryMap.put(5,"main course");
        categoryMap.put(6,"side dish");
        categoryMap.put(7,"dessert");
        categoryMap.put(8,"appetizer");
        categoryMap.put(9,"salad");
        categoryMap.put(10,"bread");
        categoryMap.put(11,"breakfast");
        categoryMap.put(12,"soup");
        categoryMap.put(13,"beverage");
        categoryMap.put(14,"sauce");
        categoryMap.put(15,"drink");
        categoryMap.put(16,"african");
        categoryMap.put(17,"chinese");
        categoryMap.put(18,"japanese");
        categoryMap.put(19,"korean");
        categoryMap.put(20,"thai");
        categoryMap.put(21,"indian");
        categoryMap.put(22,"vietnamese");
        categoryMap.put(23,"british");
        categoryMap.put(24,"irish");
        categoryMap.put(25,"french");
        categoryMap.put(26,"italian");
        categoryMap.put(27,"mexican");
        categoryMap.put(28,"spanish");
        categoryMap.put(29,"middle eastern");
        categoryMap.put(30,"jewish");
        categoryMap.put(31,"american");
        categoryMap.put(32,"cajun");
        categoryMap.put(33,"southern");
        categoryMap.put(34,"greek");
        categoryMap.put(35,"german");
        categoryMap.put(36,"nordic");
        categoryMap.put(37,"eastern european");
        categoryMap.put(38,"caribbean");
        categoryMap.put(39,"latin american");

        ArrayList<String> categoryList=new ArrayList<>();

        categoryList.add("vegetarian");
        categoryList.add("vegan");
        categoryList.add("gluten free");
        categoryList.add("dairy free");
        categoryList.add("main course");
        categoryList.add("side dish");
        categoryList.add("dessert");
        categoryList.add("appetizer");
        categoryList.add("salad");
        categoryList.add("bread");
        categoryList.add("breakfast");
        categoryList.add("soup");
        categoryList.add("beverage");
        categoryList.add("sauce");
        categoryList.add("drink");
        categoryList.add("african");
        categoryList.add("chinese");
        categoryList.add("japanese");
        categoryList.add("korean");
        categoryList.add("thai");
        categoryList.add("indian");
        categoryList.add("vietnamese");
        categoryList.add("british");
        categoryList.add("irish");
        categoryList.add("french");
        categoryList.add("italian");
        categoryList.add("mexican");
        categoryList.add("spanish");
        categoryList.add("middle eastern");
        categoryList.add("jewish");
        categoryList.add("american");
        categoryList.add("cajun");
        categoryList.add("southern");
        categoryList.add("greek");
        categoryList.add("german");
        categoryList.add("nordic");
        categoryList.add("eastern european");
        categoryList.add("caribbean");
        categoryList.add("latin american");
        Log.d(TAG,"size="+categoryList.size());

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,categoryList);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editCategories.setAdapter(adapter);
    }

    private String formatDataAsJSON()
    {
        final JSONObject root=new JSONObject();
        try{
            root.put("title","ABC");
            root.put("servings","43");
            root.put("ingredients","tomatoes,onions");
            root.put("Categories","1");
            root.put("Instructions","Do this then do that");
            root.put("ReadyInMinutes","readyInMinutes");
            Log.d(TAG,root.toString());

        }
        catch(Exception e)
        {
            Log.d(TAG,"Exception while creating JSON");
        }
        return null;
    }
    private void setImage() {
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.imageUpload);
        if (intent.hasExtra(getString(R.string.selected_image))) {
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));

            UniversalImageLoader.setImage(imgUrl, imageView, null, mAppend);

        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "got new bitmap");
            imageView.setImageBitmap(bitmap);
        }
    }
}
