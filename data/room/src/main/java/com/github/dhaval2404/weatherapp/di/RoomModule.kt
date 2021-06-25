package com.github.dhaval2404.weatherapp.di

import com.github.dhaval2404.weatherapp.room.AppDatabase
import org.koin.dsl.module

/**
 * Room Modules
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
val roomModules = module {

    // Room Database
    single { AppDatabase.getInstance(get()) }

    // Restaurant Dao
    single { (get() as AppDatabase).cityDao() }
}
