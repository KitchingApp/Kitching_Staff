package com.kitching.main.schedule

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.AppResultHandler
import com.kitching.core.common.CommonState
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.common.datepicker.DatePickerModal
import com.kitching.core.common.datepicker.DateSelector
import com.kitching.core.common.tabui.TabPager
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.viewmodel.ScheduleViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ScheduleDetailScreen(
    commonState: CommonState,
    date: LocalDate,
    viewModel: ScheduleViewModel = viewModel(factory = viewModelFactory)
) {
    var selectedDate by remember { mutableStateOf(date) }
    var showDatePicker by remember { mutableStateOf(false) }

    val teamId = "3uM01g5GSz8lC49JA6vq"
    val scheduleByDate by viewModel.scheduleByDate.collectAsStateWithLifecycle()

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

//    LaunchedEffect(Unit) {
//        viewModel.getTeamIdFromDataStore(commonState.navController.context)
//        Log.d("TAG", "ScheduleDetailScreen: ${viewModel.teamId.value}")
//    }

    LaunchedEffect(selectedDate) {
        viewModel.fetchScheduleByDate(teamId, selectedDate.toString())
    }

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

            AppResultHandler(
                state = scheduleByDate,
                onSuccess = { schedules ->
                    val tabs = scheduleTabs(schedules)

                    TabPager(
                        tabs = tabs,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        indicatorColor = PrimaryGreen300
                    )
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