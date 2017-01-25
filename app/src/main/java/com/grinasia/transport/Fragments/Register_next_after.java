package com.grinasia.transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.grinasia.transport.R;

/**
 * Created by coder on 24-Jan-17.
 */
public class Register_next_after extends Fragment {
    private EditText ednama_company, edalamat, ednomorTelephone, edNumberakte, edNumberSIUP, ednumberTDP,
            ednumberNPWP;
    private Button back, next;
    private Spinner spinner;

    public Register_next_after() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_next_after, container, false);
        /*ednama_company = (EditText) view.findViewById(R.id.edName_Company);
        edalamat = (EditText) view.findViewById(R.id.edAddress_Company);
        ednomorTelephone = (EditText) view.findViewById(R.id.edNumber_telephone);
        edNumberakte = (EditText) view.findViewById(R.id.edNumber_Akte);
        edNumberSIUP = (EditText) view.findViewById(R.id.edNumber_SIUP);
        ednumberTDP = (EditText) view.findViewById(R.id.edNumber_TDP);
        ednumberNPWP = (EditText) view.findViewById(R.id.edNumber_NPWP);*/
        //spinner = (Spinner) view.findViewById(R.id.spinner1);

        //String[] value = {"Perorangan", "Perusahaan"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, category);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(adapter);

        return view;
    }
}

