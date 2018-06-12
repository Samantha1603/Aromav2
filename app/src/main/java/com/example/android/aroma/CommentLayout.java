package com.example.android.aroma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Model.Comment;
import com.example.android.aroma.Model.UserModel;
import com.example.android.aroma.Utils.CommentListAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentLayout extends AppCompatActivity {

    private static final String TAG = "CommentLayout";

    private ImageView mBackArrow,mCheckMark;
    private EditText mComment;
    private ArrayList<Comment> mComments;
    private ListView mListView;


    private RequestQueue mQueue;
    private RequestQueue aQueue;
    private RequestQueue bQueue;


    private String filePath="MyFileStorage";
    private String fileName="comments.json";
    private String token="";
    private String recipeId="";
    File myFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mBackArrow=(ImageView) findViewById(R.id.backArrow);
        mCheckMark=(ImageView) findViewById(R.id.ivPostComment);
        mComment=(EditText) findViewById(R.id.comment);
        mListView=(ListView) findViewById(R.id.listViewC);

        Intent oldIntent=getIntent();

        mComments=new ArrayList<>();
        mQueue = Volley.newRequestQueue(this);
        aQueue = Volley.newRequestQueue(this);
        bQueue = Volley.newRequestQueue(this);
        Comment c=new Comment();
        c.setComment("as");
        Date d=new Date();
        c.setDate_created(d.toString());
        UserModel u=(UserModel) oldIntent.getSerializableExtra("user");
        c.setUser_id(u.getUserId());

        //registerUser();

        Comment c1=new Comment();
        c1.setComment("as");
        c1.setDate_created(d.toString());
        c1.setUser_id("Sam");
        //mComments.add(c);
       // mComments.add(c1);
        Intent n=getIntent();
        createUSer();
      //  recipeId=n.getStringExtra("recipeId");
        displayComments();

        mCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
///                writeToJsonFile();
                sendJSONParse(recipeId);
                displayComments();
                Log.d(TAG, "onClick: comments saved");
                Intent intent=new Intent(CommentLayout.this,CommentLayout.class);
                Intent oldIntent=getIntent();
                intent.putExtra("user",oldIntent.getSerializableExtra("user"));

                startActivity(intent);
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommentLayout.this,RecipeDetails.class);
                Intent oldIntent=getIntent();
                intent.putExtra("user",oldIntent.getSerializableExtra("user"));
                startActivity(intent);
            }
        });



    }

    private void displayComments() {
        mComments=getJSONParse(recipeId);
        if(mComments==null)
            mComments=new ArrayList<>();
        CommentListAdapter adapter=new CommentListAdapter(CommentLayout.this,R.layout.layout_comment,mComments);
        mListView.setAdapter(adapter);

    }


    public void writeToJsonFile()
    {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "commentList.json");
        boolean exits=false;

        ArrayList<Comment> c=new ArrayList<>();
        if(file.exists())
        {
            Log.d(TAG, "writeFile: FILE ALREADY CREATED");
            c=readFile();
            exits=true;
//            file.delete();
//            Log.d(TAG, "writeToJsonFile: FILE DELETED!!!!");
//            file=new File(path, "commentList.json");


        }
        try {
            FileOutputStream stream = new FileOutputStream(file);
            try {
                Log.d(TAG, "writeToJsonFile: In hereeeeeeeee");
                //HashMap<String,String> singleComment=new HashMap<>();
                Comment commentOne=new Comment();
                commentOne.setUser_id("Sam");
                commentOne.setComment(mComment.getText().toString());



                Date d = new Date();
                if(c==null)
                    c=new ArrayList<>();
                c.add(commentOne);

                Gson gson = new Gson();

                String jsonComment = gson.toJson(c);



//                commentOne.setDate_created(d.toString());
//
//                singleComment.put("username", "\""+"Sam"+"\"");
//                singleComment.put("Comment", "\""+mComment.getText().toString()+"\"");
//                Date d = new Date();
//                singleComment.put("DateCreated", "\""+d.toString()+"\"");
 //               JSONObject jsonObject = new JSONObject();
//                    JSONArray jsonArray = new JSONArray();
//                    JSONObject x = new JSONObject();
//                    x.put("username", "Sam");
//                    x.put("Comment", mComment.getText().toString());
//                    Date d = new Date();
//                    x.put("DateCreated", d.toString());
//                    jsonArray.put(x);
                //    jsonObject.put("comments",commentOne);
                    stream.write(jsonComment.toString().getBytes());

                } catch (Exception e) {
                Log.d(TAG, "writeToJsonFile: Exception in write 1");

                } finally {
                    stream.close();

                }
            } catch (Exception ex) {
            Log.d(TAG, "writeToJsonFile: Exception in write2");
            }
    }

    public ArrayList<Comment> readFile() {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "commentList.json");
//        File file = new File(filePath+"comments.json");

        int length = (int) file.length();

        byte[] bytes = new byte[length];
        try {
            FileInputStream in = new FileInputStream(file);
            try {
                in.read(bytes);
            } finally {
                in.close();
            }

        } catch (Exception e) {
            Log.d(TAG, "readFile: Exception while reading 1");
        }

        String contents = new String(bytes);
        Log.d(TAG, "readFile: " + contents);
        if (!contents.equals("")) {
            ArrayList<Comment> cList = new ArrayList<>();
            try {

                JSONArray obj=new JSONArray(contents);

                for (int i = 0; i < obj.length(); i++) {


                    JSONObject jsonComments = obj.getJSONObject(i);

                    // JSONObject singleObj = jsonComments.get;
                    Comment c = new Comment();
                    c.setUser_id(jsonComments.getString("user_id"));
                 //   c.setDate_created(jsonComments.getString("DateCreated"));
                    c.setComment(jsonComments.getString("comment"));
                    cList.add(c);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "readFile: Exception while reading 2");
            }
            return cList;
        }
        else
            return  null;

        }

    private  ArrayList<Comment> getJSONParse(final String recipeId) {
        String base_url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/recipes/";
        String url = base_url+recipeId+"/comments";
      //  Log.d(TAG, "getJSONParse:comments "+url);
        final ArrayList<Comment> cList = new ArrayList<>();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                         Log.d(TAG, "onResponse:               in json request of comments"+response);
                        try {
                            JSONObject jobj = response.getJSONObject("data");
                           // Log.d(TAG, "onResponse: "+jobj.getS);
                            JSONArray jsonArray=jobj.getJSONArray("comments");
         //                   Log.d(TAG, "onResponse: "+jsonArray);
                            //{"id":1,"comment":"great recipe!","time_added":"2018-06-08T02:57:46.000Z"}]
                            for (int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonComments = jsonArray.getJSONObject(i);

                                // JSONObject singleObj = jsonComments.get;
                                Comment c = new Comment();
                             //   c.setUser_id(jsonComments.getString("user_id"));
                                //   c.setDate_created(jsonComments.getString("DateCreated"));
                                c.setComment(jsonComments.getString("comment"));
                                String dateAdded=jsonComments.getString("time_added");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                Date d=new Date();
                                try {
                                    d = format2.parse(dateAdded);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.setDate_created(d.toString());
                                cList.add(c);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: comment response error");
                error.printStackTrace();
            }
        });

        mQueue.add(request);
        return cList;
    }

    private  void sendJSONParse(final String recipeId) {
        String base_url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/recipes/";
        String url = base_url+recipeId+"/comments";
        Log.d(TAG, "sendJSONParse:comments "+url);

        JSONObject o=new JSONObject();
        JSONObject x=new JSONObject();

        try {
            o.put("comment",mComment.getText().toString());
            x.put("comment",o);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "sendJSONParse: JSON OBJECT"+x);
        final ArrayList<Comment> cList = new ArrayList<>();

        try {

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, x,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse:  comments posted");

                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onsendjsonparse: comment response error");
                    error.printStackTrace();
                }


            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
                    Log.d(TAG, "getHeaders: authentication");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;

                }
            };

            mQueue.add(request);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private  void createUSer() {
        String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/users/token";
      //  String url = base_url+"1"+"/comments";
        Log.d(TAG, "create users "+url);

        JSONObject o=new JSONObject();
        JSONObject x=new JSONObject();

        try {
            o.put("email","imsam.rod@gmail.com");
            o.put("password","123456");
            x.put("user",o);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, x,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse:  users token  in json request of comments"+response);
                            try {
                                JSONObject jobj = response.getJSONObject("data");
                                token=jobj.getString("token");
                                Log.d(TAG, "onResponse: Token="+token);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "getuser: comment response error");
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private  void registerUser() {
        String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/users";
        //  String url = base_url+"1"+"/comments";
        //Log.d(TAG, "sendJSONParse:comments "+url);


        JSONObject o=new JSONObject();
        JSONObject x=new JSONObject();

        try {
            o.put("username","sam");
            o.put("email","imsam.rod@gmail.com");
            o.put("password","123456");
            x.put("user",o);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {


            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, x,
                    new Response.Listener<JSONObject>() {


                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse:  create uuser  in json request of comments"+response);


                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "getuser: comment response error");
                    error.printStackTrace();
                }


            });
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> params = new HashMap<String, String>();
//                    params.put("Content-Type", "application/json");
//                    String creds = String.format("%s:%s","username","password");
//                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//                    params.put("Authorization", auth);
//                    return params;
//
//                }
  //          };

            aQueue.add(request);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}