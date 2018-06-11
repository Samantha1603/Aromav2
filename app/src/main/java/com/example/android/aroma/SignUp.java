package com.example.android.aroma;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    private RequestQueue mQueue;
    EditText editTextEmail, editTextPassword, editTextUsername;
    int userId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextUsername=(EditText)findViewById(R.id.username);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mQueue= Volley.newRequestQueue(this);
        mAuth = FirebaseAuth.getInstance();;

        findViewById(R.id.signUpText).setOnClickListener(this);
        findViewById(R.id.createAccount).setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createAccount:
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();

                if (email.isEmpty()) {
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Please enter a valid email");
                    editTextEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError("Minimum lenght of password should be 6");
                    editTextPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/users";
                //  String url = base_url+"1"+"/comments";
                //Log.d(TAG, "sendJSONParse:comments "+url);
                final Intent detailIntent = new Intent(this, Home.class);

                JSONObject o=new JSONObject();
                JSONObject x=new JSONObject();

                try {
                    o.put("username",username);
                    o.put("email",email);
                    o.put("password",password);
                    x.put("user",o);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {


                    final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, x,
                            new Response.Listener<JSONObject>() {


                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("my tag", "onResponse:  create uuser  in json request of comments"+response);
                                    try {
                                        JSONObject resObj = response.getJSONObject("data");
                                        System.out.println("res object" + resObj.toString());
                                        userId = resObj.getInt("id");
                                        System.out.println("user id:"+userId);

                                        detailIntent.putExtra("username",editTextUsername.getText().toString());
                                        detailIntent.putExtra("password",editTextPassword.getText().toString());
                                        System.out.println("response is "+ userId);
                                        detailIntent.putExtra("email",editTextEmail.getText().toString());
                                        detailIntent.putExtra("id",String.valueOf(userId));

                                        startActivity(detailIntent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d(TAG, "getuser: comment response error");
                            error.printStackTrace();
                        }


                    });
//

                    mQueue.add(request);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

        }



    }
}