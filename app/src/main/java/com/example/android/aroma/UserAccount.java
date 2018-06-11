package com.example.android.aroma;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UserAccount extends AppCompatActivity{
    String username,password,email,id;
    int id1;
    TextView txtUsername,txtPassword,txtEmail,txtId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount);
        txtId=(TextView)findViewById(R.id.textViewid);
        txtUsername=(TextView)findViewById(R.id.textView7);
        txtEmail=(TextView)findViewById(R.id.textView8);
        txtPassword=(TextView)findViewById(R.id.textView9);
        Intent intent=getIntent();
        //id1=intent.getIntExtra("id");
        id=intent.getStringExtra("id");
        txtId.setText(id);
        username=intent.getStringExtra("username");
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");
        txtPassword.setText(password);
        txtEmail.setText(email);
        txtUsername.setText(username);


    }
}
