package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.aroma.Interface.ItemClickListener;
import com.example.android.aroma.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryID = "";
    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        //Firebase
        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Recipes");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent here

        if(getIntent()!=null)
        {
            categoryID=getIntent().getStringExtra("CategoryID");
        }
        if(!categoryID.isEmpty()&& categoryID!=null)
        {
            loadListFood(categoryID);
        }

    }

    private void loadListFood(String categoryID) {
        adapter =new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,
                foodList.orderByChild("MenuID").equalTo(categoryID)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                       // Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        //Start new activity for recipe details
                        Intent recipeDetails = new Intent(FoodList.this,RecipeDetails.class);
                        recipeDetails.putExtra("dataFrom","FoodList");
                        recipeDetails.putExtra("RecipeID",adapter.getRef(position).getKey());
                        startActivity(recipeDetails);

                    }
                });
            }
        };

        //set adapter
        recyclerView.setAdapter(adapter);
    }
}
