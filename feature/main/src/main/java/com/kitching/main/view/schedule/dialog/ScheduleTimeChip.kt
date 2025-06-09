package com.kitching.main.view.schedule.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.core.designsystem.theme.H5_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.core.designsystem.theme.SecondaryLightGreen100
import com.kitching.domain.entities.ScheduleTime

@Composable
fun ScheduleTimeChip(
    scheduleTimes: List<ScheduleTime>,
    selectedScheduleTime: MutableState<String>
) {
    Row(
        modifier = Modifier.width(240.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        scheduleTimes.forEach { scheduleTime ->
            FilterChip(
                onClick = {
                    selectedScheduleTime.value = scheduleTime.scheduleTimeId
                },
                label = {
                    Text(
                        text = scheduleTime.scheduleTimeName,
                        style = H5_m
                    )
                },
                selected = selectedScheduleTime.value == scheduleTime.scheduleTimeId,
                colors = SelectableChipColors(
                    containerColor = SecondaryLightGreen100,
                    labelColor = NeutralGray800,
                    leadingIconColor = NeutralGray800,
                    trailingIconColor = NeutralGray800,
                    disabledContainerColor = SecondaryLightGreen100,
                    disabledLabelColor = NeutralGray800,
                    disabledLeadingIconColor = NeutralGray800,
                    disabledTrailingIconColor = NeutralGray800,
                    selectedContainerColor = PrimaryGreen300,
                    disabledSelectedContainerColor = PrimaryGreen300,
                    selectedLabelColor = NeutralGray0,
                    selectedLeadingIconColor = NeutralGray0,
                    selectedTrailingIconColor = NeutralGray0
                ),
                border = null,
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}