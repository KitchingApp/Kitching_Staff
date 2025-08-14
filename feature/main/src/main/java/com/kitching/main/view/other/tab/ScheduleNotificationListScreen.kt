package com.kitching.main.view.other.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.ScheduleNotification
import com.kitching.main.view.other.item.ScheduleNotificationCardItem

@Composable
fun ScheduleNotificationListScreen(
    scheduleNotifications: List<ScheduleNotification>,
    onDeleteClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultHorizontalPadding(),
        contentPadding = PaddingValues(vertical = KitchingDimens.Margin.small),
        verticalArrangement = Arrangement.spacedBy(KitchingDimens.Margin.small)
    ) {
        items(scheduleNotifications) { scheduleNotification ->
            ScheduleNotificationCardItem(
                scheduleNotification = scheduleNotification,
                onDeleteClick = onDeleteClick
            )
        }
    }
}