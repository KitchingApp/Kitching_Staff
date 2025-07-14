package com.kitching.main.view.drawer

import com.kitching.main.R
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.core.common.appresultscreen.AppResultHandler
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.updateTeamInfo
import com.kitching.core.common.widget.KitchingHorizontalDivider
import com.kitching.core.designsystem.H1
import com.kitching.core.designsystem.H3
import com.kitching.core.designsystem.H3_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray300
import com.kitching.core.designsystem.NeutralGray600
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.domain.entities.Team
import com.kitching.domain.util.AppResult
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.drawer.list.DrawerOtherList
import com.kitching.main.view.drawer.list.DrawerTeamList
import com.kitching.main.view.model.DrawerViewModel
import kotlinx.coroutines.launch

@Composable
fun CustomDrawer(
    drawerState: DrawerState,
    commonState: CommonState,
    context: Context,
    onInviteCodeClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onMemberClick: () -> Unit,
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
                        .padding(KitchingDimens.Margin.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = KitchingDimens.Margin.xMedium),
                        text = stringResource(R.string.drawer_profile),
                        style = H1.copy(color = NeutralGray800)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.small)
                    ) {
                        AsyncImage(
                            model = userInfo?.userImage ?: R.drawable.img_group,
                            contentDescription = "프로필 사진",
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.drawer_profile_image_size))
                                .clip(RoundedCornerShape(KitchingDimens.Corner.small))
                                .border(
                                    width = dimensionResource(R.dimen.drawer_profile_border_width),
                                    color = NeutralGray300,
                                    shape = RoundedCornerShape(KitchingDimens.Corner.small)
                                ),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = userInfo?.userName ?: "",
                            style = H3.copy(color = NeutralGray800),
                        )

                        AsyncImage(
                            modifier = Modifier.size(dimensionResource(R.dimen.drawer_alarm_icon_size)),
                            model = R.drawable.icon_bell,
                            colorFilter = ColorFilter.tint(NeutralGray600),
                            contentDescription = "알림"
                        )
                    }

                    KitchingHorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = KitchingDimens.Margin.medium),
                        thickness = KitchingDimens.Border.xSmall,
                        color = PrimaryGreen300
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = KitchingDimens.Margin.xSmall),
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

                    KitchingHorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = KitchingDimens.Margin.medium),
                        thickness = KitchingDimens.Border.xSmall,
                        color = PrimaryGreen300
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = KitchingDimens.Margin.xSmall),
                        text = stringResource(R.string.drawer_other_list),
                        style = H3_m.copy(color = NeutralGray800)
                    )

                    DrawerOtherList(
                        onInviteCodeClick = {
                            commonState.scope.launch {
                                drawerState.close()
                            }
                            onInviteCodeClick()
                        },
                        onNoticeClick = {
                            commonState.scope.launch {
                                drawerState.close()
                            }
                            onNoticeClick()
                        },
                        onMemberClick = {
                            commonState.scope.launch {
                                drawerState.close()
                            }
                            onMemberClick()
                        }
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