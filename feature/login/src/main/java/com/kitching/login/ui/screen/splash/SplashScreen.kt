package com.kitching.login.ui.screen.splash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.clearAppInfo
import com.kitching.core.common.commonstate.updateAppInfo
import com.kitching.core.common.commonstate.updateUserInfo
import com.kitching.login.SplashEntryPoint
import com.kitching.login.ui.model.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    context: Context,
    commonState: CommonState,
    goLogin: () -> Unit,
    goMain: () -> Unit,
    goTeamSelect: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val splashResult by loginViewModel.splashResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        delay(1000)

        loginViewModel.initializeAppInfoState(context)
    }

    LaunchedEffect(splashResult) {
        when {
            splashResult.isSuccess -> {
                val result = splashResult.data

                result?.let {
                    when (result.entryPoint) {
                        SplashEntryPoint.LOGIN -> {
                            commonState.clearAppInfo()
                            goLogin()
                        }
                        SplashEntryPoint.TEAM_SELECT -> {
                            val user = result.user

                            commonState.updateUserInfo(user)
                            goTeamSelect()
                        }
                        SplashEntryPoint.MAIN -> {
                            val user = result.user
                            val team = result.team

                            commonState.updateAppInfo(user, team)

                            goMain()
                        }
                    }
                }

            }

            splashResult.isError -> {
                commonState.clearAppInfo()
                goLogin()
            }
        }
    }

    SplashUi()

}