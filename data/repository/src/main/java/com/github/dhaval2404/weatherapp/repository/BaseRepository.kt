package com.github.dhaval2404.weatherapp.repository

import android.content.Context
import com.github.dhaval2404.weatherapp.remote.exception.NetworkException
import com.github.dhaval2404.weatherapp.repository.model.NetworkResponse
import com.squareup.moshi.JsonDataException
import retrofit2.Response
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Parent class for all repository
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
abstract class BaseRepository(private val context: Context) {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return newsApiOutput(call)
    }

    private suspend fun <T : Any> newsApiOutput(
        call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body()!!)
            } else {
                NetworkResponse.Error(
                    response.errorBody()?.string()
                        ?: getError(R.string.error_something_went_wrong),
                    response.code()
                )
            }
        } catch (ex: Exception) {
            parseException(ex)
        }
    }

    private fun parseException(ex: Exception): NetworkResponse.Error {
        ex.printStackTrace()
        return when (ex) {
            is JsonDataException -> getNetworkError(R.string.error_json_error)
            is NetworkException,
            is SocketTimeoutException,
            is SocketException -> getNetworkError(R.string.error_network_connection_error)
            is UnknownHostException -> getNetworkError(R.string.error_server_not_available)
            else -> getNetworkError(R.string.error_something_went_wrong)
        }
    }

    private fun getError(error: Int) = context.getString(error)

    private fun getNetworkError(error: Int) = NetworkResponse.Error(getError(error))
}
