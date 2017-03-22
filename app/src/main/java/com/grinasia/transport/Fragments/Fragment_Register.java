package com.grinasia.transport.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.grinasia.transport.R;

/**
 * Created by coder on 24-Jan-17.
 */
public class Fragment_Register extends Fragment {

    private EditText txtRegion;
    private EditText txtEmail;
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirm_Password;

    public Fragment_Register(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        txtRegion = (EditText) view.findViewById(R.id.edKotamadya);
        txtEmail = (EditText) view.findViewById(R.id.edEmailAddress);
        txtUsername = (EditText) view.findViewById(R.id.ed_username);
        txtPassword = (EditText) view.findViewById(R.id.edPassword);
        txtConfirm_Password = (EditText) view.findViewById(R.id.edConfirmPassword);

        //Intent intent = getActivity().getIntent();

        return view;
    }
}
