package com.github.dhaval2404.weatherapp.remote.interceptor

import android.content.Context
import com.github.dhaval2404.weatherapp.remote.exception.NetworkException
import com.github.dhaval2404.weatherapp.remote.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Exit early if network connection not available
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 14 April 2021
 */
class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!NetworkUtil.isConnected(context)) {
            throw NetworkException()
        }
        return chain.proceed(request)
    }
}
