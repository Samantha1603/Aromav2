package com.example.android.aroma.Utils;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android.aroma.R;

import java.util.ArrayList;

public class ViewCommentsFragment extends Fragment
{

    private static final String TAG = "ViewCommentsFragment";

    private ImageView mBackArrow,mCheckMark;
    private EditText mComment;
    ArrayList<String> mComments=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_view_comment,container,false);
        mBackArrow=(ImageView) view.findViewById(R.id.backArrow);
        //mCheckMark=(ImageView) view.findViewById(R.id.);
        mBackArrow=(ImageView) view.findViewById(R.id.backArrow);
        return view;
    }

    private void setupWidgets()
    {
        String firstComment="Wow";
        mComments.add(firstComment);

    }
}
