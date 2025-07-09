package com.kitching.main.view.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.appresultscreen.AppResultHandler
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.main.R
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.model.OtherViewModel
import com.kitching.main.view.other.item.MemberCardItem

@Composable
fun MemberListScreen(
    commonState: CommonState,
    viewModel: OtherViewModel = viewModel(factory = viewModelFactory),
    onBack: () -> Unit
) {
    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()
    val teamName = commonState.appInfoState.value.teamInfo?.teamName.toString()

    val memberList by viewModel.memberListResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = stringResource(R.string.other_member_list_title, teamName),
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    LaunchedEffect(teamId) {
        viewModel.getAllMemberList(teamId)
    }

    KitchingStaffTheme {
        AppResultHandler(
            state = memberList,
            onRetry = { viewModel.getAllMemberList(teamId) },
            onSuccess = { memberList ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(KitchingDimens.Margin.large),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.large)
                ) {
                    items(
                        items = memberList,
                        key = { it.userId }
                    ) { member ->
                        MemberCardItem(member)
                    }
                }
            }
        )
    }
}