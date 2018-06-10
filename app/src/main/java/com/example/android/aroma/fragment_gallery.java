package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.aroma.Utils.FilePaths;
import com.example.android.aroma.Utils.FileSearch;
import com.example.android.aroma.Utils.GridImageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class fragment_gallery extends Fragment {

    private static final String TAG="Fragment Gallery";
    public static final int NUM_GRID_COLUMNS = 3;
    //public String mAppend = "http://";
    public String mAppend = "file://";
    public String mSelectedImage;

    private GridView gridView;
    private ImageView galleryView;
    private ProgressBar mProgressBar;
    private Spinner directorySpinner;
    private ArrayList<String> directories;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_fragment_gallery,container,false);
        galleryView=(ImageView) view.findViewById(R.id.galleryImageView);
        gridView=(GridView) view.findViewById(R.id.gridView);
        directorySpinner=(Spinner) view.findViewById(R.id.spinnerDirectory);
        mProgressBar=(ProgressBar) view.findViewById(R.id.galleryProgressBar);
        mProgressBar.setVisibility(View.GONE);
        directories=new ArrayList<>();

        Log.d(TAG,"gallery create view");

        ImageView shareClose=(ImageView) view.findViewById(R.id.shareClose);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Closing gallery event");
                getActivity().finish();
            }
        });

        TextView nextScreen=(TextView) view.findViewById(R.id.Next);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"next screen");
                Intent intent=new Intent(getActivity(),UploadImageActivity.class);
                intent.putExtra(getString(R.string.selected_image),mSelectedImage);
                startActivity(intent);

            }
        });

        init();
        return view;
    }

    private void init()
    {
        Log.d(TAG,"in directory spinner");
        FilePaths filePaths=new FilePaths();
        if(FileSearch.getDirectory(filePaths.Pictures)!=null)
        {
            directories=FileSearch.getDirectory(filePaths.Pictures);
        }
        directories.add(filePaths.CAMERA);

        ArrayList<String> directoryNames=new ArrayList<>();
        for(int i=0;i<directories.size();i++)
        {
            int index=directories.get(i).lastIndexOf("/");
            String string=directories.get(i).substring(index+1);
            Log.d(TAG,"STRING="+string);
            directoryNames.add(string);
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"on item clicked in spinner"+directories.get(i));
                setupGridView(directories.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setupGridView(String selectedDirectory)
    {
        Log.d(TAG,"setupGrid: directory choosen"+selectedDirectory);
        final ArrayList<String> imgURLs= FileSearch.getFilePaths(selectedDirectory);

        //set grid column width
        int gridWidth=getResources().getDisplayMetrics().widthPixels;
        int imageWidth=gridWidth/ NUM_GRID_COLUMNS;

        gridView.setColumnWidth(imageWidth);

//        use grid adapter to adapt images to the grid view
        GridImageAdapter adapter=new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview,mAppend,imgURLs);
        gridView.setAdapter(adapter);

        if(imgURLs.size()!=0) {
            setImage(imgURLs.get(0), galleryView, mAppend);
            mSelectedImage=imgURLs.get(0);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"onItemClick: selected an image:"+imgURLs.get(i));

                setImage(imgURLs.get(i),galleryView,mAppend);
                mSelectedImage=imgURLs.get(i);
            }
        });
    }

    private void setImage(String imgURL, ImageView image,String append)
    {
        Log.d(TAG,"set imahe: setting image");
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        imageLoader.displayImage(append + imgURL, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
