package com.kitching.login.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.designsystem.Body1
import com.kitching.core.designsystem.H2
import com.kitching.core.designsystem.H3_m
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray300
import com.kitching.core.designsystem.NeutralGray600
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.domain.entities.Team
import com.kitching.domain.util.AppResult
import com.kitching.login.R
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteCodeScreen(
    commonState: CommonState,
    goTeamSelect: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    val userId = commonState.appInfoState.value.userInfo?.userId.toString()
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val joinTeamResult by loginViewModel.joinTeamResult.collectAsStateWithLifecycle()

    LaunchedEffect(joinTeamResult) {
        when (joinTeamResult) {
            is AppResult.Success -> {
                val team = (joinTeamResult as AppResult.Success<Team>).data
                commonState.snackBarState.showSnackbar("${team.teamName}에 합류하였습니다.")
                goTeamSelect()
            }
            is AppResult.Failure -> {
                val exception = (joinTeamResult as AppResult.Failure).exception
                commonState.snackBarState.showSnackbar(exception.message.toString())
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = NeutralGray800,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.invite_code),
                        style = H3_m.copy(NeutralGray800)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NeutralGray0,
                    titleContentColor = NeutralGray800,
                    actionIconContentColor = NeutralGray600,
                    navigationIconContentColor = NeutralGray600
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        goTeamSelect()
                    }) {
                        AsyncImage(
                            modifier = Modifier.size(dimensionResource(R.dimen.top_app_bar_for_invite_code_screen_navigation_icon_size)),
                            model = R.drawable.icon_line_back,
                            contentDescription = stringResource(R.string.icon_description_go_to_team_select_screen),
                            colorFilter = ColorFilter.tint(NeutralGray600)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        KitchingStaffTheme {
            Surface (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.default_padding)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.default_padding))
                ) {
                    Text(
                        text = stringResource(R.string.insert_invite_code),
                        style = H2.copy(NeutralGray800)
                    )
                    Text(
                        text = stringResource(R.string.insert_invite_code_message),
                        style = Body1.copy(NeutralGray800)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.invite_code_text_field_height)),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.default_padding))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .border(
                                    border = BorderStroke(
                                        dimensionResource(R.dimen.invite_code_text_field_border),
                                        NeutralGray300
                                    ),
                                    shape = RoundedCornerShape(dimensionResource(R.dimen.invite_code_text_field_radius))
                                )
                                .padding(dimensionResource(R.dimen.default_padding), dimensionResource(R.dimen.invite_code_text_field_padding)),
                        ) {
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center),
                                value = textState.value,
                                onValueChange = {
                                    textState.value = it
                                },
                                textStyle = Body1.copy(
                                    color = NeutralGray800,
                                    textAlign = TextAlign.Start
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(NeutralGray800),
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.invite_code_button_width))
                                .height(
                                    dimensionResource(R.dimen.invite_code_text_field_height)
                                ),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.invite_code_button_radius)),
                            colors = ButtonDefaults.textButtonColors().copy(
                                containerColor = PrimaryGreen300,
                                contentColor = NeutralGray0
                            ),
                            onClick = {
                                loginViewModel.joinTeamByInviteCode(userId, textState.value.text)
                            }
                        ) {
                            Text(text = stringResource(R.string.input))
                        }
                    }
                }
            }
        }
    }

    if (joinTeamResult is AppResult.Loading) {
        ProgressIndicatorScreen()
    }
}