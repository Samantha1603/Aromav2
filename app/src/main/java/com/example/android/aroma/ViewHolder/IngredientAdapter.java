package com.example.android.aroma.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.aroma.Ingredients;
import com.example.android.aroma.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends ArrayAdapter {

    List list = new ArrayList<>();
    public IngredientAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    public void add(@Nullable Ingredients object) {
        super.add(object);
        list.add(object);

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        IngredientsHolder ingredientsHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       row=layoutInflater.inflate(R.layout.list_item_ingredients,parent,false);
            ingredientsHolder=new IngredientsHolder();
            ingredientsHolder.txtName=row.findViewById(R.id.album_name);
            ingredientsHolder.txtQuantity=row.findViewById(R.id.album_id);

            ingredientsHolder.txtUnit=row.findViewById(R.id.songs_count);
            row.setTag(ingredientsHolder);
        }
        else
        {
            ingredientsHolder=(IngredientsHolder) row.getTag();
        }
        Ingredients ingredients = (Ingredients) this.getItem(position);
        ingredientsHolder.txtName.setText(ingredients.getName());
        ingredientsHolder.txtQuantity.setText(ingredients.getQuantity());
        ingredientsHolder.txtUnit.setText(ingredients.getUnit());
        return row;
    }
    static class IngredientsHolder{
       TextView txtName,txtQuantity,txtUnit;

    }
}
