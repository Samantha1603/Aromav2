package com.example.android.aroma;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RecipeDetails extends AppCompatActivity {

    TextView food_name,food_servings,food_time,recipe_description,recipe_steps;
    //TextView recipe_ingredients;
    ImageView food_image;
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

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance((R.style.ExpandedAppBar));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance((R.style.CollapsedAppBar));

        //Get food id from intent
        if(getIntent()!=null) {
            //System.out.println(getIntent().getData());
            recipeID = getIntent().getStringExtra("RecipeID");
        }
        if(!recipeID.isEmpty())
        {
            getDetailRecipe(recipeID);
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
