package com.example.android.aroma.Utils;

import android.os.Environment;

public class FilePaths
{
    public String ROOT_DIR= Environment.getExternalStorageDirectory().getPath();

    public String CAMERA= ROOT_DIR+"/DCIM/Camera";
    public String Pictures= ROOT_DIR+"/Pictures";


}
