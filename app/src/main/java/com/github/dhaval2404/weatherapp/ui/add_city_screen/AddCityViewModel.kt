package com.github.dhaval2404.weatherapp.ui.add_city_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.repository.CityRepository
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

/**
 * Add City ViewModel
 *
 * Save City to RoomDB
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class AddCityViewModel(private val cityRepository: CityRepository) : BaseViewModel() {

    private var mCityEntity: CityEntity? = null
    private var mNavigator: AddCityNavigator? = null

    // Validation Live Data
    private val mErrorData = MutableLiveData<Int>()
    val errorData: LiveData<Int> = mErrorData

    fun setNavigator(navigator: AddCityNavigator) {
        mNavigator = navigator
    }

    fun setCityInfo(address: String, latLng: LatLng): CityEntity {
        val entity = CityEntity(
            address = address,
            latitude = latLng.latitude,
            longitude = latLng.longitude
        )
        mCityEntity = entity
        return entity
    }

    /**
     * Handle Save Button Click
     */
    fun onSaveClick() {
        val entity = mCityEntity
        if (entity == null) {
            // City not selected
            mErrorData.postValue(R.string.error_select_city)
        } else {
            saveCity(entity)
        }
    }

    /**
     * Save city to RoomDB for later use
     */
    private fun saveCity(entity: CityEntity) {
        viewModelScope.launch {
            // Save City Info
            cityRepository.insert(entity)

            // Go back to previous screen
            mNavigator?.onBackPressed()
        }
    }
}
