package com.example.android.aroma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android.aroma.Model.Comment;
import com.example.android.aroma.Utils.CommentListAdapter;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommentLayout extends AppCompatActivity {

    private static final String TAG = "CommentLayout";

    private ImageView mBackArrow,mCheckMark;
    private EditText mComment;
    private ArrayList<Comment> mComments;
    private ListView mListView;


    private String filePath="MyFileStorage";
    private String fileName="comments.json";
    File myFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mBackArrow=(ImageView) findViewById(R.id.backArrow);
        mCheckMark=(ImageView) findViewById(R.id.ivPostComment);
        mComment=(EditText) findViewById(R.id.comment);
        mListView=(ListView) findViewById(R.id.listViewC);
        mComments=new ArrayList<>();

        Comment c=new Comment();
        c.setComment("as");
        Date d=new Date();
        c.setDate_created(d.toString());
        c.setUser_id("Sam");

        Comment c1=new Comment();
        c1.setComment("as");
        c1.setDate_created(d.toString());
        c1.setUser_id("Sam");
        //mComments.add(c);
       // mComments.add(c1);
        mComments=readFile();
        CommentListAdapter adapter=new CommentListAdapter(CommentLayout.this,R.layout.layout_comment,mComments);
        mListView.setAdapter(adapter);

        mCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToJsonFile();
                Log.d(TAG, "onClick: comments saved");
                Intent intent=new Intent(CommentLayout.this,CommentLayout.class);
                startActivity(intent);
            }
        });

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CommentLayout.this,RecipeDetails.class);
                startActivity(intent);
            }
        });



    }


    public void writeToJsonFile()
    {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path, "commentList.json");
        boolean exits=false;
        if(file.exists())
        {
            Log.d(TAG, "writeFile: FILE ALREADY CREATED");
            exits=true;
            file.delete();
            Log.d(TAG, "writeToJsonFile: FILE DELETED!!!!");
            file=new File(path, "commentList.json");


        }
        try {
            FileOutputStream stream = new FileOutputStream(file,exits);
            try {
                Log.d(TAG, "writeToJsonFile: In hereeeeeeeee");
                //HashMap<String,String> singleComment=new HashMap<>();
                Comment commentOne=new Comment();
                commentOne.setUser_id("Sam");
                commentOne.setComment(mComment.getText().toString());
                Date d = new Date();
//                commentOne.setDate_created(d.toString());
//
//                singleComment.put("username", "\""+"Sam"+"\"");
//                singleComment.put("Comment", "\""+mComment.getText().toString()+"\"");
//                Date d = new Date();
//                singleComment.put("DateCreated", "\""+d.toString()+"\"");
                JSONObject jsonObject = new JSONObject();
//                    JSONArray jsonArray = new JSONArray();
//                    JSONObject x = new JSONObject();
//                    x.put("username", "Sam");
//                    x.put("Comment", mComment.getText().toString());
//                    Date d = new Date();
//                    x.put("DateCreated", d.toString());
//                    jsonArray.put(x);
                //    jsonObject.put("comments",commentOne);
                    stream.write(commentOne.toString().getBytes());

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

                    JSONObject obj = new JSONObject(contents);
                    for (int i = 0; i < obj.length(); i++) {

                    String x = obj.getString("Comment");
                    x = x.replace("\\", "");
                    JSONObject jsonComments = new JSONObject(x);

                    // JSONObject singleObj = jsonComments.get;
                    Comment c = new Comment();
                    c.setUser_id(jsonComments.getString("username"));
                    c.setDate_created(jsonComments.getString("DateCreated"));
                    c.setComment(jsonComments.getString("Comment"));
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
}