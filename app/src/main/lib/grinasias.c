//
// Created by Abdurrahman on 05-Dec-16.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_grinasia_transport_Security_SecretKey_getSecretKey(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "NDc3MjMxNGUzNDczMzE0MTVmNGUzMzczNTQ3OTM0NTI2NA==");
}
