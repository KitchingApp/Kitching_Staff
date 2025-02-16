package com.kitching.domain.util

sealed class AppResult<out T> {
    data object Initial : AppResult<Nothing>()
    data object Loading : AppResult<Nothing>()
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Failure(val exception: Throwable) : AppResult<Nothing>()
}