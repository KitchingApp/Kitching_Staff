package com.kitching.core.common.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.kitching.core.R
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray300
import com.kitching.core.designsystem.NeutralGray800

@Composable
fun DrawerOtherItem(
    text: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.drawer_other_card_item_height))
            .border(1.dp, NeutralGray300, RoundedCornerShape(dimensionResource(R.dimen.drawer_other_card_item_radius)))
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(dimensionResource(R.dimen.drawer_other_card_item_radius)),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(color = NeutralGray800)
            )
        }
    }
}