package com.github.dhaval2404.weatherapp.ui.weather_screen

import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.constant.AppConstant
import com.github.dhaval2404.weatherapp.remote.model.CurrentWeatherEntity
import com.github.dhaval2404.weatherapp.remote.model.ForecastWeatherEntity
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherForecast
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherInfo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Parse Open Weather API response and transform it for app to use
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class WeatherParser(private val isMetric: Boolean) {

    /**
     * Parse 5 day forecast weather info
     */
    fun getForecastWeatherInfo(data: ForecastWeatherEntity): List<WeatherForecast> {
        return data.list.map {
            WeatherForecast(
                temperature = "${it.main.temp}° ${getTempUnit(isMetric)}",
                weatherImage = getWeatherIcon(it.weather.firstOrNull()?.icon ?: ""),
                day = parseSystemDay(it.dt),
                time = parseSystemTime(it.dt)
            )
        }
    }

    /**
     * Parse today's forecast weather info
     */
    fun getCurrentWeatherInfo(data: CurrentWeatherEntity): List<WeatherInfo> {
        val weatherInfoList = ArrayList<WeatherInfo>()

        // Min Temp
        weatherInfoList.add(getMinTemperature(data.main.tempMin))

        // Max Temp
        weatherInfoList.add(getMaxTemperature(data.main.tempMax))

        // Chance of Rain
        weatherInfoList.add(getChanceOfRain(data.clouds.all))

        // Humidity
        weatherInfoList.add(getHumidity(data.main.humidity))

        // Wind Speed
        weatherInfoList.add(getWindSpeed(data.wind.deg, data.wind.speed))

        // Weather Pressure
        weatherInfoList.add(getPressure(data.main.pressure))

        // Visibility
        weatherInfoList.add(getVisibility(data.visibility))

        // Sunrise time
        weatherInfoList.add(getSunrise(data.sys.sunrise))

        // Sunset time
        weatherInfoList.add(getSunset(data.sys.sunrise))

        return weatherInfoList
    }

    private fun getMinTemperature(temp: Double) = WeatherInfo(
        icon = R.drawable.outline_thermostat_24,
        description = R.string.weather_min_temperature,
        value = "$temp° ${getTempUnit(isMetric)}"
    )

    private fun getMaxTemperature(temp: Double) = WeatherInfo(
        icon = R.drawable.outline_thermostat_24,
        description = R.string.weather_max_temperature,
        value = "$temp° ${getTempUnit(isMetric)}"
    )

    private fun getChanceOfRain(chanceOfRain: Int) = WeatherInfo(
        icon = R.drawable.outline_umbrella_24,
        description = R.string.weather_chance_of_rain,
        value = "$chanceOfRain%"
    )

    private fun getHumidity(humidity: Int) = WeatherInfo(
        icon = R.drawable.outline_water_drop_24,
        description = R.string.weather_humidity,
        value = "$humidity%"
    )

    private fun getPressure(pressure: Int) = WeatherInfo(
        icon = R.drawable.outline_speed_24,
        description = R.string.weather_pressure,
        value = "$pressure hPa"
    )

    private fun getVisibility(distance: Int) = WeatherInfo(
        icon = R.drawable.outline_visibility_24,
        description = R.string.weather_visibility,
        value = getDistance(distance, isMetric)
    )

    private fun getWindSpeed(degree: Int, speed: Double): WeatherInfo {
        val windDirection = getWindDirection(degree)
        val windSpeed = getWindSpeed(speed, isMetric)
        return WeatherInfo(
            icon = R.drawable.outline_air_24,
            description = R.string.weather_wind,
            value = "$windDirection $windSpeed"
        )
    }

    private fun getSunrise(timeInSecond: Int): WeatherInfo {
        return WeatherInfo(
            icon = R.drawable.ic_sunrise,
            description = R.string.weather_sunrise,
            value = parseSystemTime(timeInSecond)
        )
    }

    private fun getSunset(timeInSecond: Int): WeatherInfo {
        return WeatherInfo(
            icon = R.drawable.ic_sunset,
            description = R.string.weather_sunset,
            value = parseSystemTime(timeInSecond)
        )
    }

    companion object {

        private val COMPASS_SECTOR = mapOf(
            1 to "N",
            2 to "NNE",
            3 to "NE",
            4 to "ENE",
            5 to "E",
            6 to "ESE",
            7 to "SE",
            8 to "SSE",
            9 to "S",
            10 to "SSW",
            11 to "SW",
            12 to "WSW",
            13 to "W",
            14 to "WNW",
            15 to "NW",
            16 to "NNW",
            17 to "N",
        )

        fun getWindDirection(degree: Int): String {
            // 360/22.5 = 16
            val compassDirection: Int = (degree / 22.5f).toInt() + 1
            return COMPASS_SECTOR[compassDirection]!!
        }

        /**
         * @param speed meter/sec for metric and miles/hour for imperial unit system
         */
        fun getWindSpeed(speed: Double, isMetric: Boolean): String {
            return if (isMetric) {
                String.format("%.1f km/h", speed * 3.6f)
            } else {
                String.format("%.1f mile/h", speed)
            }
        }

        /**
         * @param distance provided distance in meter
         */
        fun getDistance(distance: Int, isMetric: Boolean): String {
            return if (isMetric) {
                String.format("%.2f km", distance / 1000.0f)
            } else {
                String.format("%.2f mile", distance / 1609.0f)
            }
        }

        /**
         * @param timeInSecond timestamp in second
         * @return i.e 10:30 PM
         */
        fun parseSystemTime(timeInSecond: Int): String {
            val date = Calendar.getInstance().apply {
                timeInMillis = timeInSecond * 1000L
            }.time

            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return sdf.format(date)
        }

        /**
         * @param timeInSecond timestamp in second
         * @return i.e 10:30 PM
         */
        fun parseSystemDay(timeInSecond: Int): String {
            val date = Calendar.getInstance().apply {
                timeInMillis = timeInSecond * 1000L
            }.time

            val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
            return sdf.format(date)
        }

        /**
         * @param isMetric
         * @return i.e if metric => "C" else "F"
         */
        fun getTempUnit(isMetric: Boolean) = if (isMetric) "C" else "F"

        fun getWeatherIcon(icon: String): String {
            return String.format(AppConstant.WEATHER_ICON_URL, icon)
        }
    }
}
