package com.github.dhaval2404.weatherapp.repository

import android.content.Context
import androidx.preference.PreferenceManager
import com.github.dhaval2404.weatherapp.remote.model.CurrentWeatherEntity
import com.github.dhaval2404.weatherapp.remote.model.ForecastWeatherEntity
import com.github.dhaval2404.weatherapp.remote.service.WeatherService
import com.github.dhaval2404.weatherapp.repository.model.NetworkResponse

/**
 * Weather Repository
 *
 * Fetch Weather Data
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
interface WeatherRepository {

    /**
     * Get Today's Weather forecast
     */
    suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): NetworkResponse<CurrentWeatherEntity>

    /**
     * Get Next 5 days / 3 hours forecast
     */
    suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): NetworkResponse<ForecastWeatherEntity>

    /**
     * Check if metric/imperial system is select
     *
     * @return true is metric system is selected else return false
     */
    fun isMetricUnitSystem(): Boolean
}

class WeatherRepositoryImpl(
    private val context: Context,
    private val weatherService: WeatherService,
    private val openWeatherKey: String
) : BaseRepository(context), WeatherRepository {

    override suspend fun fetchCurrentWeather(
        latitude: Double,
        longitude: Double
    ): NetworkResponse<CurrentWeatherEntity> {
        return safeApiCall(call = {
            weatherService.fetchCurrentWeather(
                latitude,
                longitude,
                openWeatherKey,
                getUnitSystem()
            )
        })
    }

    override suspend fun fetchWeatherForecast(
        latitude: Double,
        longitude: Double
    ): NetworkResponse<ForecastWeatherEntity> {
        return safeApiCall(call = {
            weatherService.fetchWeatherForecast(
                latitude,
                longitude,
                openWeatherKey,
                getUnitSystem()
            )
        })
    }

    override fun isMetricUnitSystem(): Boolean {
        return getUnitSystem() == "metric"
    }

    private fun getUnitSystem(): String {
        val preference = PreferenceManager.getDefaultSharedPreferences(context)
        return preference.getString("key_unit_system", "metric")!!
    }
}
