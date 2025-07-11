package com.kitching.main.view.other.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.common.widget.CommonDialogComponent
import com.kitching.core.designsystem.theme.Body1_m
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.main.R

@Composable
fun DeleteCommentDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    CommonDialogComponent(
        height = dimensionResource(R.dimen.other_comment_delete_dialog_height),
        paddingTop = KitchingDimens.Margin.xxLarge,
        paddingBottom = KitchingDimens.Margin.xxLarge,
        radius = KitchingDimens.Corner.large,
        confirmText = stringResource(R.string.other_comment_delete_dialog_confirm_text),
        onClickConfirm = { onConfirm() },
        cancelText = stringResource(R.string.other_comment_delete_dialog_cancel_text),
        onClickCancel = { onDismiss() },
    ) {
        Text(
            modifier = Modifier.padding(top = KitchingDimens.Margin.xLarge),
            text = stringResource(R.string.other_comment_delete_dialog_title),
            style = Body1_m.copy(color = NeutralGray800),
            textAlign = TextAlign.Center,
        )
    }
}