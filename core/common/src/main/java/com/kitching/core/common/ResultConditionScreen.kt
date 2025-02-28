package com.kitching.core.common

import androidx.compose.runtime.Composable

/**
 * Result condition screen
 *
 * @param loadingCondition 로딩 화면을 보여줄 상태 정의
 * @param successCondition 로드가 끝났을 때의 상태 정의(화면 띄워주기)
 * @param failCondition 실패 화면을 보여줄 상태 정의
 * @param onRetryBtnClick 실패 화면
 * @param successContent 로드가 끝났을 때 보여줄 화면
 */
@Composable
fun ResultConditionScreen(
    loadingCondition: Boolean,
    successCondition: Boolean,
    failCondition: Boolean,
    onRetryBtnClick: () -> Unit,
    successContent: @Composable () -> Unit
) {
    if(loadingCondition) {
        ProgressIndicatorScreen()
    } else if(successCondition) {
        successContent()
    } else if(failCondition) {
        DisConnectedScreen {
            onRetryBtnClick()
        }
    }
}