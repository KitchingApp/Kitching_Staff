package com.kitching.main.view.prep.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray400
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.domain.entities.TodoPrepWithDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoPrepItem(
    todoPrepWithDetails: TodoPrepWithDetails,
    onDeletePrep: (TodoPrepWithDetails) -> Unit,
    onCheckedStatus: (String, Boolean) -> Unit
) {
    val todoPrep = todoPrepWithDetails.todoPrep

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onCheckedStatus(todoPrep.id, !todoPrep.done)
                },
                onLongClick = {
                    onDeletePrep(todoPrepWithDetails)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = todoPrep.done,
            onCheckedChange = { newChecked ->
                onCheckedStatus(todoPrep.id, newChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryGreen300,
                uncheckedColor = NeutralGray400,
                checkmarkColor = NeutralGray0
            )
        )

        Text(
            text = todoPrepWithDetails.prepName,
            style = MaterialTheme.typography.titleLarge.copy(
                color = NeutralGray800,
                textDecoration = if (todoPrep.done) TextDecoration.LineThrough else TextDecoration.None
            ),
            modifier = Modifier.weight(1f)
        )
    }
}