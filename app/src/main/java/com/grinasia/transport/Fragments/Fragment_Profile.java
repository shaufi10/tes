package com.grinasia.transport.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grinasia.transport.R;

/**
 * Created by coder on 29-Dec-16.
 */
public class Fragment_Profile extends Fragment {

    public Fragment_Profile(){
    }

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile, container, false);

        context = view.getContext();


        return view;
    }
}
