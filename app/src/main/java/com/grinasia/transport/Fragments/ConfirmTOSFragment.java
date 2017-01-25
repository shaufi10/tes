package com.grinasia.transport.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.grinasia.transport.R;

/**
 * Created by coder on 24-Jan-17.
 */

public class ConfirmTOSFragment extends Fragment{
    TextView txtTOS;

    public ConfirmTOSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_confirm_tos, container, false);

        txtTOS = (TextView)view.findViewById(R.id.txtTOS);

        txtTOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.tos_layout);
                dialog.setTitle(getString(R.string.tos));

                Button btnCloseDialog = (Button)dialog.findViewById(R.id.btnClose);

                dialog.show();

                btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
