package com.kitching.login.ui.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.login.R
import com.kitching.login.SplashEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    goLogin: () -> Unit,
    goMain: () -> Unit
) {
    LaunchedEffect(Unit) {
        var entryPoint = SplashEntryPoint.MAIN

        val job = async {
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    entryPoint = if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            SplashEntryPoint.LOGIN
                        } else {
                            AuthApiClient.instance.tokenManagerProvider.manager.clear()
                            SplashEntryPoint.LOGIN
                        }
                    } else {
                        SplashEntryPoint.MAIN
                    }
                }
            } else {
                entryPoint = SplashEntryPoint.LOGIN
            }
        }

        delay(2000)
        job.await()

        when (entryPoint) {
            SplashEntryPoint.INIT -> {}
            SplashEntryPoint.LOGIN -> goLogin()
            SplashEntryPoint.MAIN -> goMain()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.logo_dish_width))
                .height(dimensionResource(R.dimen.logo_dish_height)),
            model = R.drawable.logo_dish,
            contentDescription = null,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.logo_with_text_padding)))
        Text(
            text = stringResource(R.string.Kitching),
            fontWeight = FontWeight.Black,
            color = PrimaryGreen300,
            fontSize = dimensionResource(R.dimen.logo_font_size).value.sp
        )
    }
}