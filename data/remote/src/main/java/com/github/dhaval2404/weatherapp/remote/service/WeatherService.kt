package com.github.dhaval2404.weatherapp.remote.service

import com.github.dhaval2404.weatherapp.remote.model.CurrentWeatherEntity
import com.github.dhaval2404.weatherapp.remote.model.ForecastWeatherEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * list open weather service endpoints
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
interface WeatherService {

    /**
     * Get Today's weather forecast
     */
    @GET("weather")
    suspend fun fetchCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric",
    ): Response<CurrentWeatherEntity>

    /**
     * Get next 5 days weather forecast
     */
    @GET("forecast")
    suspend fun fetchWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric",
    ): Response<ForecastWeatherEntity>

}
