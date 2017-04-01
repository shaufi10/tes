package com.grinasia.transport.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.grinasia.transport.R;

/**
 * Created by coder on 20-Jan-17.
 */

public class EditProfile extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

    }
}
