package com.kitching.main.view.other.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import com.kitching.main.R
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray300
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.Member

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.other_member_card_image_size))
                    .clip(RoundedCornerShape(KitchingDimens.Corner.large)),
                model = member.userImage,
                contentScale = ContentScale.Crop,
                contentDescription = "userImage"
            )

            Text(
                text = member.userName,
                style = Body1_m.copy(color = NeutralGray800),
            )

            if (member.staffLevelName.isEmpty()) {
                Text(
                    text = stringResource(R.string.other_staff_level_name_empty),
                    style = Body1_m.copy(color = NeutralGray300),
                )
            } else {
                Text(
                    text = member.staffLevelName,
                    style = Body1_m.copy(color = NeutralGray800),
                )
            }
        }
    }
}