package com.kitching.login.ui.screen

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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kitching.core.common.CommonState
import com.kitching.core.common.EmptyScreen
import com.kitching.core.common.ResultConditionScreen
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.TeamCardItem
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.util.AppResult
import com.kitching.login.R
import com.kitching.login.ui.model.LoginViewModel
import com.kitching.login.ui.model.LoginViewModelFactory

@Composable
fun TeamSelectScreen(
    navController: NavController,
    commonState: CommonState,
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory())
) {
    val userId by loginViewModel.userId.collectAsStateWithLifecycle()
    val teamList by loginViewModel.teamList.collectAsStateWithLifecycle()
    val teamIdSaveState by loginViewModel.teamIdSaveResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginViewModel.getUserId(navController.context)
    }

    LaunchedEffect(userId) {
        if(userId is AppResult.Success) loginViewModel.getTeamList((userId as AppResult.Success).data)
    }

    LaunchedEffect(teamIdSaveState) {
        if(teamIdSaveState is AppResult.Success) navController.navigate(ScreenRouteDef.MainGraph.routeName)
        else if(teamIdSaveState is AppResult.Failure) { /** 팀아이디 저장실패시 */ }
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
                    AsyncImage(
                        modifier = Modifier.size(dimensionResource(R.dimen.go_to_invite_code_screen_icon_button_size)),
                        model = com.kitching.core.common.R.drawable.icon_add,
                        contentDescription = stringResource(R.string.icon_description_go_to_invite_code_screen),
                        colorFilter = ColorFilter.tint(PrimaryGreen300)
                    )
                }
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.default_padding)))
                ResultConditionScreen(
                    loadingCondition = userId is AppResult.Loading || teamList is AppResult.Loading || teamIdSaveState is AppResult.Loading,
                    successCondition = userId is AppResult.Success && teamList is AppResult.Success,
                    failCondition = userId is AppResult.Failure || teamList is AppResult.Failure,
                    onRetryBtnClick = {
                        var retryCount = 1
                        while (retryCount <= 3 && (teamList !is AppResult.Success || userId !is AppResult.Success)) {
                            if(userId is AppResult.Failure) loginViewModel.getUserId(navController.context)
                            else loginViewModel.getTeamList((userId as AppResult.Success).data)
                            retryCount++
                        }
                    }
                ) {
                    val teamListData = (teamList as AppResult.Success).data
                    if (teamListData.isEmpty()) {
                        EmptyScreen(stringResource(R.string.empty_message_for_team_select_screen))
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.team_card_item_list_gap))
                            ) {
                                items(teamListData) { team ->
                                    TeamCardItem(
                                        teamName = team.teamName
                                    ) {
                                        loginViewModel.saveTeamIdToDataStore(team.teamId, navController.context)
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