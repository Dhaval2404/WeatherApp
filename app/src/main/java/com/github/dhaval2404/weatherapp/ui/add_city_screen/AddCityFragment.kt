package com.github.dhaval2404.weatherapp.ui.add_city_screen

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.constant.AppConstant
import com.github.dhaval2404.weatherapp.databinding.FragmentAddCityBinding
import com.github.dhaval2404.weatherapp.remote.util.NetworkUtil
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.Locale

/**
 * Add City
 *
 * User can click on map to select City or search using Google Place API
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class AddCityFragment :
    BaseFragment<FragmentAddCityBinding>(),
    AddCityNavigator,
    OnMapReadyCallback {

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }

    private val mViewModel: AddCityViewModel by viewModel()

    private var mGoogleMap: GoogleMap? = null

    override fun getLayout() = R.layout.fragment_add_city

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind Variables
        mViewDataBinding.viewModel = mViewModel

        // Set Navigator
        mViewModel.setNavigator(this)

        setObserver()

        setGoogleMap()

        setPlaceAPI()
    }

    private fun setObserver() {
        // Listen to validation error
        mViewModel.errorData.observe(
            viewLifecycleOwner,
            Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        )
    }

    /**
     * Setup Google Map
     */
    private fun setGoogleMap() {
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment

        // Load google map
        supportMapFragment.getMapAsync(this)
    }

    private fun setPlaceAPI() {
        // Initialize the SDK
        Places.initialize(requireContext(), getString(R.string.google_maps_key))

        mViewDataBinding.locationSearchEt.setOnClickListener {
            startPlaceAPIIntent()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map

        mGoogleMap?.setOnMapClickListener {
            setMarker(it)
        }

        // Enable the zoom controls for the map
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        // Open Ahmedabad by default
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(23.0225, 72.5714), 8.0f))
    }

    private fun startPlaceAPIIntent() {
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        val fields = listOf(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG)

        // Start the autocomplete intent.
        val intent = Autocomplete
            .IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val place = Autocomplete.getPlaceFromIntent(data)
                setMarker(place)
            }
        }
    }

    /**
     * Update marker when click on Google Map
     */
    private fun setMarker(latLng: LatLng) {
        val address = getAddress(latLng)
        address?.let {
            val entity = mViewModel.setCityInfo(address, latLng)
            setMarker(entity, false)
        }
    }

    /**
     * Update marker on Google Place API Result
     */
    private fun setMarker(place: Place) {
        val address = place.address
        val latLng = place.latLng
        if (address != null && latLng != null) {
            val entity = mViewModel.setCityInfo(address, latLng)

            // Zoom to location on set marker
            setMarker(entity, true)
        }
    }

    private fun setMarker(entity: CityEntity, zoom: Boolean) {
        // Update address on search field
        mViewDataBinding.locationSearchEt.setText(entity.address)

        val latLng = LatLng(entity.latitude, entity.longitude)
        val marker = MarkerOptions().title(entity.address).position(latLng)

        // Clear previous marker
        mGoogleMap?.clear()

        // Add new marker
        mGoogleMap?.addMarker(marker)

        val cameraUpdate = if (zoom) {
            CameraUpdateFactory.newLatLngZoom(latLng, AppConstant.GOOGLE_MARKER_ZOOM)
        } else {
            CameraUpdateFactory.newLatLng(latLng)
        }
        // Set google map camera position to marker
        mGoogleMap?.moveCamera(cameraUpdate)
    }

    /**
     * Get address from location using android local geo-coder API
     */
    private fun getAddress(latLng: LatLng): String? {
        if (!NetworkUtil.isConnected(requireContext())) {
            Toast.makeText(
                requireContext(),
                R.string.error_network_connection_error,
                Toast.LENGTH_SHORT
            ).show()
            return null
        }

        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses.isNotEmpty()) {
                val address = addresses.first()
                return if (address.locality != null && address.adminArea != null) {
                    "${address.locality}, ${address.adminArea}, ${address.countryName}"
                } else {
                    address.getAddressLine(0)
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }

    /**
     * Go Back to previous screen
     */
    override fun onBackPressed() {
        findNavController().popBackStack()
    }
}
