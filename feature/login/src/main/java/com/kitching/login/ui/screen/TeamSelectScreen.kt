package com.kitching.login.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.appresultscreen.EmptyScreen
import com.kitching.core.common.appresultscreen.UiStateHandler
import com.kitching.core.common.widget.TeamCardItem
import com.kitching.core.common.commonstate.updateTeamInfo
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.login.R
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory

@Composable
fun TeamSelectScreen(
    context: Context,
    commonState: CommonState,
    goMain: () -> Unit,
    goInviteCode: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    val userId = commonState.appInfoState.value.userInfo?.userId

    val teamList by loginViewModel.teamList.collectAsStateWithLifecycle()
    val teamIdSaveState by loginViewModel.teamIdSaveResult.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        loginViewModel.getTeamList(userId.toString())
    }

    LaunchedEffect(teamIdSaveState) {
        when {
            teamIdSaveState.isSuccess -> {
                val team = teamIdSaveState.data

                commonState.updateTeamInfo(team)

                goMain()
            }

            teamIdSaveState.isError -> {
                commonState.snackBarState.showSnackbar(teamIdSaveState.error.toString())
            }
        }
    }

    KitchingStaffTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.default_padding))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.Kitching),
                        fontWeight = FontWeight.W900,
                        fontSize = dimensionResource(R.dimen.logo_font_size).value.sp,
                        color = PrimaryGreen300
                    )
                    IconButton(
                        onClick = goInviteCode
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(dimensionResource(R.dimen.go_to_invite_code_screen_icon_button_size)),
                            model = R.drawable.icon_add,
                            contentDescription = stringResource(R.string.icon_description_go_to_invite_code_screen),
                            colorFilter = ColorFilter.tint(PrimaryGreen300)
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.default_padding)))

                UiStateHandler (
                    uiState = teamList,
                    onRetry = {
                        loginViewModel.getTeamList(userId.toString())
                    }
                ) { teamList ->
                    if (teamList.isEmpty()) {
                        EmptyScreen(stringResource(R.string.empty_message_for_team_select_screen))
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.team_card_item_list_gap))
                            ) {
                                items(
                                    items = teamList,
                                    key = { team -> team.teamId }
                                ) { team ->
                                    TeamCardItem(
                                        team = team,
                                    ) {
                                        loginViewModel.saveTeamIdToDataStore(team.teamId, context)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}