package transport.com.grinasia.transport.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import transport.com.grinasia.transport.LoginActivity;
import transport.com.grinasia.transport.R;

/**
 * Created by coder on 29-Dec-16.
 */
public class Fragment_Opsi extends Fragment {

    public Fragment_Opsi() {
    }

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.opsi, container, false);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.set_tittle_main);
        //final Button view_detail = (Button) view.findViewById(R.id.view_detail);
        context = view.getContext();

        final TextView status = (TextView) view.findViewById(R.id.notifdata);
        final Switch notification = (Switch) view.findViewById(R.id.switch_notification);
        final Button exit = (Button) view.findViewById(R.id.sign_out);

        notification.setChecked(true);

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    status.setText("Notification Data currently: ON");
                } else {
                    status.setText("Notification Data currently: OFF");
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}