package com.kitching.main.view.schedule.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kitching.core.common.widget.CommonDialogComponent
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.domain.entities.ScheduleTime
import com.kitching.main.R
import java.time.LocalDate

@Composable
fun ScheduleApplyDialog(
    selectedDate: LocalDate,
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    scheduleTimes: List<ScheduleTime>?,
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
            style = MaterialTheme.typography.headlineSmall.copy(color = NeutralGray800),
        )

        if (scheduleTimes.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.schedule_time_empty),
                style = Body1_m.copy(color = NeutralGray800),
            )
        } else {
            ScheduleTimeChip(scheduleTimes, selectedScheduleTimeId)
        }
    }
}