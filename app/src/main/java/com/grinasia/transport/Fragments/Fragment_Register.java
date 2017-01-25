package com.grinasia.transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grinasia.transport.R;

/**
 * Created by coder on 24-Jan-17.
 */
public class Fragment_Register extends Fragment {
    public Fragment_Register(){
    }

    EditText Username, Password, Confirm_Password;
    Button next, back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//       ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.set_title_register);

        //Username = (EditText) view.findViewById(R.id.eduser);
        //Password = (EditText) view.findViewById(R.id.edPassword);
        //Confirm_Password = (EditText) view.findViewById(R.id.edConfirmPassword);
        //next = (Button) view.findViewById(R.id.btn_next1);
        //back = (Button) view.findViewById(R.id.btn_back1);

        /*next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_register_1, new Register_next());
                ft.commit();
            }
        });*/
        return view;
    }
}
