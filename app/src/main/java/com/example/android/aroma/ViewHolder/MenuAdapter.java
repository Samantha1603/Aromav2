package com.example.android.aroma.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.aroma.Category;
import com.example.android.aroma.FoodList;
import com.example.android.aroma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuvHolder> {
    private Context mContext;
    private ArrayList<Category> catList;
    private OnItemClickListener mListener;

    public MenuAdapter(Context context, ArrayList<Category> exampleList) {
        mContext = context;
        catList = exampleList;
    }

    @Override
    public MenuvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
        return new MenuvHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuvHolder holder, int position) {
        Category currentItem = catList.get(position);

       String imageUrl = currentItem.getImage();
        String categoryName = currentItem.getName();
        //int likeCount = currentItem.getLikeCount();

        holder.mTextViewName.setText(categoryName);
       //holder.mTextViewLikes.setText("Likes: " + likeCount);
        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MenuvHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewName;
        //public TextView mTextViewLikes;

        public MenuvHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.menu_image);
            mTextViewName = itemView.findViewById(R.id.menu_name);
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