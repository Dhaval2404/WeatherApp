package com.github.dhaval2404.weatherapp.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastWeatherEntity(
    @Json(name = "cod")
    val cod: String,
    @Json(name = "message")
    val message: Int,
    @Json(name = "cnt")
    val cnt: Int,
    @Json(name = "list")
    val list: List<ForecastWeather>
)

@JsonClass(generateAdapter = true)
data class ForecastWeather(
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "main")
    val main: Main,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "dt_txt")
    val dtTxt: String
)

@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "3h")
    val h: Double
)
