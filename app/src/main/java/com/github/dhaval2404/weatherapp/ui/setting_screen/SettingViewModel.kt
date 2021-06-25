package com.github.dhaval2404.weatherapp.ui.setting_screen

import androidx.lifecycle.viewModelScope
import com.github.dhaval2404.weatherapp.repository.CityRepository
import com.github.dhaval2404.weatherapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Setting ViewModel
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class SettingViewModel(private val cityRepository: CityRepository) : BaseViewModel() {

    /**
     * Clear city bookmarks
     */
    fun clearCityBookmarks() {
        viewModelScope.launch {
            // Clear all cities
            cityRepository.clear()
        }
    }
}
