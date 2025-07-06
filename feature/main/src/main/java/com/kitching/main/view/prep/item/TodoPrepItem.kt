package com.kitching.main.view.prep.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray400
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.domain.entities.TodoPrepWithDetails

@Composable
fun TodoPrepItem(
    todoPrepWithDetails: TodoPrepWithDetails,
    onCheckedStatus: (String, Boolean) -> Unit
) {
    val todoPrep = todoPrepWithDetails.todoPrep

    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
            style = H3_m.copy(
                color = NeutralGray800,
                textDecoration = if (todoPrep.done) TextDecoration.LineThrough else TextDecoration.None
            ),
            modifier = Modifier.weight(1f)
        )
    }
}