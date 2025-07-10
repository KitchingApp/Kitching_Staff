package com.kitching.main.view.other.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kitching.core.common.widget.KitchingVerticalDivider
import com.kitching.core.designsystem.theme.Body2_m
import com.kitching.core.designsystem.theme.Caption1_m
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.domain.entities.Comment

@Composable
fun CommentsItem(
    comment: Comment,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = KitchingDimens.Margin.xxSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = comment.userName,
                style = Body2_m.copy(color = NeutralGray800)
            )

            KitchingVerticalDivider(
                modifier = Modifier
                    .height(KitchingDimens.Border.large)
                    .padding(horizontal = KitchingDimens.Margin.xSmall),
                color = NeutralGray800,
                thickness = KitchingDimens.Border.xxxSmall
            )

            Text(
                text = comment.formattedDate,
                style = Caption1_m.copy(color = NeutralGray800)
            )
        }

        Text(
            modifier = Modifier.padding(top = KitchingDimens.Margin.xxSmall),
            text = comment.content,
            style = Caption1_m.copy(color = NeutralGray800)
        )
    }
}