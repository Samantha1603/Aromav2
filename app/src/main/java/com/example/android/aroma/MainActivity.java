package com.example.android.aroma;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.aroma.Model.Comment;

public class MainActivity extends AppCompatActivity {
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    Button forgotPass;
    Button login;
    EditText  editTextPassword, editTextEmail;
    ProgressBar progressbar;
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

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        relay1 = (RelativeLayout)findViewById(R.id.rellay1);
//      relay2 = (RelativeLayout)findViewById(R.id.rellay2);
        relay3 = (RelativeLayout)findViewById(R.id.progress_layout);
        signUp=(Button) findViewById(R.id.signup);
        forgotPass=(Button)findViewById(R.id.forgotPass);
        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);


        handler.postDelayed(runnable,4000);
        openForgotPass();

    }


    public void userLogin() {
         String password = editTextPassword.getText().toString().trim();
         String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;

        }



        if (password.length() < 6) {
            editTextPassword.setError("Minimum Length of Passwords should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "get Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup :
                Intent intent=new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
                break;
            case R.id.login:
                userLogin();
                break;
        }
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



}
