#include <jni.h>
#include <string>
#include <string.h>
#include <android/log.h>

using namespace std;

string get_omise_public_key() {
    //Test public key
    return "pkey_test_5j1aaa5ou9x3fgwksmh";
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_android_omise_main_MyApplication_getPublicKey(JNIEnv *env, jobject object) {
    std::string token = get_omise_public_key();
    return env->NewStringUTF(token.c_str());
}
