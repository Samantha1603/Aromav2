package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Interface.ItemClickListener;
import com.example.android.aroma.ViewHolder.MenuAdapter;
import com.example.android.aroma.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MenuAdapter.OnItemClickListener {
    public static final String EXTRA_NAME = "name";

    private ArrayList<Category> menuList;
    MenuAdapter menuAdapter;
   FirebaseDatabase database;
   DatabaseReference category;
   private RequestQueue mQueue;
   TextView textFullName;
   RecyclerView recyclerMenu;
   RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mQueue = Volley.newRequestQueue(this);
        menuList = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //init firebase
        database = FirebaseDatabase.getInstance();
        category= database.getReference("Categories");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,SearchByTitle.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        textFullName=(TextView)findViewById(R.id.textFullName);
        //textFullName.setText("default");

        //load menu
        recyclerMenu = (RecyclerView)findViewById(R.id.recylcer_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerMenu.setLayoutManager(layoutManager);
        
       // loadMenu();
        jsonParse();



    }

    private void jsonParse() {
        String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/categories";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {

                                try {
                                    JSONObject jobj = response.getJSONObject("data");
                                    JSONArray jsonArray = jobj.getJSONArray("categories");
                                    for (int i =0; i<jsonArray.length();i++)
                                    {
                                        JSONObject categories = jsonArray.getJSONObject(i);
                                        String id = categories.getString("id");
                                        String name = categories.getString("name");
                                        System.out.println("number = " +i +name);
                                       // String image = categories.getString("webformatURL");
                                        String image= "https://pixabay.com/get/eb3cb5072cf4053ed1584d05fb1d4395e374ebd21fac104497f8c570a3e5b5bf_640.jpg";
                                        System.out.println("number = " +i +image);
                                        //String image = categories.getString("https://en.wikipedia.org/wiki/Food");
                                        menuList.add(new Category(name, image,id));

                                    }
                                    menuAdapter = new MenuAdapter(Home.this, menuList);
                                    recyclerMenu.setAdapter(menuAdapter);
                                    menuAdapter.setOnItemClickListener(Home.this);
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

    private void loadMenu() {
         adapter =  new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener(){
                    public void onClick(View view, int position, boolean isLongClick){
                        //Toast.makeText(Home.this,""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //Get category ID and send to new activity
                        Intent foodList = new Intent(Home.this,FoodList.class);
                        //CategoryID is key, so get key of the menu

                        System.out.println("key is" + adapter.getRef(position).getKey());

                        foodList.putExtra("CategoryID",adapter.getRef(position).getKey());
                        startActivity(foodList);

                    }
                });

            }
        };
        recyclerMenu.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_searchTitle) {
            Intent intent=new Intent(Home.this,SearchByTitle.class);
            startActivity(intent);

        }
        if (id == R.id.action_searchIngredients) {
            Intent intent=new Intent(Home.this,SearchByIngredients.class);
            startActivity(intent);

        }
        if (id == R.id.action_searchPopular) {
            Intent intent=new Intent(Home.this,SearchPopular.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_liked) {

        } else if (id == R.id.nav_subscription) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent foodlistIntent = new Intent(Home.this, FoodList.class);
        Category clickedItem = menuList.get(position);
        System.out.println(clickedItem.getId()+clickedItem.getName());

        foodlistIntent.putExtra(EXTRA_NAME, clickedItem.getId());
        //detailIntent.putExtra(EXTRA_CREATOR, clickedItem.getCreator());
        //detailIntent.putExtra(EXTRA_LIKES, clickedItem.getLikeCount());

        startActivity(foodlistIntent);
    }
}
