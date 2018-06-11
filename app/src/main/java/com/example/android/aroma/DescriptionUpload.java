package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.example.android.aroma.Utils.LinedEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class DescriptionUpload extends AppCompatActivity{

    private static final String TAG = "Description";

    private LinedEditText description;
    private Button addStep;
    private Button removeStep;
    int count=0;
    ArrayList<Integer> selectedItems =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_upload);
        description=(LinedEditText) findViewById(R.id.description);
        addStep=(Button) findViewById(R.id.addStep);
        removeStep=(Button) findViewById(R.id.removeStep);


        ImageView backArrow=(ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                Intent intent=new Intent(DescriptionUpload.this,IngredientsUploadActivity.class);
                startActivity(intent);
            }
        });

        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count+=1;
               String descriptionText=description.getText().toString();
                if(descriptionText.equals(""))
                     description.setText("Step"+count+": ");
                else
                    description.setText(descriptionText+"\nStep"+(count)+": ");
            }
        });

        removeStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String descriptionText=description.getText().toString();

                if(!descriptionText.equals("")) {
                    int indexOf = descriptionText.indexOf("Step" + (count) + ":");
                    descriptionText = descriptionText.substring(0, indexOf);
                    count-=1;
                    description.setText(descriptionText);

                }
            }
        });

        TextView upload=(TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(description.getText().toString().trim().length()<=0 || description.getText()==null || !description.getText().toString().startsWith("Step1"))
                {
                    Log.d(TAG,"next screen"+description.getText());
                    Toast.makeText(DescriptionUpload.this,"Description is mandatory and must start with Step1:",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(DescriptionUpload.this, RecipeUpload.class);
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


                    Gson gson = new Gson();
                    Log.d(TAG, "onClick: descriptionis: "+description.getText().toString());
                    String s="Step1: do one\nStep2: then two\nStep3: and then three";
                    int linecount=description.getLineCount();
                    int indexOf=description.getText().toString().lastIndexOf("Step");
                    char y=(description.getText().toString().charAt(indexOf+4));
                    int length=Integer.parseInt(y+"");
                    ArrayList<Steps> stepsList=new ArrayList<>();
                    String descriptionArray[]=description.getText().toString().split("\n");
                    for(int i=0;i<length;i++)
                    {
                        String eachStep[]=descriptionArray[i].split(":");
                        String stepNo=eachStep[0].substring(4);
                        Log.d(TAG, "onClick: step number="+stepNo);
                        Steps sep=new Steps(eachStep[1],stepNo);
                        sep.setStep(eachStep[1]);
                        sep.setStep_number(stepNo);
                        stepsList.add(sep);

                    }
                    String jsonDescription  = gson.toJson(stepsList);
                    intent.putExtra("Description", jsonDescription);
                    intent.putExtra("dataFrom","Upload");
                    startActivity(intent);
                 //  formatDataAsJSON();
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
