package com.grinasia.transport;

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
import com.grinasia.transport.Adapter.SignupViewPagerAdapter;
import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Fragments.ConfirmTOSFragment;
import com.grinasia.transport.Fragments.Fragment_Register;
import com.grinasia.transport.Fragments.Register_next;
import com.grinasia.transport.Fragments.Register_next_after;
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

/**
 * Created by coder on 06-Jan-17.
 */
public class Register  extends AppCompatActivity{

    private ViewPager signUpViewPager;
    private Button Next, Back, Mulai;
    private Spinner spinner;

    private String wilayah, email, username, password, firstname, lastname, address, phone_number, nomor_sim, name_company,
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
//        Back = (Button) findViewById(R.id.btn_back);
        Mulai = (Button) findViewById(R.id.btnstart);
        setUpViewPagerRegister(signUpViewPager);

        signUpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Next.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    Next.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    Next.setVisibility(View.VISIBLE);
                } else {
                    Mulai.setVisibility(View.GONE);
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

            /*Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (signUpViewPager.getCurrentItem() != 0) {
                        signUpViewPager.setCurrentItem(signUpViewPager.getCurrentItem() - 1, false);
                    } else {
                        finish();
                    }
                }
            }
            );*/

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
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.btn_back:
                this.finish();
                return true;
            case R.id.btn_next:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_register, new Fragment_Register());
                transaction.commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void setUpViewPagerRegister(ViewPager viewPager){
        SignupViewPagerAdapter adapter = new SignupViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Register());
        adapter.addFragment(new Register_next());
        adapter.addFragment(new Register_next_after());
        adapter.addFragment(new ConfirmTOSFragment());
        viewPager.setAdapter(adapter);
    }

    private void validateUserInput(int viewPagerPosition){
        if (viewPagerPosition == 0) {

            TextInputLayout txtLayoutRegion = (TextInputLayout) findViewById(R.id.txtInputKotamadya);
            TextInputLayout txtLayoutEmail = (TextInputLayout) findViewById(R.id.txtInputEmailAddress);
            TextInputLayout txtLayoutUsername = (TextInputLayout) findViewById(R.id.txtInputUserName);
            TextInputLayout txtLayoutPassword = (TextInputLayout) findViewById(R.id.txtInputPassword);
            TextInputLayout txtLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.txtInputConfirmPassword);

            txtLayoutRegion.setErrorEnabled(false);
            txtLayoutEmail.setErrorEnabled(false);
            txtLayoutUsername.setErrorEnabled(false);
            txtLayoutPassword.setErrorEnabled(false);
            txtLayoutConfirmPassword.setErrorEnabled(false);

            EditText txtRegion = (EditText) findViewById(R.id.edKotamadya);
            EditText txtEmail = (EditText) findViewById(R.id.edEmailAddress);
            EditText txtUser = (EditText) findViewById(R.id.edUser);
            EditText txtPassword = (EditText) findViewById(R.id.edPassword);
            EditText txtConfirmPassword = (EditText) findViewById(R.id.edConfirmPassword);

            if (TextUtils.isEmpty(txtRegion.getText().toString()) ||
                    TextUtils.isEmpty(txtEmail.getText().toString()) ||
                    TextUtils.isEmpty(txtUser.getText().toString()) ||
                    TextUtils.isEmpty(txtPassword.getText().toString())
                    || TextUtils.isEmpty(txtConfirmPassword.getText().toString())){
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

                if (TextUtils.isEmpty(txtConfirmPassword.getText().toString())){
                    showValidationErrorMessage(txtLayoutConfirmPassword, txtConfirmPassword, "Please confirm your password");
                }

                return;
            } else {

                if (!TextUtils.equals(txtPassword.getText().toString(), txtConfirmPassword.getText().toString())){
                    showValidationErrorMessage(txtLayoutConfirmPassword, txtConfirmPassword, "Password do not match");
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

            TextInputLayout txtLayoutFirstName = (TextInputLayout) findViewById(R.id.txtInputFirstName);
            TextInputLayout txtLayoutLastName = (TextInputLayout) findViewById(R.id.txtInputLastName);
            TextInputLayout txtLayoutAlamat = (TextInputLayout) findViewById(R.id.txtInputAddress);
            TextInputLayout txtLayoutNomor = (TextInputLayout) findViewById(R.id.txtInputPhone);
            TextInputLayout txtLayoutSIM = (TextInputLayout) findViewById(R.id.txtInputSIM);

            txtLayoutFirstName.setErrorEnabled(false);
            txtLayoutLastName.setErrorEnabled(false);
            txtLayoutAlamat.setErrorEnabled(false);
            txtLayoutNomor.setErrorEnabled(false);
            txtLayoutSIM.setErrorEnabled(false);

            EditText txtFirstname = (EditText) findViewById(R.id.edFirstName);
            EditText txtLastname = (EditText) findViewById(R.id.edLastName);
            EditText txtAlamat = (EditText) findViewById(R.id.edAddress);
            EditText txtNumberTelepon = (EditText) findViewById(R.id.edNumber_telephone);
            EditText txtSIM = (EditText) findViewById(R.id.edSIM_phone);

            if (TextUtils.isEmpty(txtFirstname.getText().toString()) ||
                    TextUtils.isEmpty(txtLastname.getText().toString()) ||
                    TextUtils.isEmpty(txtAlamat.getText().toString())
                    || TextUtils.isEmpty(txtNumberTelepon.getText().toString())
                    || TextUtils.isEmpty(txtSIM.getText().toString())){

                if (TextUtils.isEmpty(txtFirstname.getText().toString())){
                    showValidationErrorMessage(txtLayoutFirstName, txtFirstname, "First Name is required");
                }
                if (TextUtils.isEmpty(txtLastname.getText().toString())){
                    showValidationErrorMessage(txtLayoutLastName, txtLastname, "Last Name is required");
                }
                if (TextUtils.isEmpty(txtAlamat.getText().toString())){
                    showValidationErrorMessage(txtLayoutAlamat, txtAlamat, "Address is required");
                }
                if (TextUtils.isEmpty(txtNumberTelepon.getText().toString())){
                    showValidationErrorMessage(txtLayoutNomor, txtNumberTelepon, "Number Telephone is required");
                }
                if (TextUtils.isEmpty(txtSIM.getText().toString())){
                    showValidationErrorMessage(txtLayoutSIM, txtSIM, "SIM Number is required");
                }

                return;

            }

            signUpViewPager.setCurrentItem(viewPagerPosition + 1);



        } else if (viewPagerPosition == 2){

            final TextInputLayout txtLayoutName_Company = (TextInputLayout) findViewById(R.id.txtInputName_company);
            TextInputLayout txtLayoutAddress_Company = (TextInputLayout) findViewById(R.id.txtInputAddress_company);
            TextInputLayout txtLayoutNumber_Company = (TextInputLayout) findViewById(R.id.txtInputPhone_company);
            TextInputLayout txtLayoutNumberAkte = (TextInputLayout) findViewById(R.id.txtInputNumber_Akte);
            TextInputLayout txtLayoutNumberSIUP = (TextInputLayout) findViewById(R.id.txtInputNumberSIUP);
            TextInputLayout txtLayoutNumberTDP = (TextInputLayout) findViewById(R.id.txtInputNumberTDP);
            TextInputLayout txtLayoutNumberNPWP = (TextInputLayout) findViewById(R.id.txtInputNumberNPWP);

            txtLayoutName_Company.setErrorEnabled(false);
            txtLayoutAddress_Company.setErrorEnabled(false);
            txtLayoutNumber_Company.setErrorEnabled(false);
            txtLayoutNumberAkte.setErrorEnabled(false);
            txtLayoutNumberSIUP.setErrorEnabled(false);
            txtLayoutNumberTDP.setErrorEnabled(false);
            txtLayoutNumberNPWP.setErrorEnabled(false);

            final EditText txtNama_Company = (EditText) findViewById(R.id.edName_Company);
            final EditText txtAlamat_Company = (EditText) findViewById(R.id.edAddress_Company);
            final EditText txtTelepon_Company= (EditText) findViewById(R.id.edNumber_telephone);
            final EditText txtNumber_Akte = (EditText) findViewById(R.id.edNumber_Akte);
            final EditText txtNumber_SIUP = (EditText) findViewById(R.id.edNumber_SIUP);
            final EditText txtNumber_TDP = (EditText) findViewById(R.id.edNumber_TDP);
            final EditText txtNumber_NPWP = (EditText) findViewById(R.id.edNumber_NPWP);

            spinner = (Spinner) findViewById(R.id.spinner1);

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

