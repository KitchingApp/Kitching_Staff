package com.kitching.main.view.order.carditem

import com.kitching.main.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.H3_m
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.ShadowColor
import com.kitching.core.designsystem.dropShadow
import com.kitching.domain.entities.OrderCategory
import androidx.core.graphics.toColorInt

@Composable
fun CategoryCardItem(
    category: OrderCategory,
    isExpanded: Boolean,
    onCardClick: () -> Unit,
) {
    // 그럼 그냥 16진수로 저장할까??
    val backgroundColor = Color("#${category.color}".toColorInt())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = dimensionResource(R.dimen.order_card_item_width), height = dimensionResource(R.dimen.order_card_item_height))
            .dropShadow(
                shape = RoundedCornerShape(dimensionResource(R.dimen.order_card_item_radius)),
                color = ShadowColor,
                blur = dimensionResource(R.dimen.order_blur),
                offsetX = dimensionResource(R.dimen.order_offset_x),
                offsetY = dimensionResource(R.dimen.order_offset_y),
                spread = dimensionResource(R.dimen.order_spread)
            )
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = dimensionResource(R.dimen.order_card_vertical_padding))
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = category.categoryName,
                style = H3_m.copy(color = NeutralGray800)
            )

            AsyncImage(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = dimensionResource(R.dimen.order_card_icon_end_padding)),
                model = if (isExpanded) R.drawable.icon_arrow_up else R.drawable.icon_arrow_down,
                contentDescription = if (isExpanded) "arrow up" else "arrow down",
            )
        }
    }
}