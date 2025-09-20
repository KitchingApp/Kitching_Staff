package com.kitching.main.view.other.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.kitching.core.common.util.CoilImageRequest
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray300
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.Member
import com.kitching.main.R

@Composable
fun MemberCardItem(
    member: Member,
) {
    Card(
        modifier = Modifier
            .size(
                dimensionResource(id = R.dimen.other_member_card_width),
                dimensionResource(id = R.dimen.other_member_card_height)
            )
            .padding(KitchingDimens.Margin.large, KitchingDimens.Margin.small),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            contentColor = NeutralGray800,
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xSmall)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = KitchingDimens.Margin.xxLarge)
                    .size(dimensionResource(R.dimen.other_member_card_image_size))
                    .clip(RoundedCornerShape(KitchingDimens.Corner.large)),
                model = CoilImageRequest.getImageRequest(member.userImage),
                contentScale = ContentScale.Crop,
                contentDescription = "userImage"
            )

            Text(
                modifier = Modifier.weight(1f),
                text = member.userName,
                style = MaterialTheme.typography.bodyMedium.copy(color = NeutralGray800),
            )

            Text(
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = member.staffLevelName.ifEmpty { stringResource(R.string.other_staff_level_name_empty) },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (member.staffLevelName.isEmpty()) NeutralGray300 else NeutralGray800
                )
            )
        }
    }
}