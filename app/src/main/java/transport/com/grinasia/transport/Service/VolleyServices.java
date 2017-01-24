package transport.com.grinasia.transport.Service;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.crypto.SecretKey;

import transport.com.grinasia.transport.Security.AES;
import transport.com.grinasia.transport.Utils.Strings;
import transport.com.grinasia.transport.Utils.VolleyCallback;

/**
 * Created by coder on 24-Jan-17.
 */

public class VolleyServices {

    private VolleyCallback mResultCallback = null;
    private RequestQueue queue;

    public VolleyServices(VolleyCallback resultCallback, Context context){
        mResultCallback = resultCallback;
        queue = Volley.newRequestQueue(context);
    }

    public void getStringRequestData(int requestType, final Map<String, String> customHeaders, String url){
        try {
            StringRequest stringRequest = new StringRequest(requestType, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (mResultCallback != null) {
                        mResultCallback.onSuccessCallback(response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null){
                        mResultCallback.onErrorCallback(error);
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (!customHeaders.isEmpty()){
                        return customHeaders;
                    }
                    return super.getHeaders();
                }
            };

            queue.add(stringRequest);

        }catch(Exception e){
            Log.e("ERROR GET DATA: ", e.toString());
            e.printStackTrace();
        }
    }

    public void sendStringRequestData(int requestType, final Map<String,String> customHeaders, String url, String data){
        try {
            String iv = Strings.getHexString(SecretKey.getOriginalSecretKey().getBytes()).substring(0, 16);

            byte[] encrypted = AES.encrypt(iv.getBytes(), SecretKey.getOriginalSecretKey().getBytes(), data.getBytes());

            final String encryptedText = Base64.encodeToString(encrypted, Base64.DEFAULT).replaceAll("(\\r|\\n)", "");

            StringRequest stringRequest = new StringRequest(requestType, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (mResultCallback != null) {
                        mResultCallback.onSuccessCallback(response);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null){
                        mResultCallback.onErrorCallback(error);
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (!customHeaders.isEmpty()){
                        return customHeaders;
                    }
                    return super.getHeaders();
                }

                @Override
                public String getBodyContentType() {
                    return "text/plain; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return encryptedText == null ? null : encryptedText.getBytes("utf-8");
                    } catch (UnsupportedEncodingException e){
                        Log.e("getBody: ", e.toString());
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    if (mResultCallback != null){
                        Map<String, String> responseHeaders = response.headers;
                        mResultCallback.onResponseHeaderCallback(responseHeaders);
                    }
                    return super.parseNetworkResponse(response);
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);

        }catch(Exception e){
            Log.e("ERROR GENERATE PUBKEY: ", e.toString());
            e.printStackTrace();
        }
    }

    public void cancelAllRequest() {
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
