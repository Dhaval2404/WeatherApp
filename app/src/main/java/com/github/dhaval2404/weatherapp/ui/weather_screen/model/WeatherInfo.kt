package com.github.dhaval2404.weatherapp.ui.weather_screen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WeatherInfo(
    @DrawableRes
    val icon: Int,
    val value: String,
    @StringRes
    val description: Int,
)
