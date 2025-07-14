package com.kitching.main.view.prep.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.entities.TodoPrepWithDetails

@Composable
fun TodoPrepList(
    todoPrepData: TodoPrepData,
    onDeletePrep: (TodoPrepWithDetails) -> Unit,
    onCheckedStatus: (String, Boolean) -> Unit,
) {
    val groupedTodos = remember(todoPrepData.todos) {
        todoPrepData.todos.groupBy { it.categoryName }.toList()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .defaultHorizontalPadding(),
        contentPadding = PaddingValues(KitchingDimens.Margin.xSmall),
        verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xLarge)
    ) {
        items (
            items = groupedTodos,
            key = { (categoryName, _) -> categoryName }
        ) { (categoryName, todos) ->
            TodoPrepSection(
                categoryName = categoryName,
                todos = todos,
                categories = todoPrepData.categories,
                onDeletePrep = onDeletePrep,
                onCheckedStatus = onCheckedStatus
            )
        }
    }
}