package com.example.android.aroma.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.aroma.Food;
import com.example.android.aroma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodvHolder> {
    private Context mContext;
    private ArrayList<Food> foodList;
    private OnItemClickListener mListener;

    public FoodListAdapter(Context context, ArrayList<Food> exampleList) {
        mContext = context;
        foodList = exampleList;
    }

    @Override
    public FoodvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.food_item, parent, false);
        return new FoodvHolder(v);
    }

    @Override
    public void onBindViewHolder(FoodvHolder holder, int position) {
        Food currentItem = foodList.get(position);

        String imageUrl = currentItem.getImage();
        String name = currentItem.getName();
        String time = currentItem.getTime();
        String serves = currentItem.getServings();
        String ingredients = currentItem.getIngredients();
        String directions = currentItem.getDirections();
        //int likeCount = currentItem.getLikeCount();

        holder.mTextViewName.setText(name);
        //holder.mTextViewLikes.setText("Likes: " + likeCount);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class FoodvHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewName;
        //public TextView mTextViewLikes;

        public FoodvHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.food_image);
            mTextViewName = itemView.findViewById(R.id.food_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}