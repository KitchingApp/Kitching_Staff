package com.kitching.main.schedule.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.kitching.main.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.kitching.core.designsystem.theme.defaultHorizontalPadding
import com.kitching.domain.entities.Schedule

@Composable
fun ScheduleListScreen(schedules: List<Schedule>, text: String) {
    if (schedules.isEmpty()) {
        EmptyScheduleScreen(text)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .defaultHorizontalPadding(),
            contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.ten_dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.ten_dp))
        ) {
            items(schedules) { schedule ->
                ScheduleCardItem(
                    userName = schedule.userName,
                    scheduleTimeName = schedule.scheduleTimeName
                )
            }
        }
    }
}