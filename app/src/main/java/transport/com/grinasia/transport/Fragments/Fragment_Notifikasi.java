package transport.com.grinasia.transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import transport.com.grinasia.transport.R;

/**
 * Created by coder on 29-Dec-16.
 */
public class Fragment_Notifikasi extends Fragment {

    public Fragment_Notifikasi(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifikasi, container, false);

        return view;
    }
}
