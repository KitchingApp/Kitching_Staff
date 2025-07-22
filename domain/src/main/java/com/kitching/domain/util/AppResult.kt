package com.kitching.domain.util

import com.kitching.domain.exception.ExceptionHandler
import com.kitching.domain.exception.KitchingRuntimeException
import com.kitching.domain.exception.UNKNOWN_EXCEPTION_MESSAGE

sealed class AppResult<out T> {
    data object Loading : AppResult<Nothing>()
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Failure(val exception: Throwable) : AppResult<Nothing>()
}

fun AppResult.Failure.getDisplayMessage(): String {
    return when (this.exception) {
        is KitchingRuntimeException -> ExceptionHandler.getDisplayMessage(exception)

        else -> exception.message ?: UNKNOWN_EXCEPTION_MESSAGE
    }
}