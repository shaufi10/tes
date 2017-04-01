package com.grinasia.transport.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.grinasia.transport.Config.BaseConfig;
import com.grinasia.transport.Interface.UserService;
import com.grinasia.transport.R;
import com.grinasia.transport.Security.AES;
import com.grinasia.transport.Security.SecretKey;
import com.grinasia.transport.SplashScreenActivity;
import com.grinasia.transport.Utils.SharedPreferencesUtils;
import com.grinasia.transport.Utils.Strings;


import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_OK;


public class RegisterCompany extends Fragment {

    private Spinner Spn_kind_Company;
    private EditText txtName_Company;
    private EditText txtAddress_Company;
    private EditText txtPhone_Company;
    private EditText txtNumber_Akte;
    private EditText txtNumber_SIUP;
    private EditText txtnumber_TDP;
    private EditText txtnumber_NPWP;

    private ProgressBar progressBar;
    private Button btn_Number_Akte;
    private Button btn_Number_SIUP;
    private Button btn_Number_TDP;
    private Button btn_Number_NPWP;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private Retrofit retrofit;
    private UserService userService;

    private static final int RESULT_LOAD_IMG = 100;
    private String resultPicturePath;
    private boolean uploadingState = false;

    public RegisterCompany() {

    }

    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register_next_after, container, false);
        context = view.getContext();

        Spn_kind_Company = (Spinner) view.findViewById(R.id.spn_kind_company);
        txtName_Company = (EditText) view.findViewById(R.id.txtName_Company);
        txtAddress_Company = (EditText) view.findViewById(R.id.txtAddress_Company);
        txtPhone_Company = (EditText) view.findViewById(R.id.txtPhoneNumber_Company);
        txtNumber_Akte = (EditText) view.findViewById(R.id.txtNumber_Akte);
        txtNumber_SIUP = (EditText) view.findViewById(R.id.txtNumber_SIUP);
        txtnumber_TDP = (EditText) view.findViewById(R.id.txtNumber_TDP);
        txtnumber_NPWP = (EditText) view.findViewById(R.id.txtNumber_NPWP);

        btn_Number_Akte = (Button) view.findViewById(R.id.btn_upload_akte);
        btn_Number_SIUP = (Button) view.findViewById(R.id.btn_upload_SIUP);
        btn_Number_TDP = (Button) view.findViewById(R.id.btn_upload_TDP);
        btn_Number_NPWP = (Button) view.findViewById(R.id.btn_upload_NPWP);

        btn_Number_Akte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        btn_Number_SIUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        btn_Number_TDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        btn_Number_NPWP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            android.net.Uri selectedImage = data.getData();
            String[] filePathColomn = {
                    MediaStore.Images.Media.DATA
            };
            android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColomn, null, null, null);
            if (cursor == null)
                return;

                cursor.moveToFirst();

                int colomnIndex = cursor.getColumnIndex(filePathColomn[0]);
                String filePath = cursor.getString(colomnIndex);
                cursor.close();

                File file = new File(filePath);

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part bodyImage = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_file");

                retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .baseUrl(BaseConfig.getBaseUrl())
                        .build();

                userService = retrofit.create(UserService.class);

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

                                                Intent intent = new Intent(context, SplashScreenActivity.class);
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
}