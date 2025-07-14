package com.kitching.main.view.other.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import coil3.compose.AsyncImage
import com.kitching.core.common.widget.KitchingVerticalDivider
import com.kitching.core.designsystem.Body2_m
import com.kitching.core.designsystem.Caption1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray400
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.Comment
import com.kitching.main.R

@Composable
fun CommentsItem(
    userId: String,
    comment: Comment,
    onDeleteClick: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = KitchingDimens.Margin.xxSmall),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
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

        if (userId == comment.userId) {
            Box(
                modifier = Modifier
                    .padding(horizontal = KitchingDimens.Margin.xMedium)
                    .clickable {
                        onDeleteClick(comment.id)
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(KitchingDimens.Size.large),
                    model = R.drawable.icon_trash,
                    colorFilter = ColorFilter.tint(NeutralGray400),
                    contentDescription = "trash icon"
                )
            }
        }
    }
}