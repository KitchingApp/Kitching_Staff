package com.kitching.main.view.other.tab

import com.kitching.main.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.widget.TabItem
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification

@Composable
fun notificationTabs(
    scheduleNotifications: List<ScheduleNotification>,
    noticeNotifications: List<NoticeNotification>,
    onDeleteScheduleClick: (Long) -> Unit,
    onDeleteNoticeClick: (Long) -> Unit
): List<TabItem> {
    return listOf(
        TabItem(
            title = stringResource(R.string.notification_schedule_tab),
            content = {
                ScheduleNotificationListScreen(
                    scheduleNotifications = scheduleNotifications,
                    onDeleteClick = onDeleteScheduleClick
                )
            }
        ),
        TabItem(
            title = stringResource(R.string.notification_notice_tab),
            content = {
                NoticeNotificationListScreen(
                    noticeNotifications = noticeNotifications,
                    onDeleteClick = onDeleteNoticeClick
                )
            }
        )
    )
}