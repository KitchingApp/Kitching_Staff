package com.kitching.main.view.prep

import com.kitching.main.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.appresultscreen.AppResultHandler
import com.kitching.core.common.appresultscreen.EmptyScreen
import com.kitching.core.common.appresultscreen.ProgressIndicatorScreen
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.common.widget.DatePickerModal
import com.kitching.core.common.widget.DateSelector
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.domain.entities.TodoPrepData
import com.kitching.domain.util.AppResult
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.view.model.PrepViewModel
import com.kitching.main.view.prep.dialog.TodoPrepDialog
import com.kitching.main.view.prep.section.TodoPrepList
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun PrepTabScreen(
    commonState: CommonState,
    viewModel: PrepViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = commonState.appInfoState.value.teamInfo?.teamId.toString()

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val todoPrepData by viewModel.todoPrepsByDate.collectAsStateWithLifecycle()
    val createTodoPrepResult by viewModel.createTodoPrepResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = commonState.appInfoState.value.teamInfo?.teamName ?: "",
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
        onClickActionIcon = {
            showDialog = true
        }
    )

    LaunchedEffect(teamId, selectedDate) {
        viewModel.getTodoPrepsByDate(teamId, selectedDate.toString())
    }

    KitchingStaffTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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
                state = todoPrepData,
                onRetry = {
                    viewModel.getTodoPrepsByDate(teamId, selectedDate.toString())
                },
                onSuccess = { todoPrepList ->
                    if (todoPrepList.todos.isEmpty()) {
                        EmptyScreen(stringResource(R.string.prep_empty_screen_message))
                    } else {
                        TodoPrepList(
                            todoPrepData = todoPrepList
                        ) { todoId, newDone ->
                            viewModel.updateTodoPrep(todoId, newDone)
                        }
                    }
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

            if (showDialog) {
                TodoPrepDialog(
                    onDismiss = { showDialog = false },
                    onConfirm = { categoryId, prepId ->
                        viewModel.createTodoPrep(teamId, selectedDate.toString(), categoryId, prepId)
                        showDialog = false
                    },
                    selectedDate = selectedDate.toString(),
                    prepCategories = (todoPrepData as AppResult.Success<TodoPrepData>).data.categories,
                    preps = (todoPrepData as AppResult.Success<TodoPrepData>).data.preps
                )
            }

            if (createTodoPrepResult is AppResult.Loading) {
                ProgressIndicatorScreen()
            }
        }
    }
}