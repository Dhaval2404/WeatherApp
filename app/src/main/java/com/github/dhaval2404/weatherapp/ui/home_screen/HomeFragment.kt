package com.github.dhaval2404.weatherapp.ui.home_screen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.FragmentHomeBinding
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Home Fragment (List all bookmark cities)
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeNavigator {

    private val mViewModel: HomeViewModel by viewModel()

    private val mCityAdapter by lazy {
        CityAdapter()
    }

    override fun getLayout() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable menu for Home Fragment
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind Variables
        mViewDataBinding.viewModel = mViewModel

        // Set Navigator
        mViewModel.setNavigator(this)

        // Set LiveData Observer
        setObserver()

        // Set Adapter
        mViewDataBinding.cityRV.setHasFixedSize(true)
        mViewDataBinding.cityRV.setEmptyView(mViewDataBinding.cityEmptyView)
        mViewDataBinding.cityRV.adapter = mCityAdapter

        // Handle city click
        mCityAdapter.setCityClickListener { city ->
            showWeatherScreen(city)
        }

        // Handle city delete click
        mCityAdapter.setCityDeleteListener { city ->
            mViewModel.deleteCity(city)
        }
    }

    private fun setObserver() {
        // Observer search text changes
        mViewModel.searchText.observe(
            viewLifecycleOwner,
            Observer { text ->
                mCityAdapter.filter.filter(text)
            }
        )

        // Observer city changes
        mViewModel.getAllCities().observe(
            viewLifecycleOwner,
            Observer { cities ->
                mCityAdapter.refresh(cities)

                // Apply filter
                mViewModel.searchText.value?.let { text ->
                    mCityAdapter.filter.filter(text)
                }
            }
        )
    }

    /**
     * Inflate menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Handle menu item clicks
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Help menu click
            R.id.action_help -> {
                findNavController().navigate(R.id.showHelpInfo)
            }
            // Setting menu click
            R.id.action_setting -> {
                findNavController().navigate(R.id.showSetting)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Redirect to add city screen
     */
    override fun showAddCityScreen() {
        findNavController().navigate(R.id.showAddCity)
    }

    /**
     * Redirect to weather info screen
     */
    private fun showWeatherScreen(city: CityEntity) {
        val showCityWeather = HomeFragmentDirections.showCityWeather(city)
        findNavController().navigate(showCityWeather)
    }
}
