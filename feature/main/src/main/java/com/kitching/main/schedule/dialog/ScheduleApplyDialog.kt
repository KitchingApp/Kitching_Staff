package com.kitching.main.schedule.dialog

import com.kitching.main.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kitching.core.common.dialog.CommonDialogComponent
import com.kitching.core.designsystem.theme.H5
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.domain.entities.ScheduleTime
import java.time.LocalDate

@Composable
fun ScheduleApplyDialog(
    selectedDate: LocalDate,
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    scheduleTimes: List<ScheduleTime>,
    selectedScheduleTimeId: MutableState<String>
) {
    CommonDialogComponent(
        height = 237.dp,
        paddingTop = 24.dp,
        paddingBottom = 32.dp,
        radius = 20.dp,
        confirmText = stringResource(R.string.schedule_apply),
        onClickConfirm = { onClickConfirm() },
        cancelText = stringResource(R.string.schedule_apply_cancel),
        onClickCancel = { onDismissRequest() }
    ) {
        Text(
            text = selectedDate.toString(),
            style = H5,
            color = NeutralGray800
        )

        ScheduleTimeChip(scheduleTimes, selectedScheduleTimeId)
    }
}