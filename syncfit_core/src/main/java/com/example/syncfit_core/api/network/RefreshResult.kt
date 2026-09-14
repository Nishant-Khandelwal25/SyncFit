package com.example.syncfit_core.api.network

sealed interface RefreshResult {
    data object Success : RefreshResult
    data class Error(val message: String) : RefreshResult
}
