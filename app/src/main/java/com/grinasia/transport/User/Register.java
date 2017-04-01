package com.grinasia.transport.User;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.grinasia.transport.AccountActivationActivity;
import com.grinasia.transport.Adapter.SignupViewPagerAdapter;
import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Fragments.ConfirmTOSFragment;
import com.grinasia.transport.Fragments.Fragment_Register;
import com.grinasia.transport.Fragments.RegisterCompany;
import com.grinasia.transport.Fragments.RegisterProfile;
import com.grinasia.transport.R;
import com.grinasia.transport.Service.VolleyServices;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.VolleyCallback;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grinasia.transport.Utils.ShowErrorLayoutUtils.showValidationErrorMessage;


public class Register extends AppCompatActivity{

    private ViewPager signUpViewPager;
    private Button Next, Back, Mulai;
    private Spinner spinner;

    private String wilayah, email, username, password, firstname, lastname, address, dob, phone_number, nomor_sim, name_company,
            address_company, phone_company, number_akte, number_SIUP, number_TDP, number_NPWP;

    private VolleyCallback volleyCallback;
    private VolleyServices volleyServices;
    private SharedPreferencesUtils userDataSharedPreferences;

    private ProgressDialog signUpLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_register);

        final ProgressDialog signUpLoading = new ProgressDialog(this);
        signUpLoading.setTitle("Loading");
        signUpLoading.setMessage("Registering your account");
        signUpLoading.setCancelable(false);

        signUpViewPager = (ViewPager) findViewById(R.id.signUpView);
        Next = (Button) findViewById(R.id.btn_next);
        Mulai = (Button) findViewById(R.id.btnstart);
        setUpViewPagerRegister(signUpViewPager);

        signUpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    Next.setVisibility(View.GONE);
                    Mulai.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = signUpViewPager.getCurrentItem();
                validateUserInput(position);
                }
            }
        );

        Mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox chckTOS = (CheckBox)findViewById(R.id.chckAgreeTOS);
                if (chckTOS.isChecked()){
                    JSONObject dataUser = new JSONObject();
                    JSONObject dataUserProfile = new JSONObject();

                    try {
                        dataUser.put("Wilayah", wilayah);
                        dataUser.put("Email", email);
                        dataUser.put("username", username);
                        dataUser.put("password", password);
                        dataUser.put("status", 0);

                        dataUserProfile.put("Nama Depan", firstname);
                        dataUserProfile.put("Nama Belakang", lastname);
                        dataUserProfile.put("Alamat", address);
                        dataUserProfile.put("date_of_birth", dob);
                        dataUserProfile.put("No. HP", phone_number);
                        dataUserProfile.put("No. SIM", nomor_sim);

                        dataUserProfile.put("Nama Perusahaan", name_company);
                        dataUserProfile.put("Alamat Perusahaan", address_company);
                        dataUserProfile.put("No.HP Perusahaan", phone_company);
                        dataUserProfile.put("No. Akte", number_akte);
                        dataUserProfile.put("No. SIUP", number_SIUP);
                        dataUserProfile.put("No. TDP", number_TDP);
                        dataUserProfile.put("No. NPWP", number_NPWP);

                        dataUser.put("user_profile", dataUserProfile);

                        registeringUser(dataUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Register.this, "Please agree our Term of Service first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpViewPagerRegister(ViewPager viewPager){
        SignupViewPagerAdapter adapter = new SignupViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Register());
        adapter.addFragment(new RegisterProfile());
        adapter.addFragment(new RegisterCompany());
        adapter.addFragment(new ConfirmTOSFragment());
        viewPager.setAdapter(adapter);
    }

    private void validateUserInput(int viewPagerPosition){
        if (viewPagerPosition == 0) {

            TextInputLayout txtLayoutRegion = (TextInputLayout) findViewById(R.id.txtLayoutRegion_profile);
            TextInputLayout txtLayoutEmail = (TextInputLayout) findViewById(R.id.txtLayoutEmailAddress);
            TextInputLayout txtLayoutUsername = (TextInputLayout) findViewById(R.id.txtLayoutUserName);
            TextInputLayout txtLayoutPassword = (TextInputLayout) findViewById(R.id.txtLayoutPassword);
            TextInputLayout txtLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.txtLayoutConfirmPassword);

            txtLayoutRegion.setErrorEnabled(false);
            txtLayoutEmail.setErrorEnabled(false);
            txtLayoutUsername.setErrorEnabled(false);
            txtLayoutPassword.setErrorEnabled(false);
            txtLayoutConfirmPassword.setErrorEnabled(false);

            EditText txtRegion = (EditText) findViewById(R.id.txtRegion_profile);
            EditText txtEmail = (EditText) findViewById(R.id.txtEmailAddress_register);
            EditText txtUser = (EditText) findViewById(R.id.txtUsername);
            EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
            EditText txtConfirm_Password = (EditText) findViewById(R.id.txtConfirmPassword);

            if (TextUtils.isEmpty(txtRegion.getText().toString()) ||
                    TextUtils.isEmpty(txtEmail.getText().toString()) ||
                    TextUtils.isEmpty(txtUser.getText().toString()) ||
                    TextUtils.isEmpty(txtPassword.getText().toString())
                    || TextUtils.isEmpty(txtConfirm_Password.getText().toString())){
                if (TextUtils.isEmpty(txtRegion.getText().toString())) {
                    showValidationErrorMessage(txtLayoutRegion, txtRegion, "Region is required");
                }
                if (TextUtils.isEmpty(txtEmail.getText().toString())) {
                    showValidationErrorMessage(txtLayoutEmail, txtEmail, "Email Address is required");
                }
                if (TextUtils.isEmpty(txtUser.getText().toString())){
                    showValidationErrorMessage(txtLayoutUsername, txtUser, "Username is required");
                }
                if (TextUtils.isEmpty(txtPassword.getText().toString())){
                    showValidationErrorMessage(txtLayoutPassword, txtPassword, "Password is required");
                }

                if (TextUtils.isEmpty(txtConfirm_Password.getText().toString())){
                    showValidationErrorMessage(txtLayoutConfirmPassword, txtConfirm_Password, "Please confirm your password");
                }

                return;
            } else {

                if (!TextUtils.equals(txtPassword.getText().toString(), txtConfirm_Password.getText().toString())){
                    showValidationErrorMessage(txtLayoutConfirmPassword, txtConfirm_Password, "Password do not match");
                    return;
                } else {
                    if (txtPassword.getText().toString().length() < 6){
                        showValidationErrorMessage(txtLayoutPassword, txtPassword, "Password must be minimum 8 character");
                        return;
                    }

                    if (txtPassword.getText().toString().contains(txtUser.getText().toString())){
                        showValidationErrorMessage(txtLayoutPassword, txtPassword, "Password cannot contain your username");
                        return;
                    }
                }

            }
            signUpViewPager.setCurrentItem(viewPagerPosition + 1);
        } else if (viewPagerPosition == 1) {

            TextInputLayout txtLayoutFirstName = (TextInputLayout) findViewById(R.id.txtLayoutFirstName_profile);
            TextInputLayout txtLayoutLastName = (TextInputLayout) findViewById(R.id.txtLayoutLastName_profile);
            TextInputLayout txtLayoutAlamat = (TextInputLayout) findViewById(R.id.txtLayoutAddress_profile);
            TextInputLayout txtLayoutDOB = (TextInputLayout) findViewById(R.id.TextLayoutDOB);
            TextInputLayout txtLayoutNomor = (TextInputLayout) findViewById(R.id.txtLayoutPhoneNumber_profile);
            TextInputLayout txtLayoutSIM = (TextInputLayout) findViewById(R.id.txtLayoutSIM);

            txtLayoutFirstName.setErrorEnabled(false);
            txtLayoutLastName.setErrorEnabled(false);
            txtLayoutAlamat.setErrorEnabled(false);
            txtLayoutDOB.setEnabled(false);
            txtLayoutNomor.setErrorEnabled(false);
            txtLayoutSIM.setErrorEnabled(false);

            EditText txtFirstName = (EditText) findViewById(R.id.txtFirstName_register);
            EditText txtLastName = (EditText) findViewById(R.id.txtLastName_register);
            EditText txtAddress = (EditText) findViewById(R.id.txtAddress_register);
            EditText txtDOB = (EditText) findViewById(R.id.txtDOB_register);
            EditText txtPhoneNumber = (EditText) findViewById(R.id.txtNumberPhone_register);
            EditText txtSIM = (EditText) findViewById(R.id.txtSIM_register);

            if (TextUtils.isEmpty(txtFirstName.getText().toString()) ||
                    TextUtils.isEmpty(txtLastName.getText().toString()) ||
                    TextUtils.isEmpty(txtAddress.getText().toString())||
                    TextUtils.isEmpty(txtDOB.getText().toString())
                    || TextUtils.isEmpty(txtPhoneNumber.getText().toString())
                    || TextUtils.isEmpty(txtSIM.getText().toString())){

                if (TextUtils.isEmpty(txtFirstName.getText().toString())){
                    showValidationErrorMessage(txtLayoutFirstName, txtFirstName, "First Name is required");
                }

                if (TextUtils.isEmpty(txtLastName.getText().toString())){
                    showValidationErrorMessage(txtLayoutLastName, txtLastName, "Last Name is required");
                }

                if (TextUtils.isEmpty(txtAddress.getText().toString())){
                    showValidationErrorMessage(txtLayoutAlamat, txtAddress, "Address is required");
                }

                if (TextUtils.isEmpty(txtDOB.getText().toString())){
                    showValidationErrorMessage(txtLayoutDOB, txtDOB, "Date Of Birth is required");
                }

                if (TextUtils.isEmpty(txtPhoneNumber.getText().toString())){
                    showValidationErrorMessage(txtLayoutNomor, txtPhoneNumber, "Number Telephone is required");
                }

                if (TextUtils.isEmpty(txtSIM.getText().toString())){
                    showValidationErrorMessage(txtLayoutSIM, txtSIM, "SIM Number is required");
                }

                return;

            }

            signUpViewPager.setCurrentItem(viewPagerPosition + 1);
                } else if (viewPagerPosition == 2){
                final TextInputLayout txtLayoutName_Company = (TextInputLayout) findViewById(R.id.txtLayoutName_Company);
                TextInputLayout txtLayoutAddress_Company = (TextInputLayout) findViewById(R.id.txtLayoutAddress_Company);
                TextInputLayout txtLayoutNumber_Company = (TextInputLayout) findViewById(R.id.txtLayoutPhoneNumber_Company);
                TextInputLayout txtLayoutNumberAkte = (TextInputLayout) findViewById(R.id.txtLayoutAkte);
                TextInputLayout txtLayoutNumberSIUP = (TextInputLayout) findViewById(R.id.txtLayoutNumberSIUP);
                TextInputLayout txtLayoutNumberTDP = (TextInputLayout) findViewById(R.id.txtLayoutNumberTDP);
                TextInputLayout txtLayoutNumberNPWP = (TextInputLayout) findViewById(R.id.txtLayoutNumberNPWP);

                txtLayoutName_Company.setErrorEnabled(false);
                txtLayoutAddress_Company.setErrorEnabled(false);
                txtLayoutNumber_Company.setErrorEnabled(false);
                txtLayoutNumberAkte.setErrorEnabled(false);
                txtLayoutNumberSIUP.setErrorEnabled(false);
                txtLayoutNumberTDP.setErrorEnabled(false);
                txtLayoutNumberNPWP.setErrorEnabled(false);

                final EditText txtNama_Company = (EditText) findViewById(R.id.txtName_Company);
                final EditText txtAlamat_Company = (EditText) findViewById(R.id.txtAddress_Company);
                final EditText txtTelepon_Company= (EditText) findViewById(R.id.txtPhoneNumber_Company);
                final EditText txtNumber_Akte = (EditText) findViewById(R.id.txtNumber_Akte);
                final EditText txtNumber_SIUP = (EditText) findViewById(R.id.txtNumber_SIUP);
                final EditText txtNumber_TDP = (EditText) findViewById(R.id.txtNumber_TDP);
                final EditText txtNumber_NPWP = (EditText) findViewById(R.id.txtNumber_NPWP);

                spinner = (Spinner) findViewById(R.id.spn_kind_company);

                List<String> list = new ArrayList<>();
                list.add("Perorangan");
                list.add("Perusahaan");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(spinner.getSelectedItem().toString().trim().equals("Perorangan")){
                        txtLayoutName_Company.setVisibility(View.VISIBLE);
                        txtAlamat_Company.setVisibility(View.VISIBLE);
                        txtTelepon_Company.setVisibility(View.VISIBLE);
                        txtNumber_Akte.setVisibility(View.VISIBLE);
                        txtNumber_SIUP.setVisibility(View.VISIBLE);
                        txtNumber_TDP.setVisibility(View.VISIBLE);
                        txtNumber_NPWP.setVisibility(View.VISIBLE);
                    }else{
                        txtLayoutName_Company.setVisibility(View.VISIBLE);
                        txtAlamat_Company.setVisibility(View.VISIBLE);
                        txtTelepon_Company.setVisibility(View.VISIBLE);
                        txtNumber_Akte.setVisibility(View.VISIBLE);
                        txtNumber_SIUP.setVisibility(View.VISIBLE);
                        txtNumber_TDP.setVisibility(View.VISIBLE);
                        txtNumber_NPWP.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (TextUtils.isEmpty(txtNama_Company.getText().toString())
                    || TextUtils.isEmpty(txtAlamat_Company.getText().toString())
                    || TextUtils.isEmpty(txtTelepon_Company.getText().toString())
                    || TextUtils.isEmpty(txtNumber_Akte.getText().toString())
                    || TextUtils.isEmpty(txtNumber_SIUP.getText().toString())
                    || TextUtils.isEmpty(txtNumber_TDP.getText().toString())
                    || TextUtils.isEmpty(txtNumber_NPWP.getText().toString())){

                if (TextUtils.isEmpty(txtNama_Company.getText().toString())){
                    showValidationErrorMessage(txtLayoutName_Company, txtNama_Company, "Name Perusahaan is required");
                }
                if (TextUtils.isEmpty(txtAlamat_Company.getText().toString())){
                    showValidationErrorMessage(txtLayoutAddress_Company, txtAlamat_Company, "Alamat Perusahaan is required");
                }
                if (TextUtils.isEmpty(txtTelepon_Company.getText().toString())){
                    showValidationErrorMessage(txtLayoutNumber_Company, txtTelepon_Company, "Nomor Telepon is required");
                }
                if (TextUtils.isEmpty(txtNumber_Akte.getText().toString())){
                    showValidationErrorMessage(txtLayoutNumberAkte, txtNumber_Akte, "Nomor Akte is required");
                }
                if (TextUtils.isEmpty(txtNumber_SIUP.getText().toString())){
                    showValidationErrorMessage(txtLayoutNumberSIUP, txtNumber_SIUP, "Nomor SIUP is required");
                }
                if (TextUtils.isEmpty(txtNumber_TDP.getText().toString())){
                    showValidationErrorMessage(txtLayoutNumberTDP, txtNumber_TDP, "Nomor TDP is required");
                }
                if (TextUtils.isEmpty(txtNumber_NPWP.getText().toString())){
                    showValidationErrorMessage(txtLayoutNumberNPWP, txtNumber_NPWP, "Nomor NPWP is required");
                }
                return;
            }
            signUpViewPager.setCurrentItem(viewPagerPosition + 1);

        }
    }

    private void initVolleyCallback(){
        volleyCallback = new VolleyCallback() {
            @Override
            public void onSuccessCallback(String response) {
                signUpLoading.dismiss();

                Intent intent = new Intent(Register.this, AccountActivationActivity.class);
                startActivity(intent);
            }

            @Override
            public void onErrorCallback(VolleyError error) {
                signUpLoading.dismiss();

                new AlertDialog.Builder(Register.this)
                        .setTitle("User Registration")
                        .setMessage("Oopss...Something went wrong, Please Try Again Later")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }

            @Override
            public void onResponseHeaderCallback(Map<String, String> headers) {

            }
        };
    };

    private void registeringUser(JSONObject data){
        try {
            signUpLoading.show();

            URL baseUrl = new URL(BaseConfig.getBaseUrl() + "registerUser");

            userDataSharedPreferences = new SharedPreferencesUtils(Register.this, "UserData");

            initVolleyCallback();

            volleyServices = new VolleyServices(volleyCallback, Register.this);

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

