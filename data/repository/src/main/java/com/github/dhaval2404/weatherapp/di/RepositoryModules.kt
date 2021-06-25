package com.github.dhaval2404.weatherapp.di

import com.github.dhaval2404.weatherapp.repository.CityRepository
import com.github.dhaval2404.weatherapp.repository.CityRepositoryImpl
import com.github.dhaval2404.weatherapp.repository.WeatherRepository
import com.github.dhaval2404.weatherapp.repository.WeatherRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Repository Modules
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
val repositoryModules = module {

    factory<CityRepository> {
        CityRepositoryImpl(get())
    }

    factory<WeatherRepository> {
        WeatherRepositoryImpl(get(), get(), get(named("open_weather_key")))
    }
}
