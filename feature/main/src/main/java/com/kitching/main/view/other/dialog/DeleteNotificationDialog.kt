package com.kitching.main.view.other.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.common.widget.CommonDialogComponent
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.main.R

@Composable
fun DeleteNotificationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    CommonDialogComponent(
        height = dimensionResource(R.dimen.notification_delete_dialog_height),
        paddingTop = KitchingDimens.Margin.xxLarge,
        paddingBottom = KitchingDimens.Margin.xxLarge,
        radius = KitchingDimens.Corner.large,
        confirmText = stringResource(R.string.notification_delete_dialog_confirm_text),
        onClickConfirm = { onConfirm() },
        cancelText = stringResource(R.string.notification_delete_dialog_cancel_text),
        onClickCancel = { onDismiss() },
    ) {
        Text(
            modifier = Modifier.padding(top = KitchingDimens.Margin.xLarge),
            text = stringResource(R.string.notification_delete_dialog_title),
            style = MaterialTheme.typography.bodyMedium.copy(color = NeutralGray800),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun DeleteAllNotificationDialog(
    currentTab: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    val tabName = when (currentTab) {
        0 -> stringResource(R.string.notification_schedule_tab)
        else -> stringResource(R.string.notification_notice_tab)
    }
    CommonDialogComponent(
        height = dimensionResource(R.dimen.notification_delete_dialog_height),
        paddingTop = KitchingDimens.Margin.xxLarge,
        paddingBottom = KitchingDimens.Margin.xxLarge,
        radius = KitchingDimens.Corner.large,
        confirmText = stringResource(R.string.notification_delete_dialog_confirm_text),
        onClickConfirm = { onConfirm(currentTab) },
        cancelText = stringResource(R.string.notification_delete_dialog_cancel_text),
        onClickCancel = { onDismiss() },
    ) {
        Text(
            modifier = Modifier.padding(top = KitchingDimens.Margin.xLarge),
            text = stringResource(R.string.notification_all_delete_dialog_title, tabName),
            style = MaterialTheme.typography.bodyMedium.copy(color = NeutralGray800),
            textAlign = TextAlign.Center,
        )
    }
}