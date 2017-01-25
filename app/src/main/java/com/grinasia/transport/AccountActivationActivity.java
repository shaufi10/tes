package com.grinasia.transport;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Service.VolleyServices;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.VolleyCallback;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coder on 24-Jan-17.
 */

public class AccountActivationActivity extends AppCompatActivity {
    private EditText txtActivationCode;
    private Button btnActivateAccount;

    private ProgressDialog activateLoading;

    private VolleyCallback volleyCallback;
    private VolleyServices volleyServices;
    private SharedPreferencesUtils userDataSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activation);

        txtActivationCode = (EditText)findViewById(R.id.txtActivationCode);
        btnActivateAccount = (Button)findViewById(R.id.btnActivateAccount);

        activateLoading = new ProgressDialog(this);

        activateLoading.setTitle("Activate Account");
        activateLoading.setMessage("Activating Your Account. . .");
        activateLoading.setCancelable(false);

        btnActivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtActivationCode.getText().toString())){
                    Toast.makeText(AccountActivationActivity.this, "Insert Your Activation Code", Toast.LENGTH_SHORT).show();
                } else {
                    activatingUser(txtActivationCode.getText().toString());
                }
            }
        });
    }


    private void initVolleyCallback(){
        volleyCallback = new VolleyCallback() {
            @Override
            public void onSuccessCallback(String response) {
                activateLoading.dismiss();

                new AlertDialog.Builder(AccountActivationActivity.this)
                        .setTitle("User Activation")
                        .setMessage("Your account has been activated")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AccountActivationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }

            @Override
            public void onErrorCallback(VolleyError error) {
                activateLoading.dismiss();

                new AlertDialog.Builder(AccountActivationActivity.this)
                        .setTitle("User Activation")
                        .setMessage("Oopss...Something went wrong, Please Try Again Later")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }

            @Override
            public void onResponseHeaderCallback(Map<String, String> headers) {

            }
        };
    };

    private void activatingUser(String data){
        try {
            activateLoading.show();

            URL baseUrl = new URL(BaseConfig.getBaseUrl() + "activateAccount/" + data);

            userDataSharedPreferences = new SharedPreferencesUtils(AccountActivationActivity.this, "UserData");

            initVolleyCallback();

            volleyServices = new VolleyServices(volleyCallback, AccountActivationActivity.this);

            HashMap<String, String> customHeaders = new HashMap<>();

            if (userDataSharedPreferences.checkIfDataExists("AT")) {
                customHeaders.put("Authorization", userDataSharedPreferences.getPreferenceData("AT"));
            }

            volleyServices.getStringRequestData(Request.Method.GET, customHeaders, baseUrl.toURI().toString());
        } catch (Exception e) {
            Log.e("registeringUser: ", e.getMessage());
        }
    }
}


