package com.kitching.core.common.appresultscreen

import androidx.compose.runtime.Composable
import com.kitching.domain.util.UiState

@Composable
fun <T> UiStateHandler(
    uiState: UiState<T>,
    onRetry: (() -> Unit) = {  },
    onSuccess: @Composable (T) -> Unit,
) {
    when {
        uiState.isLoading -> {
            ProgressIndicatorScreen()
        }

        uiState.isSuccess -> {
            uiState.data?.let { data ->
                onSuccess(data)
            }
        }

        uiState.isError -> {
            ErrorScreen(uiState.error ?: "") { onRetry() }
        }
    }
}