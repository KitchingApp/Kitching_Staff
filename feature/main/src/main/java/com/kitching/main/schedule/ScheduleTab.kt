package com.kitching.main.schedule

import com.kitching.main.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.tabui.TabItem
import com.kitching.domain.entities.Schedule

@Composable
fun scheduleTabs(schedules: List<Schedule>): List<TabItem> {
    return listOf(
        TabItem(
            title = stringResource(R.string.fix_schedule),
            content = {
                ScheduleListScreen(
                    schedules = schedules.filter { it.fix },
                    text = stringResource(R.string.no_apply_schedule)
                )
            }
        ),
        TabItem(
            title = stringResource(R.string.apply_schedule),
            content = {
                ScheduleListScreen(
                    schedules = schedules.filter { !it.fix },
                    text = stringResource(R.string.no_fix_schedule)
                )
            }
        )
    )
}