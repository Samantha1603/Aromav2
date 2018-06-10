package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.ViewHolder.FoodListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static com.example.android.aroma.Home.EXTRA_NAME;

public class FoodList extends AppCompatActivity
        implements FoodListAdapter.OnItemClickListener {
    public static final String EXTRA_ID = "id";
    private ArrayList<Food> foodList;
    FoodListAdapter foodListAdapter;
    FirebaseDatabase database;
    DatabaseReference category;
    private RequestQueue mQueue;
    TextView textFullName;
    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;
    String categoryID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        mQueue = Volley.newRequestQueue(this);
        foodList = new ArrayList<>();

        //load menu
        recyclerMenu = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);

        if(getIntent()!=null)
        {
            categoryID=getIntent().getStringExtra(EXTRA_NAME);
        }
        if(!categoryID.isEmpty()&& categoryID!=null)
        {
            jsonParse(categoryID);
        }
    }

    private void jsonParse(String categoryId) {
        String base_url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/recipes?search=category&category=";
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
                            foodListAdapter = new FoodListAdapter(FoodList.this, foodList);
                            recyclerMenu.setAdapter(foodListAdapter);
                            foodListAdapter.setOnItemClickListener(FoodList.this);
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
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, RecipeDetails.class);
        Food clickedItem = foodList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        startActivity(detailIntent);
    }
}
