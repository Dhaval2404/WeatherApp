package com.github.dhaval2404.weatherapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Parent ViewModel for all the app ViewModel
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Loading State
     */
    protected val mLoadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = mLoadingState

    /**
     * Error State
     */
    protected val mErrorState = MutableLiveData<String>()
    val errorState: LiveData<String> = mErrorState
}
