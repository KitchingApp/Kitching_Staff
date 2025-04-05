package com.kitching.main.order.carditem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray300
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.domain.entities.Order
import com.kitching.main.R

@Composable
fun OrderCardItem(
    order: Order,
    count: Int,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = dimensionResource(R.dimen.order_card_item_width), height = dimensionResource(R.dimen.order_card_item_height)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = dimensionResource(R.dimen.one_dp),
            color = NeutralGray300
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.order_card_vertical_padding), horizontal = dimensionResource(R.dimen.order_card_horizontal_padding)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = order.orderName,
                style = H3_m.copy(color = NeutralGray800)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDecreaseClick
                ) {
                    AsyncImage(
                        model = R.drawable.icon_minus,
                        contentDescription = "감소"
                    )
                }

                Text(
                    text = count.toString(),
                    style = H3_m.copy(color = NeutralGray800)
                )

                IconButton(
                    onClick = onIncreaseClick
                ) {
                    AsyncImage(
                        model = R.drawable.icon_plus,
                        contentDescription = "증가"
                    )
                }
            }
        }
    }
}