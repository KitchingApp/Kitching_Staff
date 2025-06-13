package com.kitching.login.ui.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.CommonState
import com.kitching.core.common.ProgressIndicatorScreen
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray300
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.entities.User
import com.kitching.domain.util.AppResult
import com.kitching.login.R
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    context: Context,
    commonState: CommonState,
    onLoginSuccess: (User) -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val kakaoLoginState by loginViewModel.kakaoLoginState.collectAsStateWithLifecycle()

    LaunchedEffect(kakaoLoginState) {
        when (kakaoLoginState) {
            is AppResult.Success -> {
                val user = (kakaoLoginState as AppResult.Success<User>).data
                onLoginSuccess(user)
            }

            is AppResult.Failure -> {
                val exception = (kakaoLoginState as AppResult.Failure).exception
                commonState.snackBarState.showSnackbar(exception.message.toString())
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(250.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.logo_dish_width))
                    .height(dimensionResource(R.dimen.logo_dish_height))
                    .clickable { showBottomSheet = true },
                model = R.drawable.logo_dish,
                contentDescription = null,
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.logo_with_text_padding)))
            Text(
                text = stringResource(R.string.Kitching),
                fontWeight = FontWeight.Black,
                color = PrimaryGreen300,
                fontSize = dimensionResource(R.dimen.logo_font_size).value.sp,
            )
        }
        Text(
            text = stringResource(R.string.login_welcome_message),
            fontWeight = FontWeight.Medium,
            color = NeutralGray800,
            fontSize = dimensionResource(R.dimen.login_welcome_message_font_size).value.sp,
            textAlign = TextAlign.Center,
            lineHeight = dimensionResource(R.dimen.login_welcome_message_line_height).value.sp
        )
        // 카카오 로그인 버튼
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.button_kakao_login_width))
                .height(dimensionResource(R.dimen.button_kakao_login_height))
                .clickable { showBottomSheet = true },
            model = R.drawable.button_kakao_login,
            contentDescription = null,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.button_kakao_login_padding_bottom)))

        if (showBottomSheet) {
            ModalBottomSheet(
                containerColor = NeutralGray0,
                dragHandle = null,
                shape = RoundedCornerShape(dimensionResource(R.dimen.login_bottom_modal_sheet_radius)),
                onDismissRequest = { showBottomSheet = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.login_bottom_modal_sheet_height)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.login_bottom_sheet_message),
                        fontWeight = FontWeight.Medium,
                        color = NeutralGray800,
                        fontSize = dimensionResource(R.dimen.login_bottom_modal_sheet_message_font_size).value.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.login_bottom_modal_sheet_spacer_height))
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.login_bottom_modal_sheet_button_gap))
                    ) {
                        TextButton(
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.login_bottom_modal_sheet_button_width))
                                .height(dimensionResource(R.dimen.login_bottom_modal_sheet_button_height)),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.login_bottom_modal_sheet_button_radius)),
                            colors = ButtonDefaults.textButtonColors().copy(
                                containerColor = PrimaryGreen300,
                                contentColor = NeutralGray0
                            ),
                            onClick = {
                                showBottomSheet = false
                                loginViewModel.performKakaoLogin(context)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.login_bottom_sheet_confirm_text)
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.login_bottom_modal_sheet_button_width))
                                .height(dimensionResource(R.dimen.login_bottom_modal_sheet_button_height)),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.login_bottom_modal_sheet_button_radius)),
                            colors = ButtonDefaults.textButtonColors().copy(
                                containerColor = NeutralGray300,
                                contentColor = NeutralGray0
                            ),
                            onClick = { showBottomSheet = false }
                        ) {
                            Text(
                                text = stringResource(R.string.login_bottom_sheet_cancel_text)
                            )
                        }
                    }
                }
            }
        }
    }

    if (kakaoLoginState is AppResult.Loading) {
        ProgressIndicatorScreen()
    }
}