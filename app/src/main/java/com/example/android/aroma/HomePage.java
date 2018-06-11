package com.example.android.aroma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class HomePage extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.commonmenus,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.home)
        {
            Toast.makeText(this, "Home menu is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
        }
        else if(id==R.id.liked)
        {
            Toast.makeText(this, "Liked Recipes is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
        }
        else if(id==R.id.subscribe)
        {
            Toast.makeText(this, "Subscription is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
        }
        else if(id==R.id.account)
        {
            Toast.makeText(this, "My account Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
        }
        else if(id==R.id.upload)
        {
            Toast.makeText(this, "Upload Recipe is Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomePage.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
