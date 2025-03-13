package com.kitching.main.schedule.tab

import com.kitching.main.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kitching.core.designsystem.theme.Body1_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen50
import com.kitching.core.designsystem.theme.PrimaryGreen700

@Composable
fun ScheduleCardItem(
    userName: String,
    scheduleTimeName: String,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.schedule_card_item_height))
            .border(dimensionResource(R.dimen.one_dp), PrimaryGreen50, RoundedCornerShape(dimensionResource(R.dimen.schedule_card_item_radius))),
        shape = RoundedCornerShape(dimensionResource(R.dimen.schedule_card_item_radius)),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
            disabledContentColor = NeutralGray800
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(R.dimen.schedule_card_item_vertical_padding)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userName,
                    style = Body1_m.copy(color = NeutralGray800)
                )

                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.schedule_card_item_between_padding)))

                Text(
                    text = scheduleTimeName,
                    style = Body1_m.copy(color = PrimaryGreen700)
                )
            }
        }
    }
}