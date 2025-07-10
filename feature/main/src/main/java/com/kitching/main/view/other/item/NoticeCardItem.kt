package com.kitching.main.view.other.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kitching.core.designsystem.theme.Body1_m
import com.kitching.core.designsystem.theme.Caption1_R
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray600
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.domain.entities.Notice

@Composable
fun NoticeCardItem(
    notice: Notice,
    onNoticeClick: (Notice) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = KitchingDimens.Margin.large)
            .clickable { onNoticeClick(notice) },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = notice.title,
            style = Body1_m.copy(NeutralGray800),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(end = KitchingDimens.Margin.xSmall),
                text = notice.writerName,
                style = Caption1_R.copy(NeutralGray600),
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.weight(1f),
                text = notice.date,
                style = Caption1_R.copy(NeutralGray600),
                overflow = TextOverflow.Ellipsis,
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = notice.content,
            style = Caption1_R.copy(NeutralGray800),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}