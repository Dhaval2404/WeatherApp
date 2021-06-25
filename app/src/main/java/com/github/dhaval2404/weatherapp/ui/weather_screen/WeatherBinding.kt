package com.github.dhaval2404.weatherapp.ui.weather_screen

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object WeatherBinding {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageSrc(view: ImageView, resource: Int?) {
        view.setImageResource(resource ?: 0)
    }

    @JvmStatic
    @BindingAdapter("srcRemote")
    fun setImageURL(view: ImageView, url: String?) {
        view.load(url)
    }
}
