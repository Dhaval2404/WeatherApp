package com.github.dhaval2404.weatherapp.ui.weather_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.dhaval2404.weatherapp.remote.model.CurrentWeatherEntity
import com.github.dhaval2404.weatherapp.remote.model.ForecastWeatherEntity
import com.github.dhaval2404.weatherapp.repository.WeatherRepository
import com.github.dhaval2404.weatherapp.repository.model.NetworkResponse
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseViewModel
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherForecast
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherInfo
import kotlinx.coroutines.launch

/**
 * Handle weather operations
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class WeatherViewModel(private val weatherRepository: WeatherRepository) : BaseViewModel() {

    // Today's weather data
    private val mCurrentWeatherData = MutableLiveData<List<WeatherInfo>>()
    val currentWeatherData: LiveData<List<WeatherInfo>> = mCurrentWeatherData

    // 5 day's weather data
    private val mForecastWeatherData = MutableLiveData<List<WeatherForecast>>()
    val forecastWeatherData: LiveData<List<WeatherForecast>> = mForecastWeatherData

    // Today's weather temperature data
    private val mTemperatureData = MutableLiveData<String>()
    val temperatureData: LiveData<String> = mTemperatureData

    // Today's weather image data
    private val mWeatherImageURL = MutableLiveData<String>()
    val weatherImageURL: LiveData<String> = mWeatherImageURL

    /**
     * Fetch today's weather info for a given city
     *
     * @param cityEntity fetch weather info of a given city
     */
    fun fetchCurrentWeather(cityEntity: CityEntity) {
        // Start progress loader
        mLoadingState.postValue(true)

        viewModelScope.launch {
            // fetch data
            val response =
                weatherRepository.fetchCurrentWeather(cityEntity.latitude, cityEntity.longitude)

            // Stop progress loader
            mLoadingState.postValue(false)

            if (response is NetworkResponse.Success) {
                // Handle weather info
                val weatherInfoList = parseCurrentWeather(response.data)
                mCurrentWeatherData.postValue(weatherInfoList)
            } else if (response is NetworkResponse.Error) {
                // Handle error
                mErrorState.postValue(response.error)
            }
        }
    }

    /**
     * Fetch next 5 days weather info for a given city
     *
     * @param cityEntity fetch weather info of a given city
     */
    fun fetchWeatherForecast(cityEntity: CityEntity) {
        // Start progress loader
        mLoadingState.postValue(true)

        viewModelScope.launch {

            // fetch data
            val response =
                weatherRepository.fetchWeatherForecast(cityEntity.latitude, cityEntity.longitude)

            // Stop progress loader
            mLoadingState.postValue(false)

            if (response is NetworkResponse.Success) {
                // Handle weather info
                val weatherInfoList = parseForecastWeather(response.data)
                mForecastWeatherData.postValue(weatherInfoList)
            } else if (response is NetworkResponse.Error) {
                // Handle error
                mErrorState.postValue(response.error)
            }
        }
    }

    /**
     * Transform next 5 days weather info
     */
    private fun parseForecastWeather(data: ForecastWeatherEntity): List<WeatherForecast> {
        val isMetric = weatherRepository.isMetricUnitSystem()
        return WeatherParser(isMetric).getForecastWeatherInfo(data)
    }

    /**
     * Transform today's weather info
     */
    private fun parseCurrentWeather(data: CurrentWeatherEntity): List<WeatherInfo> {
        data.weather.firstOrNull()?.icon?.let {
            mWeatherImageURL.postValue(WeatherParser.getWeatherIcon(it))
        }

        val isMetric = weatherRepository.isMetricUnitSystem()
        val tempUnit = WeatherParser.getTempUnit(isMetric)
        mTemperatureData.postValue("${data.main.temp}° $tempUnit")

        return WeatherParser(isMetric).getCurrentWeatherInfo(data)
    }
}
