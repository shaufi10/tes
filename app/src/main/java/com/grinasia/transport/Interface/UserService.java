package com.grinasia.transport.Interface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by dark_coder on 3/20/2017.
 */

public interface UserService {
    @Multipart
    @POST("users/uploadUserFileImage")
    Call<String> uploadImage(@Header("Authorization")
                             String authorizationToken,
                             @Part MultipartBody.Part image,
                             @Part ("name") RequestBody name
    );
}
