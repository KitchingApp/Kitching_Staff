package com.kitching.main.view.other

import com.kitching.main.R
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.appresultscreen.CombinedUiStateHandler
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.common.widget.TabPager
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.main.factory.NotificationViewModelFactory
import com.kitching.main.view.model.NotificationViewModel
import com.kitching.main.view.other.dialog.DeleteAllNotificationDialog
import com.kitching.main.view.other.dialog.DeleteNotificationDialog
import com.kitching.main.view.other.tab.notificationTabs

@Composable
fun NotificationScreen(
    context: Context,
    commonState: CommonState,
    popBackStack: () -> Unit,
    viewModel: NotificationViewModel = viewModel(factory = NotificationViewModelFactory(context))
) {

    val scheduleNotifications by viewModel.scheduleNotificationList.collectAsStateWithLifecycle()
    val noticeNotifications by viewModel.noticeNotificationList.collectAsStateWithLifecycle()

    var selectedScheduleNotificationId by remember { mutableStateOf<Long?>(null) }
    var selectedNoticeNotificationId by remember { mutableStateOf<Long?>(null) }
    var showAllDeleteDialog by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState(initialPage = 0) { 2 }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = stringResource(R.string.notification_title),
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            popBackStack()
        },
        actionIconInfo = ActionIconInfo.DELETE,
        onClickActionIcon = {
            showAllDeleteDialog = true
        }
    )

    LaunchedEffect(Unit) {
        viewModel.fetchScheduleNotificationList()
        viewModel.fetchNoticeNotificationList()
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = KitchingDimens.Margin.small),
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
                TabPager(
                    tabs = notificationTabs(
                        scheduleNotifications,
                        noticeNotifications,
                        { id ->
                            selectedScheduleNotificationId = id
                        },
                        { id ->
                            selectedNoticeNotificationId = id

                        }
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    indicatorColor = PrimaryGreen300,
                    pagerState = pagerState
                )
            }
        }
    }

    selectedScheduleNotificationId?.let { id ->
        DeleteNotificationDialog(
            onDismiss = { selectedScheduleNotificationId = null },
            onConfirm = {
                viewModel.deleteScheduleNotification(id)
                selectedScheduleNotificationId = null
                viewModel.fetchScheduleNotificationList()
            }
        )
    }

    selectedNoticeNotificationId?.let { id ->
        DeleteNotificationDialog(
            onDismiss = { selectedNoticeNotificationId = null },
            onConfirm = {
                viewModel.deleteNoticeNotification(id)
                selectedNoticeNotificationId = null
                viewModel.fetchNoticeNotificationList()
            }
        )
    }

    if (showAllDeleteDialog) {
        DeleteAllNotificationDialog(
            currentTab = pagerState.currentPage,
            onDismiss = { showAllDeleteDialog = false }
        ) { tabIndex ->
            when (tabIndex) {
                0 -> {
                    viewModel.deleteAllScheduleNotification()
                    viewModel.fetchScheduleNotificationList()
                }

                1 -> {
                    viewModel.deleteAllNoticeNotification()
                    viewModel.fetchNoticeNotificationList()
                }
            }
            showAllDeleteDialog = false
        }
    }
}