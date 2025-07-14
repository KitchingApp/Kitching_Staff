package com.kitching.main.view.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.kitching.core.common.widget.DottedDivider
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray600
import com.kitching.domain.entities.Notice
import com.kitching.main.R
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.model.OtherViewModel
import com.kitching.main.view.other.item.NoticeCardItem

@Composable
fun NoticeScreen(
    commonState: CommonState,
    viewModel: OtherViewModel = viewModel(factory = viewModelFactory),
    onNoticeClick: (Notice) -> Unit,
    onBack: () -> Unit
) {
    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = stringResource(R.string.other_notice_title),
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    val noticeListResult by viewModel.noticeListResult.collectAsStateWithLifecycle()

    LaunchedEffect(teamId) {
        viewModel.getNoticeList(teamId)
    }

    KitchingStaffTheme {
        AppResultHandler(
            state = noticeListResult,
            onRetry = { viewModel.getNoticeList(teamId) },
            onSuccess = { noticeList ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(KitchingDimens.Margin.large),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xxSmall)
                ){
                    items(
                        items = noticeList,
                        key = { it.noticeId }
                    ) { notice ->
                        NoticeCardItem(notice) {
                            onNoticeClick(notice)
                        }

                        DottedDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = KitchingDimens.Margin.medium, vertical = KitchingDimens.Margin.xSmall)
                                .height(KitchingDimens.Border.xxxSmall),
                            color = NeutralGray600
                        )
                    }
                }
            }
        )
    }
}