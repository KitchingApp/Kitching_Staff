package com.kitching.core.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray300
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300

@Composable
fun TeamCardItem(
    teamName: String,
    onCardClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.width(260.dp).height(60.dp)
            .border(1.dp, NeutralGray300, RoundedCornerShape(8.dp))
            .clickable {
                onCardClick()
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_spatula_spoon),
                contentDescription = "spatula and spoon icon",
                tint = PrimaryGreen300
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = teamName,
                style = H3_m.copy(color = NeutralGray800)
            )
        }
    }
}