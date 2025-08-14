package com.kitching.main.view.other.item

import androidx.compose.foundation.clickable
import com.kitching.main.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray400
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification

@Composable
fun ScheduleNotificationCardItem(
    scheduleNotification: ScheduleNotification,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = KitchingDimens.Margin.small, vertical = KitchingDimens.Margin.xxSmall),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.schedule_notification_date, scheduleNotification.scheduleDate, scheduleNotification.scheduleTimeName),
                    style = Body1_m.copy(NeutralGray800)
                )

                Text(
                    text = scheduleNotification.rejectReason,
                    style = Body1_m.copy(NeutralGray800)
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(KitchingDimens.Size.large)
                    .clickable { onDeleteClick() },
                colorFilter = ColorFilter.tint(NeutralGray400),
                model = R.drawable.icon_trash,
                contentDescription = "trash icon"
            )
        }
    }
}

@Composable
fun NoticeNotificationCardItem(
    noticeNotification: NoticeNotification,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = KitchingDimens.Margin.small, vertical = KitchingDimens.Margin.xxSmall),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = noticeNotification.title,
                    style = Body1_m.copy(NeutralGray800)
                )

                Text(
                    text = noticeNotification.writerName,
                    style = Body1_m.copy(NeutralGray800)
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(KitchingDimens.Size.large)
                    .clickable { onDeleteClick() },
                colorFilter = ColorFilter.tint(NeutralGray400),
                model = R.drawable.icon_trash,
                contentDescription = "trash icon"
            )
        }
    }
}