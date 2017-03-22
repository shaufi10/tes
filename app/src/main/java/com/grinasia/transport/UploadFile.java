package com.grinasia.transport;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.grinasia.transport.Interface.UserService;
import com.grinasia.transport.Utils.SharedPreferencesUtils;

import retrofit2.Retrofit;

/**
 * Created by dark_coder on 3/20/2017.
 */

public class UploadFile extends AppCompatActivity {

    private SharedPreferencesUtils userSharedPreferences;
    private Retrofit retrofit;
    private UserService userService;

    private static int RESULT_LOAD_IMG = 1;

    private boolean uploadingState = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }
}
