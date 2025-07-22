package com.kitching.core.common.appresultscreen

import androidx.compose.runtime.Composable
import com.kitching.domain.util.UiState

/**
 * UiState Handler - UiState를 화면에서 처리
 *
 * @param uiState 처리할 상태 값
 * @param onRetry 에러 상태에서 재시도 함수
 * @param onSuccess 성공일 때 보여줄 화면
 */
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

/**
 * Combined UiState Handler - 두 개의 UiState를 동시에 처리
 *
 * @param firstState 첫 번째 상태
 * @param secondState 두 번째 상태
 * @param onRetry 에러 상태에서 재시도 함수
 * @param onSuccess 두 상태 모두 성공일 때 보여줄 화면
 */
@Composable
fun <T, R> CombinedUiStateHandler(
    firstState: UiState<T>,
    secondState: UiState<R>,
    onRetry: Pair<() -> Unit, () -> Unit> = Pair({}, {}),
    onSuccess: @Composable (T, R) -> Unit
) {
    when {
        firstState.isLoading || secondState.isLoading -> {
            ProgressIndicatorScreen()
        }

        firstState.isError || secondState.isError -> {
            val errorMessage = firstState.error ?: secondState.error ?: "알 수 없는 오류가 발생했습니다."
            ErrorScreen(errorMessage) {
                if (firstState.isError) {
                    onRetry.first()
                }
                if (secondState.isError) {
                    onRetry.second()
                }
            }
        }

        firstState.isSuccess && secondState.isSuccess -> {
            firstState.data?.let { firstData ->
                secondState.data?.let { secondData ->
                    onSuccess(firstData, secondData)
                }
            }
        }
    }
}