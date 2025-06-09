package com.kitching.main.view.order.carditem

import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kitching.domain.entities.Order

@Composable
fun OrderItemsList(
    orderItems: List<Order>,
    itemCounts: Map<String, Int>,
    onIncreaseClick: (Order) -> Unit,
    onDecreaseClick: (Order) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.order_card_between_card)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.order_card_between_card))
    ) {
        orderItems.forEach { order ->
            OrderCardItem(
                order = order,
                count = itemCounts[order.orderId] ?: 0,
                onIncreaseClick = { onIncreaseClick(order) },
                onDecreaseClick = { onDecreaseClick(order) }
            )
        }
    }
}