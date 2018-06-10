package com.example.android.aroma;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.aroma.Utils.Permissions;

public class fragment_photo extends Fragment {

    private static final String TAG="Fragment Photo";
    public static final int PHOTO_FRAGMENT_NUMBER = 1;
    public static final int CAMERA_REQUEST_CODE = 5;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_fragment_photo,container,false);
        Button btnLauchCamera=(Button) view.findViewById(R.id.buttonLaunchCamera);
        btnLauchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick:launching camera");

                if(((UploadActivity)getActivity()).getCurrentTabNumber()== PHOTO_FRAGMENT_NUMBER)
                {
                    if(((UploadActivity)getActivity()).checkPermissions(Permissions.CAMERAPERMISSIONS[0]))
                    {
                        Log.d(TAG,"onclick:starting camera");
                        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                    }
                    else
                    {
                        Intent intent=new Intent(getActivity(),UploadActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }

                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE)
        {
            Bitmap bitmap;
            bitmap= (Bitmap) data.getExtras().get("data");
            if(isRootTask())
            {

                try {
                    Log.d(TAG,"received new bitmap from camera is root"+bitmap);
                    Intent intent=new Intent(getActivity(),UploadImageActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap),bitmap);
                    startActivity(intent);
                }
                catch (NullPointerException e)
                {
                    Log.d(TAG,"null pointer exception"+e.getMessage());

                }
            }
            else
            {
                try {
                    Log.d(TAG,"received new bitmap from camera"+bitmap);
                    Intent intent=new Intent(getActivity(),UploadImageActivity.class);
                    intent.putExtra(getString(R.string.selected_bitmap),bitmap);
                    intent.putExtra("Return to fragment","Edit Profile Fragmet");
                    startActivity(intent);
                    getActivity().finish();

                }
                catch (NullPointerException e)
                {
                    Log.d(TAG,"null pointer exception"+e.getMessage());

                }
            }



        }
    }

    private boolean isRootTask()
    {
        Log.d(TAG,"(UploadActivity)getActivity()).getTask()="+((UploadActivity)getActivity()).getTask());
        if(((UploadActivity)getActivity()).getTask()==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
