package com.grinasia.transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.grinasia.transport.R;

public class Fragment_Register extends Fragment {

    private EditText txtRegion;
    private EditText txtEmail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtConfirm_Password;

    public Fragment_Register(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        txtRegion = (EditText) view.findViewById(R.id.txtRegion_profile);
        txtEmail = (EditText) view.findViewById(R.id.txtEmailAddress);
        txtUser = (EditText) view.findViewById(R.id.txtUsername);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtConfirm_Password = (EditText) view.findViewById(R.id.txtConfirmPassword);

        return view;
    }
}
