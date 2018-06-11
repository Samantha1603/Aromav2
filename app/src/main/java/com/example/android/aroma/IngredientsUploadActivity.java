package com.example.android.aroma;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.aroma.Model.IngredientModel;
import com.example.android.aroma.Utils.CreateCategoryHashMap;
import com.example.android.aroma.Utils.IngredientsCustomAdapter;
import com.example.android.aroma.ViewHolder.FoodListAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientsUploadActivity extends AppCompatActivity {

    private static final String TAG = "IngredientsActivity";


    private Button addIngredients;
    //private TextView selectedIngredients;
    private ListView listView;
    private RequestQueue mQueue;
    private final HashMap<String,String> ingredientHashMap = new HashMap<>();

    private AutoCompleteTextView inputEditText;
    public ArrayList<IngredientModel> ingredientList=new ArrayList<>();


    boolean[] checkedItems;
    String[] ingredients;
    HashMap<Integer,String> selectedItems =new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_upload);
   //     editingredients = (Button) findViewById(R.id.ingredients);
        addIngredients = (Button) findViewById(R.id.add_ingredients);
        inputEditText = (AutoCompleteTextView) findViewById(R.id.editIngredient);
   //     selectedIngredients = (TextView) findViewById(R.id.selectedIngredients);
        ingredients = CreateCategoryHashMap.createCategoryList();
        checkedItems = new boolean[ingredients.length];
        mQueue = Volley.newRequestQueue(this);

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Closing gallery event");
                Intent intent = new Intent(IngredientsUploadActivity.this, CategoryAndTime.class);
                startActivity(intent);
            }
        });

        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: add ingredients WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                AlertDialog.Builder builder = new AlertDialog.Builder(IngredientsUploadActivity.this);
                builder.setTitle(inputEditText.getText().toString());
                // Get the layout inflater
                 LayoutInflater inflater = IngredientsUploadActivity.this.getLayoutInflater();
                                        final View dialogView = inflater.inflate(R.layout.ingredient_search, null);
                                        // Inflate and set the layout for the dialog
                                        // Pass null as the parent view because its going in the dialog layout


                                        builder.setView(dialogView)
                                                // Add action buttons
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // sign in the user ...
                                                        EditText val = (EditText) dialogView.findViewById(R.id.enterQuantity);
                                                        EditText measure = (EditText) dialogView.findViewById(R.id.enterMeasure);
                                                        //   TextView title=(TextView) dialogView.findViewById(R.id.ingredientName);

                                                        IngredientModel ingredientItem = new IngredientModel();
                                                        ingredientItem.setName(inputEditText.getText().toString());
                                                        ingredientItem.setQuantity(val.getText().toString());
                                                        ingredientItem.setMeasure(measure.getText().toString());
                                                        ingredientItem.setId(ingredientHashMap.get(inputEditText.getText().toString()));
                                                        ingredientList.add(ingredientItem);

                                                        listView=(ListView) findViewById(R.id.ingredient_listview);

                                                        IngredientsCustomAdapter adapter=new IngredientsCustomAdapter(IngredientsUploadActivity.this,R.layout.activity_ingredients_upload,ingredientList);
                                                        listView.setAdapter(adapter);
                                                        listView.setScrollContainer(false);
                                                        addIngredients.setText("");
                                                        Button cancel=(Button) findViewById(R.id.cancel);
                                                        Log.d("LIST VIEW","List view added successfully");


                                                    }
                                                })
                                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //LoginDialogFragment.this.getDialog().cancel();
//                                                        selectedItems.remove(selectedI);
//                                                        checkedItems[selectedI] = false;
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();


            }
        });


        TextView upload = (TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listView.getAdapter().getCount() == 0) {
//                /    Log.d(TAG, "next screen" + selectedIngredients.getText());
                    Toast.makeText(IngredientsUploadActivity.this, "Select at least one ingredient", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(IngredientsUploadActivity.this, DescriptionUpload.class);
                    Intent intentOld = getIntent();
                    if (intentOld.hasExtra(getString(R.string.selected_image))) {
                        String imgUrl;
                        imgUrl = intentOld.getStringExtra(getString(R.string.selected_image));
                        intent.putExtra("selected_image", imgUrl);

                    } else if (intentOld.hasExtra(getString(R.string.selected_bitmap))) {
                        Bitmap bitmap;
                        bitmap = intentOld.getParcelableExtra(getString(R.string.selected_bitmap));
                        intent.putExtra("selected_bitmap", bitmap);
                    }
                    intent.putExtra("Title", intentOld.getStringExtra("Title"));
                    intent.putExtra("Category", intentOld.getStringExtra("Category"));
                    intent.putExtra("Time Duration", intentOld.getStringExtra("Time Duration"));
                    intent.putExtra("Servings", intentOld.getStringExtra("Servings"));

//                    for (int u = 0; u < listView.getAdapter().getCount(); u++) {
//
//                        Log.d(TAG, "onClick: " + listView.getAdapter().getItem(u).toString());
//
//                    }
//
//
                    Gson gson = new Gson();

                    String jsonIngredient = gson.toJson(ingredientList);
                    Log.d(TAG, "onClick: ingredientlist +"+jsonIngredient);
                    intent.putExtra("Ingredients", jsonIngredient);


                    startActivity(intent);
                }
            }
        });
        final ArrayList<String> responseList = new ArrayList<>();
        String url ="http://aroma-env.wv5ap2cp4n.us-west-1.elasticbeanstalk.com/ingredients";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject jobj = response.getJSONObject("data");
                            JSONArray jsonArray = jobj.getJSONArray("ingredients");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject ingredients = jsonArray.getJSONObject(i);
                                String name = ingredients.getString("name");
                                responseList.add(name);
                                ingredientHashMap.put(name,ingredients.getString("id").toString());
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
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, responseList);
        inputEditText.setAdapter(adapter);
    }
}
