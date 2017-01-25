package com.grinasia.transport.Config;

/**
 * Created by coder on 24-Jan-17.
 */

public class BaseConfig {
    static {
        System.loadLibrary("GrinasiaConfig");
    }

    public native static String getBaseUrl();

//    public native static String getPaymentUrl();

//    public native static String getMerchantCode();

//    public native static String getMerchantPass();

//    public native static String getMerchantReturnUrl();

}
