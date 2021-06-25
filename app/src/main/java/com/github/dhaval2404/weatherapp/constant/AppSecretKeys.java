package com.github.dhaval2404.weatherapp.constant;

/**
 * App Secret Keys
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
public class AppSecretKeys {

    static {
        System.loadLibrary("secrets");
    }

    /**
     * @return Open Weather API key
     */
    public native static String getOpenWeatherKey();

}
