package com.kitching.main.view.other.item

import androidx.compose.foundation.clickable
import com.kitching.main.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray600
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen50
import com.kitching.domain.entities.NoticeNotification
import com.kitching.domain.entities.ScheduleNotification

@Composable
fun ScheduleNotificationCardItem(
    scheduleNotification: ScheduleNotification,
    onDeleteClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .size(
                width = dimensionResource(R.dimen.schedule_notification_card_width),
                height = dimensionResource(R.dimen.schedule_notification_card_height)
            ),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryGreen50
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(KitchingDimens.Margin.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.schedule_notification_date, scheduleNotification.scheduleDate, scheduleNotification.scheduleTimeName),
                    style = Body1_m.copy(NeutralGray600)
                )

                Spacer(modifier = Modifier.height(KitchingDimens.Margin.xxSmall))

                Text(
                    text = scheduleNotification.rejectReason,
                    style = Body1_m.copy(NeutralGray800),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(KitchingDimens.Size.large)
                    .clickable { onDeleteClick(scheduleNotification.id) },
                colorFilter = ColorFilter.tint(NeutralGray600),
                model = R.drawable.icon_trash,
                contentDescription = "trash icon"
            )
        }
    }
}

@Composable
fun NoticeNotificationCardItem(
    noticeNotification: NoticeNotification,
    onDeleteClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .size(
                width = dimensionResource(R.dimen.notice_notification_card_width),
                height = dimensionResource(R.dimen.notice_notification_card_height)
            ),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryGreen50
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(KitchingDimens.Margin.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = noticeNotification.title,
                    style = Body1_m.copy(NeutralGray800),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(KitchingDimens.Margin.xxSmall))

                Text(
                    text = noticeNotification.writerName,
                    style = Body1_m.copy(NeutralGray600)
                )
            }

            AsyncImage(
                modifier = Modifier
                    .size(KitchingDimens.Size.large)
                    .clickable { onDeleteClick(noticeNotification.id) },
                colorFilter = ColorFilter.tint(NeutralGray600),
                model = R.drawable.icon_trash,
                contentDescription = "trash icon"
            )
        }
    }
}