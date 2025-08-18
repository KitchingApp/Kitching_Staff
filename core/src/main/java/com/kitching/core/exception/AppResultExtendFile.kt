package com.kitching.core.exception

import com.kitching.domain.util.AppResult

fun AppResult.Failure.getDisplayMessage(): String {
    return when (this.exception) {
        is KitchingRuntimeException -> ExceptionHandler.getDisplayMessage(exception as KitchingRuntimeException)

        else -> exception.message ?: UNKNOWN_EXCEPTION_MESSAGE
    }
}