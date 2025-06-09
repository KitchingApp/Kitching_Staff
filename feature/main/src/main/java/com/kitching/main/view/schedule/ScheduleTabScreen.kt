package com.kitching.main.view.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.CommonState
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.main.view.schedule.calendar.Calendar
import com.kitching.main.view.schedule.calendar.rememberCalendarState
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun ScheduleTabScreen(
    commonState: CommonState,
    onNavigateToDetail: (LocalDate) -> Unit
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = PrimaryGreen300,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL,
    )

    val calendarState = rememberCalendarState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Calendar(
            modifier = Modifier
                .fillMaxWidth(),
            state = calendarState,
            onDoubleSelect = { selectedDate ->
                onNavigateToDetail(selectedDate)
            }
        )
    }
}