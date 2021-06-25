package com.github.dhaval2404.weatherapp.di

import com.github.dhaval2404.weatherapp.BuildConfig
import com.github.dhaval2404.weatherapp.constant.AppSecretKeys
import com.github.dhaval2404.weatherapp.ui.add_city_screen.AddCityViewModel
import com.github.dhaval2404.weatherapp.ui.home_screen.HomeViewModel
import com.github.dhaval2404.weatherapp.ui.setting_screen.SettingViewModel
import com.github.dhaval2404.weatherapp.ui.weather_screen.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * App Modules
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
val appModules = module {
    // Open weather api key
    single(named("open_weather_key")) { AppSecretKeys.getOpenWeatherKey() }

    viewModel { HomeViewModel(get()) }
    viewModel { AddCityViewModel(get()) }
    viewModel { WeatherViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}

/**
 * Return all Koin Modules
 */
fun getAllModules(): List<Module> {
    val remoteModules = remoteModules(BuildConfig.API_BASE_URL, BuildConfig.DEBUG)
    return listOf(appModules, roomModules, remoteModules, repositoryModules)
}
