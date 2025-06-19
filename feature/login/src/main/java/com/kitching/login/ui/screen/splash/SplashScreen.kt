package com.kitching.login.ui.screen.splash

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.clearAppInfo
import com.kitching.core.common.commonstate.updateAppInfo
import com.kitching.core.common.commonstate.updateUserInfo
import com.kitching.domain.util.AppResult
import com.kitching.login.SplashEntryPoint
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    context: Context,
    commonState: CommonState,
    goLogin: () -> Unit,
    goMain: () -> Unit,
    goTeamSelect: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    val splashResult by loginViewModel.splashResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        delay(1000)

        loginViewModel.initializeAppInfoState(context)
    }

    LaunchedEffect(splashResult) {
        when (splashResult) {
            is AppResult.Success -> {
                val result = (splashResult as AppResult.Success).data

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

            is AppResult.Failure -> {
                commonState.clearAppInfo()
                goLogin()
            }

            else -> {  }
        }
    }

    SplashUi()

}