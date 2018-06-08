package com.example.android.aroma;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button signUp;
    Button forgotPass;
    Button login;
    RelativeLayout relay1, relay2, relay3,mainLayout;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            relay1.setVisibility(View.VISIBLE);
            mainLayout.setBackgroundResource(R.drawable.login_page_blur);
          //  relay2.setVisibility(View.VISIBLE);
            relay3.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        relay1 = (RelativeLayout)findViewById(R.id.rellay1);
//      relay2 = (RelativeLayout)findViewById(R.id.rellay2);
        relay3 = (RelativeLayout)findViewById(R.id.progress_layout);
        signUp=(Button) findViewById(R.id.signup);
        forgotPass=(Button)findViewById(R.id.forgotPass);
        login=(Button) findViewById(R.id.login);


        handler.postDelayed(runnable,4000);
        openForgotPass();
        openSignUp();
        onLogin();
    }

    public void openSignUp()
    {
        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,SignUp.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void openForgotPass()
    {
        forgotPass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,forgot_password.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void onLogin()
    {
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,CommentLayout.class);
                        startActivity(intent);
                    }
                }
        );
    }

}
