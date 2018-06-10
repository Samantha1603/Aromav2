package com.example.android.aroma;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Model.IngredientModel;
import com.example.android.aroma.ViewHolder.IngredientAdapter;
import com.example.android.aroma.ViewHolder.StepsAdapter;
import com.example.android.aroma.Model.Comment;
import com.example.android.aroma.Utils.IngredientsCustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import static com.example.android.aroma.FoodList.EXTRA_ID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeUpload extends AppCompatActivity {

    private static final String TAG = "RecipeUpload";
    TextView food_name,food_servings,food_time,recipe_description,recipe_steps;
    TextView txtview_steps;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button upload;
    private RequestQueue mQueue;
    ListView list_ingredients ;
    ListView listView_steps;
    ArrayList<String> ingList = new ArrayList<>();
    ArrayList<String> stpList = new ArrayList<>();
    String title;
    String recipeID="";
    StepsAdapter stepsAdapter;
    IngredientAdapter ingredientAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_upload);
        ingredientAdapter = new IngredientAdapter(this,R.id.recipe_ingredients);
        stepsAdapter = new StepsAdapter(this,R.id.recipe_steps);
        list_ingredients= (ListView)findViewById(R.id.ingredient_listview);
        listView_steps =(ListView)findViewById(R.id.steps_listView);
        list_ingredients.setAdapter(ingredientAdapter);
        listView_steps.setAdapter(stepsAdapter);
        upload = (Button)findViewById(R.id.uploadConfirm);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeUpload.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to upload?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        generateJSON();

                        dialog.dismiss();
                           // stop chronometer here

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        food_name =(TextView)findViewById(R.id.food_name);
        //txtview_steps=(TextView)findViewById(R.id.)
        //recipe_directions =(TextView)findViewById(R.id.recipe_description);

        //recipe_ingredients=(TextView)findViewById(R.id.recipe_ingredients);
        // recipe_ingredients =(TextView)findViewById(R.id.recipe_ingredients);
        food_servings=(TextView)findViewById(R.id.food_servings);
        food_time=(TextView)findViewById(R.id.food_time);
        food_image=(ImageView)findViewById(R.id.image_recipe);
        mQueue = Volley.newRequestQueue(this);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance((R.style.ExpandedAppBar));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));

        //Get food id from intent
        if(getIntent()!=null) {
            //System.out.println(getIntent().getData());
            recipeID = getIntent().getStringExtra(EXTRA_ID);
            System.out.println(recipeID);
        }
        Intent intent=getIntent();
        String dataFrom=intent.getStringExtra("dataFrom");
        displayRecipe(intent);

   //     test();
    }



    private void test() {

        String img="https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F4_3_horizontal_-_1200x900%2Fpublic%2Fcheese-truffles-sl-1000.jpg%3Fitok%3DZZw-7iua&w=800&q=85";
        Picasso.with(getBaseContext()).load(img).into(food_image);
        collapsingToolbarLayout.setTitle("abc");
        food_servings.setText("10");
        food_time.setText("2 hrs");
        food_name.setText("abc");


//       / recipe_description.setText("this is recipe for abc");
//        recipe_steps.setText("first do this then do that");
    }


    private void displayRecipe(Intent intent)
    {
        try{
            if (intent.hasExtra(getString(R.string.selected_image))) {
                String imgUrl;
                imgUrl = intent.getStringExtra(getString(R.string.selected_image));
                String mAppend = "file://"+imgUrl;
                Picasso.with(getBaseContext()).load(mAppend).into(food_image);

            } else if (intent.hasExtra(getString(R.string.selected_bitmap))) {
                Bitmap bitmap;
                bitmap = intent.getParcelableExtra(getString(R.string.selected_bitmap));
                //   Picasso.with(getBaseContext()).load(bitmap).into(food_image);
            }

            collapsingToolbarLayout.setTitle(intent.getStringExtra("Title"));
            food_servings.setText(intent.getStringExtra("Servings"));
            // food_time.setText(food.getTime());
            food_name.setText(intent.getStringExtra("Title"));
//            recipe_description.setText(intent.getStringExtra("Ingredients"));
//            recipe_steps.setText(intent.getStringExtra("Instructions"));
//            food_time.setText(intent.getStringExtra("Time Duration"));

            String ingName, ingQuantity, ingUnit, step,step_number;
            JSONArray ingredientArray=new JSONArray(intent.getStringExtra("Ingredients"));

            Log.d(TAG, "displayRecipe: "+ingredientArray);
            for (int j =0;j<ingredientArray.length();j++) {
                JSONObject ings = ingredientArray.getJSONObject(j);

                ingName=ings.getString("name");
                ingList.add(ingName);
                ingQuantity=ings.getString("quantity");
                ingUnit=ings.getString("measure");
                Ingredients ingredientsList= new Ingredients(ingName,ingQuantity,ingUnit);
                ingredientAdapter.add(ingredientsList);

            }

            JSONArray stepsArray=new JSONArray(intent.getStringExtra("Description"));
            Log.d(TAG, "displayRecipe: steps:"+stepsArray);
            for (int j =0;j<stepsArray.length();j++) {
                JSONObject stepObj = stepsArray.getJSONObject(j);
                step=stepObj.getString("step");
                step_number =stepObj.getString("step_number");
                Steps stepsList= new Steps(step,step_number);
                stpList.add(step);
                stepsAdapter.add(stepObj);
            }
        }
        catch(Exception e)
        {
            Log.d(TAG,"Exception while creating JSON in description one");
        }
    }


    private void jsonParse(String recipeId) {
        String base_url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/recipes/";
        String url = base_url+recipeId;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try {
                            JSONObject jobj = response.getJSONObject("data");
                            JSONObject recipes = jobj.getJSONObject("recipe");

                            //JSONObject recipes = jsonArray.getJSONObject(i);
                            //String id = categories.getString("id");
                            String name = recipes.getString("title");
                            title = name;
                            food_name.setText(name);
                            String time = recipes.getString("duration");
                            food_time.setText(time);
                            String serves = recipes.getString("servings");
                            food_servings.setText(serves);

                            // String image = categories.getString("webformatURL");
                            String image= recipes.getString("image_url");
                            Picasso.with(getBaseContext()).load(image)
                                    .into(food_image);
                            String ingName, ingQuantity, ingUnit, step,step_number;
                            //String image = categories.getString("https://en.wikipedia.org/wiki/Food");
                            JSONObject recipeObj = jobj.getJSONObject("recipe");
                            JSONArray ingredientArray =  recipeObj.getJSONArray("ingredients");
                            for (int j =0;j<ingredientArray.length();j++) {
                                JSONObject ings = ingredientArray.getJSONObject(j);

                                ingName=ings.getString("name");
                                ingList.add(ingName);
                                ingQuantity=ings.getString("_pivot_amount");
                                ingUnit=ings.getString("_pivot_unit");
                                Ingredients ingredientsList= new Ingredients(ingName,ingQuantity,ingUnit);
                                ingredientAdapter.add(ingredientsList);

                            }
                            JSONArray stepsArray =  recipeObj.getJSONArray("instructions");
                            for (int j =0;j<stepsArray.length();j++) {
                                JSONObject steps = stepsArray.getJSONObject(j);
                                step=steps.getString("instruction");
                                step_number =steps.getString("step_num");
                                //ArrayList<String>
                                Steps stepsList= new Steps(step,step_number);
                                stpList.add(step);
                                stepsAdapter.add(stepsList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
    private void generateJSON() {
        Intent i = getIntent();
        JSONObject j = new JSONObject();
        try {
            j.put("title", i.getStringExtra("Title"));
            j.put("vegetarian", false);
            j.put("vegan", false);
            j.put("dairyFree", false);
            j.put("glutenFree", false);
            j.put("sourceUrl", "");
            j.put("sourceName", "");
            j.put("description", "");
            j.put("readyInMinutes",  i.getStringExtra("Time Duration"));
            j.put("servings", i.getStringExtra("Servings"));
            j.put("categories", i.getStringExtra("Category"));
            j.put("ingredients",i.getStringExtra("Ingredients"));
            j.put("instructions",i.getStringExtra("Description"));
            j.put("imagebase64", "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4...");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}



/*
JSON format

"recipe": {
        "title": "Buttermilk-Marinated Chicken",
        "vegetarian": false,
        "vegan": false,
        "dairyFree": false,
        "glutenFree": true,
        "sourceUrl": "https://cooking.nytimes.com/recipes/1018731-buttermilk-marinated-roast-chicken",
        "sourceName": "New York Times Cooking",
        "description": "This recipe, adapted from Samin Nosrat's \"Salt, Fat, Acid, Heat\", is inspired by the Southern grandma method of marinating chicken overnight in buttermilk before frying it. You're roasting here, but the buttermilk and salt still work like a brine, tenderizing the meat on multiple levels to yield an unbelievably juicy chicken.",
        "readyInMinutes": 105,
        "servings": 4,
        "categories": [5, 31],
        "ingredients": [
            {
                "id": 5006,
                "name": "whole chicken",
                "amount": 1,
                "extra_info": "3 1/2 - 4 pounds"
            },
            {
                "id": 1230,
                "name": "buttermilk",
                "amount": 2,
                "unit": "cup"
            },
            {
                "id": 1082047,
                "name": "kosher salt",
                "amount": 3,
                "unit": "Tbsp"
            },
            {
                "id": 1001,
                "name": "butter",
                "amount": 1,
                "unit": "stick"
            }
        ],
        "instructions": [
            {
                "step": "The day before you want to cook the chicken, remove the wingtips by cutting through the first wing joint with poultry shears or a sharp knife. Reserve for stock. Season chicen generously with salt and let it sit for 30 minutes."
            },
            {
                "step": "Stir 2 tablespoons kosher salt or 4 teaspoons fine sea salt into the buttermilk to dissolve. Place the chicken in a gallon-size resealable plastic bag and pour in the buttermilk. (If the chicken won't fit in a gallon-size bag, double up two plastic produce bags to prevent leaks and tie the bag with twine.)"
            },
            {
                "step": "Seal the baag, squish the buttermilk all around the chicken, place on a rimmed plate, nd refrigerate for 12 to 24 hours. If you're so inclined, you can turn the bag periodically so every part of the chicken gets marinated, but that's not essential"
            },
            {
                "step": "Pull the chicken from the fridge an hour before you plan to cook it. Heat the oven to 425 degrees with a rack set in the center position."
            },
            {
                "step": "Remove the chicken from the plastic bag and scrape off as much buttermilk as you can without being obsessive. Tightly tie together the legs with a piece of butcher's twine. Place the chicken in a 10-inch cast-iron skillet or a shallow roasting pan."
            },
            {
                "step": "Slide the pan all the way to the back of the oven on the center rack. Rotate the pan so that the legs are pointing toward the center of the oven. (The back corners tend to be the hottest spots in the oven, so this orientation protects the breast from overcooking before the legs are done.) Pretty quickly you should hear the chicken sizzling."
            },
            {
                "step": "After about 20 minutes, when the chicken starts to brown, reduce the heat to 400 degrees and continue roasting for 10 minutes."
            },
            {
                "step": "Move the pan so the legs are facing the  right corner of the oven. Continue cooking for another 30 minutes or so, until the chicken is brown all over and the juices run clear when you insert a knife down to the bone between the leg and the thigh. If the skin is getting too brown before it is cooked through, use a foil tent. Remov it to a platter and let it rest for 10 minutes before carving and serving."
            }
        ],
        "imagebase64": "data:image/jpg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAUDBAQEAwUEBAQFBQUGBwwIBwcHBw8LCwkMEQ8SEhEPERETFhwXExQaFRERGCEYGh0dHx8fExciJCIeJBweHx7/2wBDAQUFBQcGBw4ICA4eFBEUHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4eHh4..."
    }
 */