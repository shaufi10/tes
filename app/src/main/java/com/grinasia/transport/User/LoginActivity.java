package com.grinasia.transport.User;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.MainActivity;
import com.grinasia.transport.R;
import com.grinasia.transport.Security.AES;
import com.grinasia.transport.Security.SecretKey;
import com.grinasia.transport.Service.VolleyServices;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.Strings;
import com.grinasia.transport.Utils.VolleyCallback;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.grinasia.transport.Utils.ShowErrorLayoutUtils.showValidationErrorMessage;

/**
 * Created by coder on 06-Jan-17.
 */

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout txtLayoutLoginUsername;
    private TextInputLayout txtLayoutLoginPassword;

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    private TextView txtSignUp;

    private ProgressDialog signInLoading;

    private SharedPreferencesUtils userDataSharedPreferences;
    private VolleyCallback volleyCallback;
    private VolleyServices volleyServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtLayoutLoginUsername = (TextInputLayout)findViewById(R.id.txtLayoutUsername);
        txtLayoutLoginPassword = (TextInputLayout)findViewById(R.id.txtLayoutLoginPassword);

        txtUsername = (EditText)findViewById(R.id.txtLoginPassword);
        txtPassword = (EditText)findViewById(R.id.txtLoginPassword);

        btnLogin = (Button)findViewById(R.id.btnSignIn);
        txtSignUp = (TextView)findViewById(R.id.txtSignUp);

        signInLoading = new ProgressDialog(this);
        signInLoading.setTitle("Sign In");
        signInLoading.setMessage("Signing your account. . .");
        signInLoading.setCancelable(false);

        userDataSharedPreferences = new SharedPreferencesUtils(LoginActivity.this, "UserData");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUserInput()){
                    JSONObject data = new JSONObject();
                    try {
                        data.put("username", txtUsername.getText().toString());
                        data.put("password", txtPassword.getText().toString());
                        data.put("loginType", "default");

                        signInUser(data);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateUserInput() {
        txtLayoutLoginUsername.setErrorEnabled(false);
        txtLayoutLoginPassword.setErrorEnabled(false);

        if (TextUtils.isEmpty(txtUsername.getText().toString()) || TextUtils.isEmpty(txtPassword.getText().toString())){
            if (TextUtils.isEmpty(txtUsername.getText().toString())){
                showValidationErrorMessage(txtLayoutLoginUsername, txtUsername, "Username is required");
            }

            if (TextUtils.isEmpty(txtPassword.getText().toString())){
                showValidationErrorMessage(txtLayoutLoginPassword, txtPassword, "Password is required");
            }

            return false;
        } else if (txtPassword.getText().toString().length() < 6){
            showValidationErrorMessage(txtLayoutLoginPassword, txtPassword, "Password must be minimum 6 character");
            return false;
        }

        return true;
    }

    private void initVolleyCallback(){
        volleyCallback = new VolleyCallback() {
            @Override
            public void onSuccessCallback(String response) {
                signInLoading.dismiss();
                if (response != null && !response.isEmpty()) {
                    try {
                        String iv = Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);

                        byte[] decryptedResponse = AES.decrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), Base64.decode(response, Base64.DEFAULT));

                        JSONObject jsonResponse = new JSONObject(new String(decryptedResponse));

                        userDataSharedPreferences.storeData("userToken", jsonResponse.getString("authToken"));
                        userDataSharedPreferences.storeData("userProfile", jsonResponse.getString("data"));

                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("User Signin")
                                .setMessage("Oopss...Something went wrong, Please Try Again Later")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onErrorCallback(VolleyError error) {
                signInLoading.dismiss();

                if (error.networkResponse.statusCode == 404 || error.networkResponse.statusCode == 401){
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("User Signin")
                            .setMessage("Username/Password Is Invalid")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                if ("loginType" == "default") {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else if (error.networkResponse.statusCode == 401) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("User Signin")
                            .setMessage("Username/Password Is Invalid")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else if (error.networkResponse.statusCode == 406) {

                    String iv = null;
                    try {
                        iv = Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);

                        final byte [] decryptedResponse = AES.decrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), Base64.decode(error.networkResponse.data, Base64.DEFAULT));

                        new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("User Signin")
                            .setMessage("Oopss...Something went wrong, Please Try Again Later")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } catch (Exception e) {
                        e.printStackTrace();
                }
            } else {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("User Signin")
                        .setMessage("Oopss...Something went wrong, Please Try Again Later")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onResponseHeaderCallback(Map<String, String> headers) {

            }
        };
    }

    private void signInUser(JSONObject data){
        try {
            signInLoading.show();

            URL baseUrl = new URL(BaseConfig.getBaseUrl() + "login");

            initVolleyCallback();

            volleyServices = new VolleyServices(volleyCallback, LoginActivity.this);

            HashMap<String, String> customHeaders = new HashMap<>();

            if (userDataSharedPreferences.checkIfDataExists("AT")) {
                customHeaders.put("Authorization", userDataSharedPreferences.getPreferenceData("AT"));
            }

            volleyServices.sendStringRequestData(Request.Method.POST, customHeaders, baseUrl.toURI().toString(), data.toString());
        } catch (Exception e) {
            Log.e("registeringUser: ", e.getMessage());
        }
    }
}

