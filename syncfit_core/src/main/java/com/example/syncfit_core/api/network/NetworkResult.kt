package com.example.syncfit_core.api.network

sealed interface NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(
        val message: String,
        val statusCode: Int? = null,
        val cause: Throwable? = null,
    ) : NetworkResult<Nothing>
}
