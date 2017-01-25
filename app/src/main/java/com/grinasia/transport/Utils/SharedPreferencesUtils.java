package com.grinasia.transport.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.grinasia.transport.Security.AES;
import com.grinasia.transport.Security.SecretKey;

/**
 * Created by coder on 24-Jan-17.
 */

public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;

    private String iv;

    public SharedPreferencesUtils(Context context, String preferencesName) {
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    public void storeData(String key, String data){
        try {
            iv = com.grinasia.transport.Utils.Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);

            byte[] encrypedData = AES.encrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), data.getBytes());

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(key, Base64.encodeToString(encrypedData, Base64.DEFAULT));

            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ERROR STORING DATA", e.getMessage());
        }
    }

    public boolean checkIfDataExists(String key){
        return sharedPreferences.contains(key);
    }

    public String getPreferenceData(String key) throws Exception{
        iv = com.grinasia.transport.Utils.Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);
        String encryptedData = sharedPreferences.getString(key, null);
        byte[] decryptedData = AES.decrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), Base64.decode(encryptedData, Base64.DEFAULT));
        return new String(decryptedData);
    }

    public void removeData(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(key);

        editor.apply();
    }

    public void clearAllData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
