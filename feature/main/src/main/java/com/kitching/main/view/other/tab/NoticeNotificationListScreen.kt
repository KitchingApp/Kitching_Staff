package com.kitching.main.view.other.tab

import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.appresultscreen.EmptyScreen
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.NoticeNotification
import com.kitching.main.view.other.item.NoticeNotificationCardItem

@Composable
fun NoticeNotificationListScreen(
    noticeNotifications: List<NoticeNotification>,
    onDeleteClick: () -> Unit
) {
    if (noticeNotifications.isEmpty()) {
        EmptyScreen(stringResource(R.string.notice_notification_empty_text))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .defaultHorizontalPadding(),
            contentPadding = PaddingValues(vertical = KitchingDimens.Margin.small),
            verticalArrangement = Arrangement.spacedBy(KitchingDimens.Margin.small)
        ) {
            items(noticeNotifications) { noticeNotification ->
                NoticeNotificationCardItem(
                    noticeNotification = noticeNotification,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}