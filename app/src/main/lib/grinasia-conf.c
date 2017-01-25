//
// Created by Abdurrahman on 05-Dec-16.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Config_BaseConfig_getBaseUrl(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env, "https://api.grinasia.coms/api/");
}

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Config_BaseConfig_getPaymentUrl(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "https://www.padipay.coms/padipay-payment/webapp/home/request.html");
}

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Config_BaseConfig_getMerchantCode(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "nestyard");
}

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Config_BaseConfig_getMerchantPass(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "nestyard@Dev");
}

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Config_BaseConfig_getMerchantReturnUrl(JNIEnv *env, jclass type) {

    // TODO


    return (*env)->NewStringUTF(env, "https://api.grinasia.coms/api/paymentCallback");
}