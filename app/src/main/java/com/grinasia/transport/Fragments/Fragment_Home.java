package com.grinasia.transport.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grinasia.transport.Detail_Order_Aktif;
import com.grinasia.transport.R;

/**
 * Created by coder on 29-Dec-16.
 */
public class Fragment_Home extends Fragment {

    public Fragment_Home(){
    }

    Activity context;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        final Button view_detail = (Button) view.findViewById(R.id.detail);
        view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context, Detail_Order_Aktif.class);
                startActivity(a);
            }
        });
        return view;
    }
}
