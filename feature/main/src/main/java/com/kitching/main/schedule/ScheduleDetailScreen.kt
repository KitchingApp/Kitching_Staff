package com.kitching.main.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.CommonState
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.common.datepicker.DatePickerModal
import com.kitching.core.common.datepicker.DateSelector
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ScheduleDetailScreen(
    commonState: CommonState,
    date: LocalDate
) {
    var selectedDate by remember { mutableStateOf(date) }
    var showDatePicker by remember { mutableStateOf(false) }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {}
    )

    KitchingStaffTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DateSelector(
                selectedDateTime = selectedDate.atTime(LocalTime.now()),
                onDateChange = { newDateTime ->
                    selectedDate = newDateTime.toLocalDate()
                },
                onClickDateBtn = {
                    showDatePicker = true
                }
            )

            if (showDatePicker) {
                DatePickerModal(
                    selectedDateTime = selectedDate,
                    onDismissRequest = { showDatePicker = false },
                    onClickConfirm = { newSelectedDate ->
                        if (newSelectedDate != null) {
                            selectedDate = newSelectedDate
                        }
                        showDatePicker = false
                    },
                    onClickCancel = { showDatePicker = false }
                )
            }
        }
    }
}