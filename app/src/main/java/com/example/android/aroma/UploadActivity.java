package com.example.android.aroma;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.aroma.Utils.Permissions;
import com.example.android.aroma.Utils.SectionsPagerAdapter;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG="UploadActivity";
    public static final int VERIFY_PERMISSION_REQUEST_CODE = 1;

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Log.d(TAG,"in on create");
        if(checkPermissionsArray(Permissions.PERMISSIONS))
        {
            Log.d(TAG,"check");
            setupViewPager();
        }
        else
        {
            Log.d(TAG,"verify");
            verifyPermissions(Permissions.PERMISSIONS);
            setupViewPager();
        }
    }

    public int getTask()
    {
       return getIntent().getFlags();
    }
    public int getCurrentTabNumber()
    {
        return viewPager.getCurrentItem();
    }
    private void setupViewPager()
    {
        Log.d(TAG,"setup view pager");
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_gallery());
        adapter.addFragment(new fragment_photo());
        viewPager=(ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("GALLERY");
        tabLayout.getTabAt(1).setText("PHOTO");
    }

    private void verifyPermissions(String[] permissions)
    {
        Log.d(TAG,"verifyPermissionsArray");
        ActivityCompat.requestPermissions(UploadActivity.this,permissions, VERIFY_PERMISSION_REQUEST_CODE);

    }

    /* check array of permission*/
    private boolean checkPermissionsArray(String[] permissions)
    {
        Log.d(TAG,"checkPErmissionsArray");
        for(int i=0;i<permissions.length;i++)
        {
            String check=permissions[i];
            if(checkPermissions(check))
            {
                return false;
            }
        }
        return true;
    }

    /* check single permission*/
    public boolean checkPermissions(String check)
    {
        Log.d(TAG,"check single PErmission");
        int permissionRequest= ActivityCompat.checkSelfPermission(UploadActivity.this,check);
        if(permissionRequest!= PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG,"check PErmission\nPermission was not granted for:"+check);
            return false;
        }
        else
        {
            Log.d(TAG,"check PErmission\nPermission was granted for:"+check);
            return true;
        }

    }

    /**
     * BottomNavigationView setup
     */
//    private void setupBottomNavigationView(){
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }


}
