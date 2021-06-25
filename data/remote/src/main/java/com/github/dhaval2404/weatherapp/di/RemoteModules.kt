package com.github.dhaval2404.weatherapp.di


import com.github.dhaval2404.weatherapp.remote.interceptor.NetworkConnectionInterceptor
import com.github.dhaval2404.weatherapp.remote.service.WeatherService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Remote Modules
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
fun remoteModules(baseUrl: String, debug: Boolean) = module {

    factory<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            // Show Log for debug app
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {
        NetworkConnectionInterceptor(get())
    }

    factory() {
        val moshi = Moshi.Builder().build()
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }
    factory {
        val builder = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(get<NetworkConnectionInterceptor>())
        if (debug) {
            builder.addNetworkInterceptor(get<HttpLoggingInterceptor>())
        }
        builder
    }

    factory<Retrofit>() {
        get<Retrofit.Builder>().client(
            get<OkHttpClient.Builder>().build()
        ).build()
    }

    factory { get<Retrofit>().create(WeatherService::class.java) }
}
