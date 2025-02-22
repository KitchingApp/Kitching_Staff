package com.kitching.core.common

import androidx.compose.runtime.Composable
import com.kitching.domain.util.AppResult

@Composable
fun <T> AppResultHandler(
    state: AppResult<T>,
    onFailure: @Composable (Throwable) -> Unit = {},
    onSuccess: @Composable (T) -> Unit
) {
    when (state) {
        is AppResult.Initial -> {}

        is AppResult.Loading -> {
            ProgressIndicatorScreen()
        }

        is AppResult.Failure -> { onFailure(state.exception) }

        is AppResult.Success -> { onSuccess(state.data) }
    }
}