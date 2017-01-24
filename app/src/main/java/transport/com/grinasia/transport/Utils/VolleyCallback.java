package transport.com.grinasia.transport.Utils;

import com.android.volley.VolleyError;

import java.util.Map;

/**
 * Created by coder on 24-Jan-17.
 */

public interface VolleyCallback {
    void onSuccessCallback(String response);
    void onErrorCallback(VolleyError error);
    void onResponseHeaderCallback(Map<String, String> header);
}
