package com.kitching.main.view.prep.section

import androidx.compose.foundation.background
import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.domain.entities.TodoPrepByCategory
import androidx.core.graphics.toColorInt
import com.kitching.core.common.widget.TodoPrepItem
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray800

@Composable
fun TodoPrepSection(
    todoPrepByCategory: TodoPrepByCategory,
    onCheckedStatus: (String, Boolean) -> Unit,
) {
    val category = todoPrepByCategory.category
    val todos = todoPrepByCategory.todos

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = KitchingDimens.Margin.large),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(dimensionResource(R.dimen.prep_category_box_width))
                .padding(horizontal = KitchingDimens.Margin.xSmall)
                .background(
                    color = Color(category.color.toColorInt()),
                    shape = RoundedCornerShape(KitchingDimens.Corner.xSmall)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.categoryName,
                style = H3_m.copy(color = NeutralGray800),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(KitchingDimens.Margin.xxSmall)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xxxSmall)
        ) {
            todos.forEach { todoPrepWithDetails ->
                TodoPrepItem(
                    todoPrepWithDetails = todoPrepWithDetails,
                    onCheckedStatus = onCheckedStatus
                )
            }
        }
    }
}