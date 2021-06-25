package com.github.dhaval2404.weatherapp.repository.model

/**
 * Network response class
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
sealed class NetworkResponse<out T : Any> {

    data class Success<out T : Any>(val data: T) : NetworkResponse<T>()

    data class Error(val error: String, val status: Int = 0) : NetworkResponse<Nothing>()
}
