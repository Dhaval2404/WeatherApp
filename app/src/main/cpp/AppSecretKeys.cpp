/**
 * App Credentials
 *
 * @author Dhaval Patel
 * @version 4.0
 * @since 16 May 2021
 */
#include <jni.h>
#include <stdio.h>
#include <string>

/**
 * Open weather API Key
 */
#define OPEN_WEATHER_KEY  "OPEN_WEATHER_KEY"

extern "C"
JNIEXPORT jstring JNICALL
Java_com_github_dhaval2404_weatherapp_constant_AppSecretKeys_getOpenWeatherKey(JNIEnv *env,
                                                                   jclass type) {
    return (*env).NewStringUTF(OPEN_WEATHER_KEY);
}