package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.aroma.Model.Comment;
import com.example.android.aroma.Utils.IngredientsCustomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    private static final String TAG = "RecipeDetails";
    TextView food_name,food_servings,food_time,recipe_description,recipe_steps;
    //TextView recipe_ingredients;
    ImageView food_image;
    Button addComment;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnLike;

    String recipeID="";
    FirebaseDatabase database;

    DatabaseReference recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        //Firebase
        database = FirebaseDatabase.getInstance();
        recipe = database.getReference("Recipes");

        btnLike = (FloatingActionButton)findViewById(R.id.btnLike);

        food_name =(TextView)findViewById(R.id.food_name);
        recipe_description=(TextView)findViewById(R.id.recipe_description);
        //recipe_ingredients=(TextView)findViewById(R.id.recipe_ingredients);
        recipe_steps=(TextView)findViewById(R.id.recipe_directions);
        food_servings=(TextView)findViewById(R.id.food_servings);
        food_time=(TextView)findViewById(R.id.food_time);
        food_image=(ImageView)findViewById(R.id.image_recipe);
        addComment=(Button) findViewById(R.id.addcomments);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance((R.style.ExpandedAppBar));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));

        //Get food id from intent
        if(getIntent()!=null) {
            //System.out.println(getIntent().getData());
            recipeID = getIntent().getStringExtra("RecipeID");
        }
        Intent intent=getIntent();
        String dataFrom=intent.getStringExtra("dataFrom");
//        if(dataFrom.equals("Upload"))
//        {
//            displayRecipe(intent);
//        }
//        else
//        {
//            if(!recipeID.isEmpty())
//            {
//                getDetailRecipe(recipeID);
//            }
//        }
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecipeDetails.this, CommentLayout.class);
                startActivity(intent);
            }
        });


        test();




    }

    private void test() {

        String img="https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F4_3_horizontal_-_1200x900%2Fpublic%2Fcheese-truffles-sl-1000.jpg%3Fitok%3DZZw-7iua&w=800&q=85";
        Picasso.with(getBaseContext()).load(img).into(food_image);
        collapsingToolbarLayout.setTitle("abc");
        food_servings.setText("10");
        food_time.setText("2 hrs");
        food_name.setText("abc");
        recipe_description.setText("this is recipe for abc");
        recipe_steps.setText("first do this then do that");
    }


    private void displayRecipe(Intent intent)
    {
        try{
            String steps=intent.getStringExtra("description");

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
            recipe_description.setText(intent.getStringExtra("Ingredients"));
            recipe_steps.setText(intent.getStringExtra("Instructions"));
            food_time.setText(intent.getStringExtra("Time Duration"));



        }
        catch(Exception e)
        {
            Log.d(TAG,"Exception while creating JSON");
        }

    }

    private void getDetailRecipe(String recipeID) {

        System.out.println(recipeID + recipe.child(recipeID).toString());

        recipe.child(recipeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                //Set image
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(food_image);
                collapsingToolbarLayout.setTitle(food.getName());
                System.out.println("name is : "+ food.getName());
                food_servings.setText(food.getServings());
               // food_time.setText(food.getTime());
                food_name.setText(food.getName());
                recipe_description.setText(food.getDescription());
                recipe_steps.setText(food.getDirections());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
