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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.grinasia.transport.R;
import com.grinasia.transport.Register;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coder on 24-Jan-17.
 */
public class Register_next extends Fragment {

    private EditText txtfirstname;
    private EditText txtlastname;
    private Spinner  spnGender;
    private EditText txtAddress;
    private EditText txtdate;
    private EditText txtphone;
    private EditText txtSIM;

    public Register_next() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_next, container, false);

        txtfirstname = (EditText) view.findViewById(R.id.edFirstName);
        txtlastname = (EditText) view.findViewById(R.id.edLastName);
        spnGender = (Spinner) view.findViewById(R.id.spnGender);
        txtAddress = (EditText) view.findViewById(R.id.edAddress);
        txtdate = (EditText) view.findViewById(R.id.txtDOB);
        txtphone = (EditText) view.findViewById(R.id.edNumber_telephone);
        txtSIM = (EditText) view.findViewById(R.id.edSIM);

        List<String> gender = new ArrayList<>();
        gender.add("Pria");
        gender.add("Wanita");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnGender.setAdapter(genderAdapter);

        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setFocusable(true);
                DialogFragment dialogFragment = new DOBFragment();
                if (!TextUtils.isEmpty(txtdate.getText().toString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("dob", txtdate.getText().toString());
                    dialogFragment.setArguments(bundle);
                }
                dialogFragment.setTargetFragment(Register_next.this, 25);
                dialogFragment.show(getFragmentManager(), "datePicker");
            }
        });

        txtdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogFragment dialogFragment = new DOBFragment();
                    if (!TextUtils.isEmpty(txtdate.getText().toString())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dob", txtdate.getText().toString());
                        dialogFragment.setArguments(bundle);
                    }
                    dialogFragment.setTargetFragment(Register_next.this, 25);
                    dialogFragment.show(getFragmentManager(), "datePicker");
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult (int requestcode, int resultCode, Intent data) {
        if (requestcode == 25) {
            String dob_formatted = data.getStringExtra("date_selected_dob_formatted");
            txtdate.setText(dob_formatted);
        }
    }
}
