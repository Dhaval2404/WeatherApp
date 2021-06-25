package com.github.dhaval2404.weatherapp.ui.weather_screen

import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.AdapterWeatherInfoBinding
import com.github.dhaval2404.weatherapp.ui.base.BaseAdapter
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherInfo

/**
 * Show today's weather info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class WeatherInfoAdapter :
    BaseAdapter<WeatherInfo, AdapterWeatherInfoBinding,
        WeatherInfoAdapter.WeatherViewHolder>() {

    override fun getLayout() = R.layout.adapter_weather_info

    override fun getViewHolder(binding: AdapterWeatherInfoBinding) = WeatherViewHolder(binding)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WeatherViewHolder(private val binding: AdapterWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherInfo) {
            binding.weather = item
            binding.executePendingBindings()
        }
    }
}
