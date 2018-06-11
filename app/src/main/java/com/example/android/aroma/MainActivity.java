package com.example.android.aroma;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Model.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String username,password,email;
    EditText editUsername,editPassword;
    Button signUp;
    Button forgotPass;
    private RequestQueue mQueue;
    String token="",userId;
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
        editUsername=(EditText)findViewById(R.id.signin_email);
        editPassword=(EditText)findViewById(R.id.signin_password);
        mQueue = Volley.newRequestQueue(this);

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
                        username=editUsername.getText().toString().trim();
                        password=editPassword.getText().toString();
                        String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/users/token";

                        JSONObject o=new JSONObject();
                        JSONObject x=new JSONObject();

                        try {
                            o.put("email",username);
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
                                                JSONObject jobj = response.getJSONObject("data");

                                                token=jobj.getString("token");
                                                email=jobj.getString("email");
                                                userId=String.valueOf(jobj.getInt("id"));
                                                if(token==null){

                                                    Toast.makeText(getApplicationContext(), "Username or Password incorrect", Toast.LENGTH_SHORT).show();

                                                }
                                                else {
                                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                                    intent.putExtra("username",username);
                                                    intent.putExtra("password",password);
                                                    intent.putExtra("email",email);
                                                    intent.putExtra("id",userId);
                                                    startActivity(intent);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    error.printStackTrace();
                                }


                            }){
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("Content-Type", "application/json");
                                    String creds = String.format("%s:%s","imsam.rod@gmail.com","123456");
                                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                                    params.put("Authorization", auth);
                                    return params;

                                }
                            };

                            mQueue.add(request);
                            //System.out.println("token is: "+token);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                    }
                }
        );
    }
    private  void createUSer() {
           }

}
