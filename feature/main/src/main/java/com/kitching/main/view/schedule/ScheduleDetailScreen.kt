package com.kitching.main.view.schedule

import com.kitching.main.R
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
import androidx.compose.ui.res.stringResource
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
import com.kitching.domain.entities.ScheduleTime
import com.kitching.domain.util.AppResult
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.schedule.dialog.ScheduleApplyDialog
import com.kitching.main.view.schedule.tab.scheduleTabs
import com.kitching.main.view.model.ScheduleViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun ScheduleDetailScreen(
    commonState: CommonState,
    date: LocalDate,
    viewModel: ScheduleViewModel = viewModel(factory = viewModelFactory)
) {
    val userId = commonState.appInfoState.value.userInfo?.userId.toString()
    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()

    var selectedDate by remember { mutableStateOf(date) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showApplyDialog by remember { mutableStateOf(false) }

    var selectedScheduleTimeId = remember { mutableStateOf("") }

    val scheduleTimesState by viewModel.scheduleTimes.collectAsStateWithLifecycle()
    val applyScheduleResult by viewModel.scheduleResult.collectAsStateWithLifecycle()

    val scheduleByDate by viewModel.scheduleByDate.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = commonState.appInfoState.value.teamInfo?.teamName ?: "",
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            showApplyDialog = true
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getScheduleTimes(teamId)
    }

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

            if (showApplyDialog) {
                ScheduleApplyDialog(
                    selectedDate = selectedDate,
                    onDismissRequest = { showApplyDialog = false },
                    onClickConfirm = {
                        viewModel.createSchedule(
                            teamId = teamId,
                            dateString = selectedDate.toString(),
                            userId = userId,
                            scheduleTimeId = selectedScheduleTimeId.value
                        )
                    },
                    scheduleTimes = (scheduleTimesState as AppResult.Success<List<ScheduleTime>>).data,
                    selectedScheduleTimeId = selectedScheduleTimeId
                )
            }

            AppResultHandler(
                state = applyScheduleResult,
                onSuccess = {
                    showApplyDialog = false
//                    Toast.makeText(commonState.navController.context, stringResource(R.string.schedule_apply_success), Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    showApplyDialog = false
//                    Toast.makeText(commonState.navController.context, stringResource(R.string.schedule_apply_fail), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}