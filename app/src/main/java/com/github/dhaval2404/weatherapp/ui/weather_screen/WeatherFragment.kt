package com.github.dhaval2404.weatherapp.ui.weather_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.databinding.FragmentWeatherBinding
import com.github.dhaval2404.weatherapp.room.entity.CityEntity
import com.github.dhaval2404.weatherapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Show weather info for Today as well as next 5 days
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    private val mViewModel: WeatherViewModel by viewModel()

    // Today's weather info adapter
    private val mWeatherInfoAdapter by lazy {
        WeatherInfoAdapter()
    }

    // Next 5 days weather info adapter
    private val mWeatherFutureForecastAdapter by lazy {
        WeatherFutureForecastAdapter()
    }

    override fun getLayout() = R.layout.fragment_weather

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind Variables
        mViewDataBinding.viewModel = mViewModel

        // Set today's weather info Adapter
        mViewDataBinding.weatherInfoRV.setHasFixedSize(true)
        mViewDataBinding.weatherInfoRV.adapter = mWeatherInfoAdapter

        // Set next 5 days weather info Adapter
        mViewDataBinding.futureWeatherInfoRV.setHasFixedSize(true)
        mViewDataBinding.futureWeatherInfoRV.adapter = mWeatherFutureForecastAdapter

        // Set UI Observers
        setObserver()

        // Handle city arguments
        val city = arguments?.getSerializable("city") as? CityEntity
        if (city != null) {
            activity?.title = city.address

            mViewModel.fetchCurrentWeather(city)

            mViewModel.fetchWeatherForecast(city)
        } else {
            // missing city info
            Toast.makeText(requireContext(), R.string.error_missing_city_info, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun setObserver() {
        // Observe error
        mViewModel.errorState.observe(
            viewLifecycleOwner,
            Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        )

        // Observe today's weather info
        mViewModel.currentWeatherData.observe(
            viewLifecycleOwner,
            Observer {
                mWeatherInfoAdapter.refresh(it)
            }
        )

        // Observe next 5 days weather info
        mViewModel.forecastWeatherData.observe(
            viewLifecycleOwner,
            Observer {
                mWeatherFutureForecastAdapter.refresh(it)
            }
        )
    }
}
