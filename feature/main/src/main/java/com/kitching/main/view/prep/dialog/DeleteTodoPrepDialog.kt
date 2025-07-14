package com.kitching.main.view.prep.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import com.kitching.main.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.common.widget.CommonDialogComponent
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.TodoPrepWithDetails

@Composable
fun DeleteTodoPrepDialog(
    selectedTodoPrep: TodoPrepWithDetails,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    CommonDialogComponent(
        height = dimensionResource(R.dimen.prep_delete_dialog_height),
        paddingTop = KitchingDimens.Margin.xxLarge,
        paddingBottom = KitchingDimens.Margin.xxLarge,
        radius = KitchingDimens.Corner.large,
        confirmText = stringResource(R.string.prep_delete_dialog_confirm_text),
        onClickConfirm = { onConfirm(selectedTodoPrep.todoPrep.id) },
        cancelText = stringResource(R.string.prep_dialog_cancel_text),
        onClickCancel = onDismiss,
    ) {
        Text(
            modifier = Modifier.padding(top = KitchingDimens.Margin.xLarge),
            text = stringResource(R.string.prep_delete_dialog_message, selectedTodoPrep.prepName),
            style = Body1_m.copy(color = NeutralGray800),
            textAlign = TextAlign.Center,
        )
    }
}