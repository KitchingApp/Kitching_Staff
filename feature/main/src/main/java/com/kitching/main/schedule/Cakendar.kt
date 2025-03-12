package com.kitching.main.schedule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.core.common.AppResultHandler
import com.kitching.main.factory.viewModelFactory
import com.kitching.main.viewmodel.ScheduleViewModel
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onDoubleSelect: (LocalDate) -> Unit,
    viewModel: ScheduleViewModel = viewModel(factory = viewModelFactory)
) {
    val schedules by viewModel.mySchedules.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchSchedules("3863591667", "3uM01g5GSz8lC49JA6vq")
        Log.e("TAG", "Calendar: $schedules")
    }

    // Pager 페이지가 변경되면 -> "현재 선택일자"와 "새로운 YearMonth"의 날짜를 조정
    LaunchedEffect(state.datePagerState.currentPage) {
        val newYM = state.currentPageYM
        val todayYM = YearMonth.from(state.currentDate)

        state.selectedDate = if (newYM == todayYM) {
            state.currentDate // 현재 달이면 오늘 날짜
        } else {
            newYM.atDay(1) // 다른 달이면 해당 달의 1일
        }
    }

    // 만약 사용자가 날짜를 클릭해 selectedDate가 바뀌면,
    // 해당 날짜가 현재 Pager 범위 밖이면 Pager도 이동
    LaunchedEffect(state.selectedDate) {
        val currentYM = state.currentPageYM
        val selectedYM = YearMonth.from(state.selectedDate)
        if (currentYM != selectedYM) {
            val offset = if (selectedYM.isAfter(currentYM)) 1 else -1
            state.datePagerState.animateScrollToPage(
                state.datePagerState.currentPage + offset
            )
        }
    }

    AppResultHandler(state = schedules,
        onSuccess = { scheduleList ->
            Column(
                modifier = modifier.background(Color.White)
            ) {
                // 상단의 연/월 + 버튼들
                TopBarSection(state = state)
                // 월~일 헤더
                DayOfWeekLabels()

                // 날짜 Pager
                HorizontalPager(
                    state = state.datePagerState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) { page ->
                    // 이 페이지가 가리키는 YearMonth
                    val pageYearMonth = remember {
                        val baseYM = YearMonth.from(LocalDate.now())
                        val offset = page - (Int.MAX_VALUE / 2)
                        baseYM.plusMonths(offset.toLong())
                    }
                    val daysOfMonth = remember(pageYearMonth) {
                        state.getDaysOfMonth(pageYearMonth)
                    }

                    Column(Modifier.fillMaxSize()) {
                        // 1주(7일) 단위로 나누어 그리기
                        daysOfMonth.chunked(7).forEach { weekDates ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                weekDates.forEach { date ->
                                    CalendarDay(
                                        date = date,
                                        isToday = (date == state.currentDate),
                                        isSelected = (date == state.selectedDate),
                                        isVisibleMonth = (YearMonth.from(date) == pageYearMonth),
                                        schedules = scheduleList,
                                        onClick = {
                                            if (state.onDateSelected(date)) {
                                                onDoubleSelect(date)
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxHeight()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TopBarSection(state: CalendarState) {
    // 현재 페이지의 YearMonth
    val currentYM = state.currentPageYM

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // 가운데 연/월
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 8.dp)
                .clickable {},
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val ymText = "${currentYM.year}. ${currentYM.monthValue}"

            BasicText(
                text = ymText,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
private fun DayOfWeekLabels() {
    val labels = listOf("일", "월", "화", "수", "목", "금", "토")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        labels.forEach { dayText ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                val textColor = when (dayText) {
                    "토" -> Color.Blue
                    "일" -> Color.Red
                    else -> Color.Black
                }
                BasicText(
                    text = dayText,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                )
            }
        }
    }
}