package com.kitching.main.view.drawer

import com.kitching.main.R
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.appresultscreen.AppResultHandler
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.updateTeamInfo
import com.kitching.core.designsystem.theme.H3
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.entities.Team
import com.kitching.domain.util.AppResult
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.drawer.list.DrawerTeamList
import com.kitching.main.view.model.DrawerViewModel

@Composable
fun CustomDrawer(
    drawerState: DrawerState,
    commonState: CommonState,
    context: Context,
    changeTeamId: () -> Unit,
    drawerViewModel: DrawerViewModel = viewModel(factory = viewModelFactory),
    content: @Composable () -> Unit,
) {
    val userId = commonState.appInfoState.value.userInfo?.userId.toString()
    val currentTeamId = commonState.appInfoState.value.teamInfo?.teamId.toString()
    val userInfo = commonState.appInfoState.value.userInfo

    val teamListResult by drawerViewModel.teamListResult.collectAsStateWithLifecycle()
    val teamChangeResult by drawerViewModel.teamChangeResult.collectAsStateWithLifecycle()

    LaunchedEffect(userId) {
        drawerViewModel.getTeamListByUserId(userId)
    }

    LaunchedEffect(teamChangeResult) {
        when (teamChangeResult) {
            is AppResult.Success<*> -> {
                val newTeam = (teamChangeResult as AppResult.Success<Team>).data
                val message = context.getString(R.string.drawer_team_change_success, newTeam.teamName)

                commonState.updateTeamInfo(newTeam)
                drawerState.close()
                changeTeamId()
                commonState.snackBarState.showSnackbar(message)
            }
            is AppResult.Failure -> {
                val message = context.getString(R.string.drawer_team_change_fail)
                commonState.snackBarState.showSnackbar(message)
            }
            else -> {}
        }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = NeutralGray0,
            ) {
                Column(
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.drawer_page_width))
                        .padding(dimensionResource(R.dimen.drawer_page_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    AsyncImage(
                        model = userInfo?.userImage ?: R.drawable.img_group,
                        contentDescription = "프로필 사진",
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.drawer_profile_image_size))
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                            .border(
                                width = dimensionResource(R.dimen.drawer_profile_border_width),
                                color = PrimaryGreen300,
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
                        text = userInfo?.userName ?: "",
                        style = H3.copy(color = NeutralGray800),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
                        thickness = dimensionResource(R.dimen.drawer_divider_thickness),
                        color = PrimaryGreen300
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
                        text = stringResource(R.string.drawer_team_list),
                        style = H3_m.copy(color = NeutralGray800)
                    )

                    AppResultHandler(
                        state = teamListResult,
                        onSuccess = { teamList ->
                            DrawerTeamList(
                                teamList = teamList.filter { it.teamId != currentTeamId },
                                onTeamClick = { team ->
                                    drawerViewModel.changeTeam(context, team.teamId)
                                }
                            )
                        }
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
                        thickness = dimensionResource(R.dimen.drawer_divider_thickness),
                        color = PrimaryGreen300
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
                        text = stringResource(R.string.drawer_other_list),
                        style = H3_m.copy(color = NeutralGray800)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        content()
    }

    if (teamChangeResult is AppResult.Loading) {
        ProgressIndicatorScreen()
    }
}