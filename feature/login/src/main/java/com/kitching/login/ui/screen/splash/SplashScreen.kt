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
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.login.R
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    context: Context,
    goLogin: () -> Unit,
    goMain: () -> Unit,
    goTeamSelect: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    val userId by loginViewModel.userId.collectAsStateWithLifecycle()
    val teamId by loginViewModel.teamId.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val splashJob = async {
            loginViewModel.getUserId(context)
            loginViewModel.getTeamIdFromDataStore(context)
        }

        delay(2000)
        splashJob.await()
    }

    LaunchedEffect(userId, teamId) {
//        if (userId is AppResult.Success && teamId is AppResult.Success) {
//            if((userId as AppResult.Success<String>).data !== "") {
//                if((teamId as AppResult.Success<String>).data !== "") {
//                    goMain()
//                } else {
//                    goTeamSelect()
//                }
//            } else {
//                goLogin()
//            }
//        }
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