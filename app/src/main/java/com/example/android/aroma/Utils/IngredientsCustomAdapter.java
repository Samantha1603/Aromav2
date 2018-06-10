package com.example.android.aroma.Utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.aroma.Model.IngredientModel;
import com.example.android.aroma.R;

import java.util.ArrayList;

public class IngredientsCustomAdapter extends ArrayAdapter<IngredientModel> {

    private static final String TAG = "IngredientsCustomAdapte";
    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;
    private ViewHolder viewHolder;

        private static class ViewHolder {
            private TextView name;
            private EditText value;
            private EditText measure;

        }

        public IngredientsCustomAdapter(Context context,  @LayoutRes int resource,ArrayList<IngredientModel> items) {
            super(context, resource, items);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mContext = context;
            layoutResource = resource;
        }


        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(this.getContext())
                        .inflate(R.layout.ingredient_list_item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.ingredientName);
                viewHolder.value = (EditText) convertView.findViewById(R.id.ingredientValue);
                viewHolder.measure = (EditText) convertView.findViewById(R.id.ingredientMeasure);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            IngredientModel item = getItem(position);
            if (item!= null) {
                // My layout has only one TextView
                // do whatever you want with your string and long
                Log.d("item",item.toString());
                Log.d("item name",item.getName());
                viewHolder.name.setText(item.getName().toString());
                viewHolder.value.setText(item.getQuantity());
                viewHolder.value.setEnabled(false);
                viewHolder.measure.setText(item.getMeasure());
                viewHolder.measure.setEnabled(false);
            }

            return convertView;
        }
    }
