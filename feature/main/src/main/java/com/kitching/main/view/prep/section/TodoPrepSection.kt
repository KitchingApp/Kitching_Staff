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
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.common.util.hexToArgb
import com.kitching.main.view.prep.item.TodoPrepItem
import com.kitching.core.designsystem.H3_m
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.PrepCategory
import com.kitching.domain.entities.TodoPrepWithDetails

@Composable
fun TodoPrepSection(
    categoryName: String,
    todos: List<TodoPrepWithDetails>,
    categories: List<PrepCategory>,
    onDeletePrep: (TodoPrepWithDetails) -> Unit,
    onCheckedStatus: (String, Boolean) -> Unit,
) {
    val category = remember(categoryName, categories) {
        categories.find { it.categoryName == categoryName }!!
    }

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
                    color = Color(hexToArgb(category.color)),
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
                key(todoPrepWithDetails.todoPrep.id) {
                    TodoPrepItem(
                        todoPrepWithDetails = todoPrepWithDetails,
                        onDeletePrep = onDeletePrep,
                        onCheckedStatus = onCheckedStatus
                    )
                }
            }
        }
    }
}