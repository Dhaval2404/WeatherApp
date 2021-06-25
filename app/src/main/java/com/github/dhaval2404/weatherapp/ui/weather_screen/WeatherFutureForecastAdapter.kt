package com.github.dhaval2404.weatherapp.ui.weather_screen

import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.AdapterFutureWeatherForecastBinding
import com.github.dhaval2404.weatherapp.ui.base.BaseAdapter
import com.github.dhaval2404.weatherapp.ui.weather_screen.model.WeatherForecast

/**
 * Show next 5 days weather info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class WeatherFutureForecastAdapter :
    BaseAdapter<WeatherForecast, AdapterFutureWeatherForecastBinding,
        WeatherFutureForecastAdapter.WeatherViewHolder>() {

    override fun getLayout() = R.layout.adapter_future_weather_forecast

    override fun getViewHolder(binding: AdapterFutureWeatherForecastBinding) =
        WeatherViewHolder(binding)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val resources = holder.binding.root.context.resources
        val backgroundColors = resources.getIntArray(R.array.card_background_colors)
        val color = backgroundColors[position % backgroundColors.size]

        // Set background card color
        holder.binding.cardView.setCardBackgroundColor(color)

        // Bind weather info
        holder.bind(getItem(position))
    }

    inner class WeatherViewHolder(val binding: AdapterFutureWeatherForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * Bind weather info
         */
        fun bind(item: WeatherForecast) {
            binding.weather = item
            binding.executePendingBindings()
        }
    }
}
