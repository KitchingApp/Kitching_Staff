package com.kitching.login.ui.screen.splash

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.CommonState
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.util.AppResult
import com.kitching.login.R
import com.kitching.login.SplashEntryPoint
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory
import kotlinx.coroutines.async
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
                    SplashEntryPoint.LOGIN -> {}
                    SplashEntryPoint.TEAM_SELECT -> TODO()
                    SplashEntryPoint.MAIN -> TODO()
                }
            }

            else -> {}
        }
    }

    SplashUi()
}