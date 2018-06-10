package com.example.android.aroma.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.aroma.BaseActivity;
import com.example.android.aroma.UploadActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.android.aroma.R;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class GridImageAdapter extends ArrayAdapter<String>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context mContext, int layoutResource, String mAppend, ArrayList<String> imgURLs) {
        super(mContext,layoutResource,imgURLs);
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutResource = layoutResource;
        this.mAppend = mAppend;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder{
        SquareImageView image;
        ProgressBar progressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView==null)
        {
            convertView=mInflater.inflate(layoutResource,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.progressBar=(ProgressBar) convertView.findViewById(R.id.gridImageProgressbar);
            viewHolder.image=(SquareImageView) convertView.findViewById(R.id.gridImageView);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        String imgURL=getItem(position);

        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        if(imageLoader.isInited())
            Log.d("GridImageAdapter","Image loader is initiated:");
        else
            Log.d("GridImageAdapter","Image loader is not initiated:");
        imageLoader.displayImage(mAppend + imgURL, viewHolder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.progressBar!=null)
                {
                    viewHolder.progressBar.setVisibility(View.VISIBLE)  ;
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.progressBar!=null)
                {
                    viewHolder.progressBar.setVisibility(View.INVISIBLE)  ;
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.progressBar!=null)
                {
                    viewHolder.progressBar.setVisibility(View.INVISIBLE)  ;
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.progressBar!=null)
                {
                    viewHolder.progressBar.setVisibility(View.INVISIBLE)  ;
                }
            }
        });

        return convertView;
    }
}

