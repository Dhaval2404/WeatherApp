package com.github.dhaval2404.weatherapp.ui.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.dhaval2404.weatherapp.repository.CityRepository
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * View Model for Home Fragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class HomeViewModel(private val cityRepository: CityRepository) : BaseViewModel() {

    // Search live data
    val mSearchText = MutableLiveData<String>()
    val searchText: LiveData<String> = mSearchText

    private var mNavigator: HomeNavigator? = null

    fun setNavigator(navigator: HomeNavigator) {
        mNavigator = navigator
    }

    /**
     * Get all bookmarked cities
     */
    fun getAllCities() = cityRepository.getAllCities()

    /**
     * Handle add city click
     */
    fun addCityClick() {
        mNavigator?.showAddCityScreen()
    }

    /**
     * Delete bookmarked city
     */
    fun deleteCity(city: CityEntity) {
        viewModelScope.launch {
            cityRepository.delete(city)
        }
    }
}
