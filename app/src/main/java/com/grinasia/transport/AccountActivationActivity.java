package com.grinasia.transport;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Service.VolleyServices;
import com.grinasia.transport.User.LoginActivity;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.VolleyCallback;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class AccountActivationActivity extends AppCompatActivity {
    private EditText txtActivationCode;
    private TextView resendActivationCodeToEmail;
    private Button btnActivateAccount;

    private ProgressDialog activateLoading;
    private ProgressDialog resendLoading;

    private VolleyCallback volleyCallbackUserActivation;
    private VolleyCallback volleyCallbackResendActivationCode;

    private VolleyServices volleyServicesUserActivation;
    private VolleyServices volleyServicesResendActivationCode;

    private SharedPreferencesUtils userDataSharedPreferences;

    private BroadcastReceiver messageReceiver;
    private final String TAG = this.getClass().getSimpleName();
    private IntentFilter messageIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activation);

        txtActivationCode = (EditText)findViewById(R.id.txtActivationCode);
        resendActivationCodeToEmail = (TextView) findViewById(R.id.resendActivationCodeToEmail);
        btnActivateAccount = (Button)findViewById(R.id.btnActivateAccount);

        activateLoading = new ProgressDialog(AccountActivationActivity.this);
        resendLoading = new ProgressDialog(AccountActivationActivity.this);

        activateLoading.setTitle("Activate Account");
        activateLoading.setMessage("Activating Your Account. . .");
        activateLoading.setCancelable(false);

        activateLoading.setTitle("Resend Activation Code");
        activateLoading.setMessage("Resending your activation code to email. . .");
        activateLoading.setCancelable(false);

        messageIntentFilter = new IntentFilter();
        messageIntentFilter.addAction("android.provide.Telephony.SMS_RECEIVED");

        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();

                String strMessage = "";

                if (extras != null){
                    Object[] smsextras = (Object[]) extras.get("pdus");

                    for (int i = 0; i < smsextras.length; i++){

                        SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                        String strMsgBody = smsmsg.getMessageBody().toString();
                        String strMsgSrc = smsmsg.getDisplayOriginatingAddress();

                        if (strMsgSrc.equals("GLOBALSMS")){
                            String[] splitMsg = strMsgBody.split(": ");
                            if (splitMsg[0].equals("Grinasia")) {
                                txtActivationCode.setText(splitMsg[2]);
                            }
                        }
                    }
                }
            }
        };

        registerReceiver(messageReceiver, messageIntentFilter);

        resendActivationCodeToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdata = getIntent();
                if (intentdata.hasExtra("userSignUpData")) {
                    resendActivationCodeToEmail(intentdata.getStringExtra("userSignUpData"));
                }
            }
        });
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


    private void initVolleyCallbackUserActivation(){
        volleyCallbackUserActivation = new VolleyCallback() {
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

    private void initVolleyCallbackResendActivationCode() {
        volleyCallbackResendActivationCode = new VolleyCallback() {
            @Override
            public void onSuccessCallback(String response) {
                resendLoading.dismiss();

                new AlertDialog.Builder(AccountActivationActivity.this)
                        .setTitle("Resend Activation Code")
                        .setMessage("Please check your mail inbox/spam folder")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }

            @Override
            public void onErrorCallback(VolleyError error) {
                resendLoading.dismiss();

                new AlertDialog.Builder(AccountActivationActivity.this)
                        .setTitle("Resend Activation Code")
                        .setMessage("Resend Activation Code To Email Failed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

            }

            @Override
            public void onResponseHeaderCallback(Map<String, String> header) {

            }
        };
    };

    private void activatingUser(String data){
        try {
            activateLoading.show();

            URL baseUrl = new URL(BaseConfig.getBaseUrl() + "activateAccount/" + data);

            userDataSharedPreferences = new SharedPreferencesUtils(AccountActivationActivity.this, "UserData");

            initVolleyCallbackUserActivation();

            volleyServicesUserActivation= new VolleyServices(volleyCallbackUserActivation, AccountActivationActivity.this);

            HashMap<String, String> customHeaders = new HashMap<>();

            if (userDataSharedPreferences.checkIfDataExists("AT")) {
                customHeaders.put("Authorization", userDataSharedPreferences.getPreferenceData("AT"));
            }

            volleyServicesUserActivation.getStringRequestData(Request.Method.GET, customHeaders, baseUrl.toURI().toString());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void resendActivationCodeToEmail(String userSignUpData){
        try {
            resendLoading.show();

            URL baseUrl = new URL(BaseConfig.getBaseUrl() + "/users/resendActivationCodeToEmail");

            userDataSharedPreferences = new SharedPreferencesUtils(AccountActivationActivity.this, "UserData");

            initVolleyCallbackResendActivationCode();

            volleyServicesResendActivationCode= new VolleyServices(volleyCallbackResendActivationCode, AccountActivationActivity.this);

            HashMap<String, String> customHeaders = new HashMap<>();

            if (userDataSharedPreferences.checkIfDataExists("AT")) {
                customHeaders.put("Authorization", userDataSharedPreferences.getPreferenceData("AT"));
            }

            volleyServicesResendActivationCode.sendStringRequestData(Request.Method.GET, customHeaders, baseUrl.toURI().toString(), userSignUpData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        registerReceiver(messageReceiver, messageIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }
}


