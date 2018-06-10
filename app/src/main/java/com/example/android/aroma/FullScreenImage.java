package com.example.android.aroma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class FullScreenImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        ImageView fullScreenImgView=(ImageView) findViewById(R.id.fullScreenImageView);

        Intent callingIntent=getIntent();
        if(callingIntent!=null)
        {
            Uri uriIntent=callingIntent.getData();
        }
    }
}
