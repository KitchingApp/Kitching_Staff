package com.kitching.core.common.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kitching.core.common.R
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.entities.Team

@Composable
fun TeamCardItem(
    team: Team,
    onCardClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.team_card_item_height))
            .border(1.dp, PrimaryGreen300, RoundedCornerShape(dimensionResource(R.dimen.team_card_item_radius)))
            .clickable {
                onCardClick()
            },
        shape = RoundedCornerShape(dimensionResource(R.dimen.team_card_item_radius)),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = dimensionResource(R.dimen.defaultPadding)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.team_card_item_gap_between_icon_and_text))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_spatula_spoon),
                contentDescription = null,
                tint = PrimaryGreen300
            )
            Text(
                text = team.teamName,
                style = H3_m.copy(color = NeutralGray800)
            )
        }
    }
}