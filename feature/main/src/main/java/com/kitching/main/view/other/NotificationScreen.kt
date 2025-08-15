package com.kitching.main.view.other

import com.kitching.main.R
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kitching.core.common.appresultscreen.CombinedUiStateHandler
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.common.widget.TabPager
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.main.view.model.NotificationViewModel
import com.kitching.main.view.other.tab.notificationTabs

@Composable
fun NotificationScreen(
    context: Context,
    commonState: CommonState
) {
    val viewModel: NotificationViewModel by lazy {
        NotificationViewModel(context)
    }

    val scheduleNotifications by viewModel.scheduleNotificationList.collectAsStateWithLifecycle()
    val noticeNotifications by viewModel.noticeNotificationList.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = stringResource(R.string.notification_title),
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {

        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {

        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchScheduleNotificationList()
        viewModel.fetchNoticeNotificationList()
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CombinedUiStateHandler(
                firstState = scheduleNotifications,
                secondState = noticeNotifications,
                onRetry = Pair(
                    { viewModel.fetchScheduleNotificationList() },
                    { viewModel.fetchNoticeNotificationList() }
                )
            ) { scheduleNotifications, noticeNotifications ->
                val tabs = notificationTabs(
                    scheduleNotifications,
                    noticeNotifications,
                    {},
                    {}
                )

                TabPager(
                    tabs = tabs,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    indicatorColor = PrimaryGreen300
                )
            }
        }
    }
}