package com.kitching.main.view.other.tab

import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.appresultscreen.EmptyScreen
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.main.view.other.item.ScheduleNotificationCardItem

@Composable
fun ScheduleNotificationListScreen(
    scheduleNotifications: List<ScheduleNotification>,
    onDeleteClick: (Long) -> Unit
) {
    if (scheduleNotifications.isEmpty()) {
        EmptyScreen(stringResource(R.string.schedule_notification_empty_text))
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .defaultHorizontalPadding(),
            contentPadding = PaddingValues(vertical = KitchingDimens.Margin.small),
            verticalArrangement = Arrangement.spacedBy(KitchingDimens.Margin.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(scheduleNotifications) { scheduleNotification ->
                ScheduleNotificationCardItem(
                    scheduleNotification = scheduleNotification,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    }
}