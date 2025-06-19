package com.kitching.core.common

import androidx.compose.runtime.Composable
import com.kitching.domain.util.AppResult

/**
 * App Result Handler screen
 *
 * @param state 화면을 띄울때 필요한 상태값
 * @param onRetry 실패화면에서 다시하기 버튼을 누를시 실행할 함수
 * @param onSuccess 상태값 성공시 띄워줄 화면
 */

@Composable
fun <T> AppResultHandler(
    state: AppResult<T>,
    onRetry: () -> Unit,
    onSuccess: @Composable (T) -> Unit
) {
    when (state) {
        is AppResult.Initial -> {}

        is AppResult.Loading -> {
            ProgressIndicatorScreen()
        }

        is AppResult.Failure -> {
            DisConnectedScreen {
                onRetry()
            }
        }

        is AppResult.Success -> { onSuccess(state.data) }
    }
}

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

        is AppResult.Failure -> {
            onFailure(state.exception)
        }

        is AppResult.Success -> { onSuccess(state.data) }
    }
}

@Composable
fun <T, R> CombinedAppResultHandler(
    firstState: AppResult<T>,
    secondState: AppResult<R>,
    onLoading: @Composable () -> Unit = { ProgressIndicatorScreen() },
    onFailure: @Composable (Throwable) -> Unit = {},
    onSuccess: @Composable (T, R) -> Unit
) {
    when {
        firstState is AppResult.Initial && secondState is AppResult.Initial -> {}

        firstState is AppResult.Loading || secondState is AppResult.Loading -> {
            onLoading()
        }

        firstState is AppResult.Failure || secondState is AppResult.Failure -> {
            onFailure((firstState as? AppResult.Failure)?.exception ?: (secondState as AppResult.Failure).exception)
        }

        firstState is AppResult.Success && secondState is AppResult.Success -> {
            onSuccess(firstState.data, secondState.data)
        }
    }
}