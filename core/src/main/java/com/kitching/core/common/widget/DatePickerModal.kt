package com.kitching.core.common.widget

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.core.designsystem.pretendard
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    selectedDateTime: LocalDate,
    onDismissRequest: () -> Unit,
    onClickConfirm: (selectedDateMillis: LocalDate?) -> Unit,
    onClickCancel: () -> Unit
) {

    val selectedDateInMillis = selectedDateTime.atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateInMillis
    )

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    val newSelectedDate = datePickerState.selectedDateMillis?.let { millis ->
                        Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    onClickConfirm(newSelectedDate)
                }
            ) {
                Text(
                    text = "확인",
                    color = PrimaryGreen300,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onClickCancel() }
            ) {
                Text(
                    text = "취소",
                    color = PrimaryGreen300,
                    fontWeight = FontWeight.Bold,
                    fontFamily = pretendard
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        )
    ) {

        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = NeutralGray0,
                dividerColor = Color.Transparent, // 보라색
                titleContentColor = PrimaryGreen300, // 파란색
                headlineContentColor = PrimaryGreen300, // 주황색
                weekdayContentColor = PrimaryGreen300, // 녹색
                subheadContentColor = PrimaryGreen300, // 노란색
                navigationContentColor = PrimaryGreen300, // 청록색
                selectedDayContainerColor = PrimaryGreen300, // 보라색 (짙은)
                selectedDayContentColor = NeutralGray0, // 흰색
                selectedYearContentColor = NeutralGray0, // 흰색
                selectedYearContainerColor = PrimaryGreen300, // 짙은 파란색
                todayDateBorderColor = PrimaryGreen300, // 밝은 주황색
                todayContentColor = PrimaryGreen300, // 갈색
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = NeutralGray0, // 연한 녹색
                    focusedIndicatorColor = NeutralGray800, // 밝은 녹색
                    focusedTextColor = PrimaryGreen300, // 진한 청록색
                    focusedLabelColor = PrimaryGreen300, // 핑크색
                    focusedPlaceholderColor = PrimaryGreen300,
                    unfocusedContainerColor = NeutralGray0, // 연한 노란색
                )
            )
        )
    }
}