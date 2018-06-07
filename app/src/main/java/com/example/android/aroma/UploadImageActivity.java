package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    private HashMap<Integer,String> categoryMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Log.d(TAG,"oncreate got the chosen image:"+getIntent().getStringExtra(getString(R.string.selected_image)));
        setImage();
        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);
        editTitle=(EditText) findViewById(R.id.recipeName);
//        editCategories=(Spinner) findViewById(R.id.category);
//        editDescription=(EditText) findViewById(R.id.description);
//        editIngredients=(EditText) findViewById(R.id.ingredients);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(UploadImageActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTitle.getText().toString().trim().length()<=0 || editTitle.getText()==null)
                {
                    Log.d(TAG,"next screen"+editTitle.getText());
                    Toast.makeText(UploadImageActivity.this,"Enter Recipe Name",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(UploadImageActivity.this, CategoryAndTime.class);
                    startActivity(intent);
                }
                //UPLOAD IMAGE TO FIREBASE
                formatDataAsJSON();
            }
        });

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
            //           imgUrl="spoonacular.com/recipeImages/618144-556x370.jpg";
            UniversalImageLoader.setImage(imgUrl, imageView, null, mAppend);

        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
            Log.d(TAG, "got new bitmap");
            imageView.setImageBitmap(bitmap);
        }

    }
}
