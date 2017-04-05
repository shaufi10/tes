package com.grinasia.transport.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.grinasia.transport.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegisterProfile extends Fragment {

    private EditText txtFirstName;
    private EditText txtLastName;
    private Spinner  spnGender;

    private EditText txtAddress;
    private EditText txtDOB;
    private EditText txtPhoneNumber;
    private EditText txtSIM_Number;

    public RegisterProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_next, container, false);

        txtFirstName = (EditText) view.findViewById(R.id.txtFirstName_register);
        txtLastName = (EditText) view.findViewById(R.id.txtLastName_register);
        spnGender = (Spinner) view.findViewById(R.id.spnGender1);
        txtAddress = (EditText) view.findViewById(R.id.txtAddress_register);
        txtDOB = (EditText) view.findViewById(R.id.txtDOB_register);
        txtPhoneNumber = (EditText) view.findViewById(R.id.txtNumberPhone_register);
        txtSIM_Number = (EditText) view.findViewById(R.id.txtSIM_register);

        List<String> gender = new ArrayList<>();

        gender.add("Pria");
        gender.add("Wanita");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnGender.setAdapter(genderAdapter);

        

        txtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDOB.setFocusable(true);
                DialogFragment dateFragment = new DOBPickerFragment();
                if (!TextUtils.isEmpty(txtDOB.getText().toString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("dob", txtDOB.getText().toString());
                    dateFragment.setArguments(bundle);
                }
                dateFragment.setTargetFragment(RegisterProfile.this, 25);
                dateFragment.show(getFragmentManager(), "datePicker");
            }
        });

        txtDOB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment dateFragment = new DOBPickerFragment();
                    if (!TextUtils.isEmpty(txtDOB.getText().toString())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dob", txtDOB.getText().toString());
                        dateFragment.setArguments(bundle);
                    }
                    dateFragment.setTargetFragment(RegisterProfile.this, 25);
                    dateFragment.show(getFragmentManager(), "datePicker");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult (int requestcode, int resultCode, Intent data) {
        if (requestcode == 25) {
            String dob_formatted = data.getStringExtra("date_selected_dob_formatted");
            txtDOB.setText(dob_formatted);
        }
    }
}
