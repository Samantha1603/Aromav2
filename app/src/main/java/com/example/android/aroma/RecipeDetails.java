package com.example.android.aroma;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.ViewHolder.IngredientAdapter;
import com.example.android.aroma.ViewHolder.StepsAdapter;
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

public class RecipeDetails extends AppCompatActivity {

    TextView food_name,food_servings,food_time, recipe_directions, recipe_ingredients;
    TextView txtview_steps;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnLike;
    private RequestQueue mQueue;
    ListView list_ingredients ;
    ListView listView_steps;
    ArrayList<String> ingList = new ArrayList<>();
    ArrayList<String> stpList = new ArrayList<>();
    String title;
    String recipeID="";
    StepsAdapter stepsAdapter;
    IngredientAdapter ingredientAdapter;
    //FirebaseDatabase database;
    //DatabaseReference recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ingredientAdapter = new IngredientAdapter(this,R.id.recipe_ingredients);
        stepsAdapter = new StepsAdapter(this,R.id.recipe_steps);
        list_ingredients= (ListView)findViewById(R.id.ingredient_listview);
        listView_steps =(ListView)findViewById(R.id.steps_listView);
        list_ingredients.setAdapter(ingredientAdapter);
        listView_steps.setAdapter(stepsAdapter);
        btnLike = (FloatingActionButton)findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareIngredients = title + "   Ingredients : " + ingList.toString() +"Steps : "+stpList.toString();
                String shareName = "Ingredients to buy";

                myIntent.putExtra(Intent.EXTRA_TEXT,shareIngredients);
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareName);
                startActivity(Intent.createChooser(myIntent,"Share using"));


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
        if(!recipeID.isEmpty())
        {
            jsonParse(recipeID);
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

}
