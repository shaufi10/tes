package transport.com.grinasia.travel_transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import transport.com.grinasia.travel_transport.R;

/**
 * Created by coder on 29-Dec-16.
 */
public class Fragment_Informasi extends Fragment {

    public Fragment_Informasi(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.informasi, container, false);


        return view;
    }
}
