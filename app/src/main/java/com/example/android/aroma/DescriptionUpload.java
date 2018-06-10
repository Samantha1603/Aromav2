package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.example.android.aroma.Utils.LinedEditText;

import org.json.JSONObject;

import java.util.ArrayList;

public class DescriptionUpload extends AppCompatActivity {

    private static final String TAG = "Description";

    private LinedEditText description;
    ArrayList<Integer> selectedItems =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_upload);
        description=(LinedEditText) findViewById(R.id.description);


        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(DescriptionUpload.this,IngredientsUploadActivity.class);
                startActivity(intent);
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(description.getText().toString().trim().length()<=0 || description.getText()==null)
                {
                    Log.d(TAG,"next screen"+description.getText());
                    Toast.makeText(DescriptionUpload.this,"Description is mandatory.",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(DescriptionUpload.this, RecipeDetails.class);
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
                    intent.putExtra("Ingredients", intentOld.getStringExtra("Ingredients"));
                    intent.putExtra("Description", description.getText().toString());
                    intent.putExtra("dataFrom","Upload");
                    startActivity(intent);
                    formatDataAsJSON();
                }
            }
        });
    }
    private String formatDataAsJSON()
    {
        /*
        "Image": "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F4_3_horizontal_-_1200x900%2Fpublic%2Fcheese-truffles-sl-1000.jpg%3Fitok%3DZZw-7iua&w=800&q=85",
     "Ingredients": "1 package (17-1/2 ounces) sugar cookie mix",
      "MenuID": "01"
         */
        Intent intent=getIntent();
        if (intent.hasExtra(getString(R.string.selected_image))) {
            String imgUrl;
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            intent.putExtra("selected_image", imgUrl);

        } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
            Bitmap bitmap;
            bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
            intent.putExtra("selected_bitmap", bitmap);
        }
        final JSONObject root=new JSONObject();
        try{
            String steps=description.getText().toString();
            root.put("Name",intent.getStringExtra("Title"));
            root.put("Servings",intent.getStringExtra("Servings"));
            root.put("Description","");
            root.put("Steps",steps);
            root.put("Ingredients", intent.getStringExtra("Ingredients"));
            root.put("MenuID","00");
            if (intent.hasExtra(getString(R.string.selected_image))) {
                String imgUrl;
                imgUrl = intent.getStringExtra(getString(R.string.selected_image));
                String mAppend = "file://"+imgUrl;
                root.put("Image",mAppend);

            } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
//                Bitmap bitmap;
//                bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
                root.put("Image","Bitmap");
            }
            Log.d(TAG,root.toString());

        }
        catch(Exception e)
        {
            Log.d(TAG,"Exception while creating JSON");
        }
        return null;
    }

}
