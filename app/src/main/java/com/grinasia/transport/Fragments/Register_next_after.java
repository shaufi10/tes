package com.grinasia.transport.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Custom.UploadProgressRequestBody;
import com.grinasia.transport.Interface.UserService;
import com.grinasia.transport.R;
import com.grinasia.transport.Security.AES;
import com.grinasia.transport.Security.SecretKey;
import com.grinasia.transport.SplashScreenActivity;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.Strings;


import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by coder on 24-Jan-17.
 */
public class Register_next_after extends Fragment {
    private Spinner Spn_kind_company;
    private EditText txtName_company;
    private EditText txtAddress_company;
    private EditText txtPhone_company;
    private EditText txtNumber_akte;
    private EditText txtNumber_SIUP;
    private EditText txtnumber_TDP;
    private EditText txtnumber_NPWP;

    private ProgressBar progressBar;
    private Button btn_Number_akte;
    private Button btn_Number_SIUP;
    private Button btn_Number_TDP;
    private Button btn_Number_NPWP;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private Retrofit retrofit;
    private UserService userService;

    private boolean uploadingState = false;

    private static int RESULT_LOAD_IMG = 1;
    private String picturePath, resultPicturePath;

    public Register_next_after() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register_next_after, container, false);

        Spn_kind_company = (Spinner) view.findViewById(R.id.spn_kind_company);
        txtName_company = (EditText) view.findViewById(R.id.edName_Company);
        txtAddress_company = (EditText) view.findViewById(R.id.edAddress_Company);
        txtPhone_company = (EditText) view.findViewById(R.id.edNumber_telephone_Company);
        txtNumber_akte = (EditText) view.findViewById(R.id.edNumber_Akte);
        txtNumber_SIUP = (EditText) view.findViewById(R.id.edNumber_SIUP);
        txtnumber_TDP = (EditText) view.findViewById(R.id.edNumber_TDP);
        txtnumber_NPWP = (EditText) view.findViewById(R.id.edNumber_NPWP);

        btn_Number_akte = (Button) view.findViewById(R.id.btn_upload_akte);
        btn_Number_SIUP = (Button) view.findViewById(R.id.btn_upload_SIUP);
        btn_Number_TDP = (Button) view.findViewById(R.id.btn_upload_TDP);
        btn_Number_NPWP = (Button) view.findViewById(R.id.btn_upload_NPWP);

        List<String> kind_company = new ArrayList<>();
        kind_company.add("Perorangan");
        kind_company.add("Perusahaan");

        ArrayAdapter<String> kindcompanyaAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, kind_company);
        kindcompanyaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spn_kind_company.setAdapter(kindcompanyaAdapter);

        sharedPreferencesUtils = new SharedPreferencesUtils(getActivity(), "UserFile");

        retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BaseConfig.getBaseUrl())
                .build();

        userService = retrofit.create(UserService.class);

        btn_Number_akte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btn_Number_SIUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btn_Number_TDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btn_Number_NPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               uploadFile();

            }
        });


        return view;
    }



    private void uploadFile(){
        File imgFile = new File(picturePath);

        uploadingState = true;

        UploadProgressRequestBody reqFile = new UploadProgressRequestBody(imgFile, getActivity());
        MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("userFile", imgFile.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "Upload User File");

        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        Call<String> req = null;
        try {
            req = userService.uploadImage(sharedPreferencesUtils.getPreferenceData("AT"), bodyImage, name);
            req.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 403) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Upload User File")
                                .setMessage("Your Session has expired")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sharedPreferencesUtils.removeData("AT");
                                        sharedPreferencesUtils.removeData("UserFile");

                                        Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();

                    } else {
                        try {
                            String iv = Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);
                            byte[] decryptedResponse = AES.decrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), Base64.decode(response.body(), Base64.DEFAULT));

                            JSONObject responseData = new JSONObject(new String(decryptedResponse));

                            resultPicturePath = BaseConfig.getCDNBaseUrl() + "user_file" + responseData.getJSONObject("data").get("filename");

                            progressBar.setVisibility(View.GONE);

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Upload User File")
                                    .setMessage("Your Photo Has Been Uploaded")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            uploadingState = false;
                                        }
                                    })
                                    .show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();

                    uploadingState = false;

                    progressBar.setVisibility(View.GONE);

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Upload User File")
                            .setMessage("Failed to Upload")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}