package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Interface.ItemClickListener;
import com.example.android.aroma.ViewHolder.FoodListAdapter;
import com.example.android.aroma.ViewHolder.FoodViewHolder;
import com.example.android.aroma.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchByTitle extends AppCompatActivity implements FoodListAdapter.OnItemClickListener{

    public static final String Search_ID = "id";
    private ArrayList<Food> foodList;
    FoodListAdapter foodListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    //DatabaseReference foodList;
    String categoryID = "";
    private RequestQueue mQueue;
    TextView textFullName;
   // RecyclerView recyclerMenu;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    //Search Functionality
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_title);
        mQueue = Volley.newRequestQueue(this);
        foodList = new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_food_search);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent here

        if(getIntent()!=null)
        {

            //loadListFood();
        }

        //search thing
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.search_bar);
        materialSearchBar.setHint("Enter title to search here");
        materialSearchBar.setSpeechMode(false);
        //loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest =new ArrayList<>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //When search bar is closed
                //Restore original suggest adapter
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //when search finishes
                jsonParse(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

   /* private void startSearch(CharSequence text) {
        searchAdapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
            Food.class,
            R.layout.food_item,
            FoodViewHolder.class,
            foodList.orderByChild("Name").equalTo(text.toString())
        ){

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
                        Intent recipeDetails = new Intent(SearchByTitle.this,RecipeDetails.class);
                        recipeDetails.putExtra("RecipeID",searchAdapter.getRef(position).getKey());
                        startActivity(recipeDetails);

                    }
            });
        }
    };
        recyclerView.setAdapter(searchAdapter);

    }*/

    private void jsonParse(CharSequence categoryId) {
        String base_url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/recipes?search=category&keyword=";
        String url = base_url+categoryId;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try {
                            JSONObject jobj = response.getJSONObject("data");
                            JSONArray jsonArray = jobj.getJSONArray("recipes");
                            for (int i =0; i<jsonArray.length();i++)
                            {
                                JSONObject recipes = jsonArray.getJSONObject(i);
                                String id = recipes.getString("id");
                                String name = recipes.getString("title");
                                System.out.println("number = " +i +name);
                                // String image = categories.getString("webformatURL");
                                String image= recipes.getString("image_url");
                                System.out.println("number = " +i +image);
                                //String image = categories.getString("https://en.wikipedia.org/wiki/Food");
                                foodList.add(new Food(name,image,id));

                            }
                            foodListAdapter = new FoodListAdapter(SearchByTitle.this, foodList);
                            recyclerView.setAdapter(foodListAdapter);
                            foodListAdapter.setOnItemClickListener(SearchByTitle.this);
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

    /*
    private void loadSuggest() {
        foodList.orderByChild("MenuID")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Food item = postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadListFood() {
        adapter =new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,
                foodList.orderByChild("MenuID")) {
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
                        Intent recipeDetails = new Intent(SearchByTitle.this,RecipeDetails.class);
                        recipeDetails.putExtra("RecipeID",adapter.getRef(position).getKey());
                        startActivity(recipeDetails);

                    }
                });
            }
        };

        //set adapter
        recyclerView.setAdapter(adapter);
    }*/

    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, RecipeDetails.class);
        Food clickedItem = foodList.get(position);

        detailIntent.putExtra(Search_ID, clickedItem.getId());
        //detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        //detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(detailIntent);
    }

}
