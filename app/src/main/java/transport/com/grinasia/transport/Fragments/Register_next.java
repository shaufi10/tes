package transport.com.grinasia.transport.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import transport.com.grinasia.transport.R;

/**
 * Created by coder on 24-Jan-17.
 */
public class Register_next extends Fragment {
    EditText ednama, edalamat, ednomor, ednomorSIM;
    Button back;

    public Register_next() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_next, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.set_tittle_main);
        //ednama = (EditText) view.findViewById(R.id.edName);
        //edalamat = (EditText) view.findViewById(R.id.edAddress);
        //ednomor = (EditText) view.findViewById(R.id.edNumber_telephone);
        //ednomorSIM = (EditText) view.findViewById(R.id.edSIM_phone);
        //back = (Button) view.findViewById(R.id.btn_next2);
        //next = (Button) view.findViewById(R.id.btn_next2);

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.root_frame, new Fragment_Home_1());
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //ft.addToBackStack(null);
                //ft.commit();
            }
        });*/
        return view;
    }
}
