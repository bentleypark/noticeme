package com.project.noticeme.common.base

import retrofit2.Response
import timber.log.Timber

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Result<T> = apiOutput(call, errorMessage)
        val data: T?

        data = when (result) {
            is Result.Success -> {
                result.data
            }
            is Result.Error -> {
                Timber.e("Repository, $errorMessage & Exception - ${result.exception}")
//                Sentry.captureException(Exception(" $errorMessage & Exception - ${result.exception}"))
                null
            }
        }

        Timber.i("Data - $data")
        return data
    }

    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Result<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else
            Result.Error("Something went wrong due to  $error")
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
}
