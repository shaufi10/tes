package com.grinasia.transport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by coder on 20-Jan-17.
 */

public class EditProfile extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        username = (EditText) findViewById(R.id.ed_username);
        password = (EditText) findViewById(R.id.ed_password_profile);

        username.setText("");
        password.setText("");
    }
}
