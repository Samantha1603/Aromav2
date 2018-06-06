package com.example.android.aroma.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.aroma.Food;
import com.example.android.aroma.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<FoodViewHolder>{

    Context context;
    List<Food> foodList;

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.food_item,parent,false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(v);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.food_name.setText(foodList.get(position).getName());
        //holder.food_image.setText(foodList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
