package com.kitching.main.schedule

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

/**
 * 달력을 그릴 때 필요한 상태 보관
 */
data class CalendarState(
    val datePagerState: PagerState
) {
    // 사용자가 선택한 날짜
    var selectedDate by mutableStateOf(LocalDateTime.now().toLocalDate())
    var previousSelectedDate by mutableStateOf<LocalDate?>(null)

    // 오늘 날짜 (연산 프로퍼티)
    val currentDate: LocalDate
        get() = LocalDateTime.now().toLocalDate()

    // 현재 Pager가 표시하는 YearMonth
    val currentPageYM: YearMonth
        get() {
            // Pager의 currentPage를 기준으로,
            // (Int.MAX_VALUE / 2) = 0 (중심점)이라고 보고
            // 해당 offset을 현재 날짜의 YearMonth에 더함
            val offset = datePagerState.currentPage - (Int.MAX_VALUE / 2)
            val baseYM = YearMonth.from(LocalDateTime.now().toLocalDate())
            return baseYM.plusMonths(offset.toLong())
        }

    /**
     * [yearMonth] 해당 월의 “보여줄 날짜들”을 구함
     *  - 시작: 월요일
     *  - 끝: 일요일
     */
    fun getDaysOfMonth(yearMonth: YearMonth): List<LocalDate> {
        // yearMonth의 첫 날
        val startOfMonth = yearMonth.atDay(1)
            // 일요일까지 거슬러 올라감
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

        // yearMonth의 마지막 날
        val endOfMonth = yearMonth.atEndOfMonth()
            // 해당 주의 토요일까지 확장
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))

        // startOfMonth부터 endOfMonth까지 하루씩 증가
        return generateSequence(startOfMonth) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endOfMonth) }
            .toList()
    }

    // 날짜 선택 처리를 위한 함수 추가
    fun onDateSelected(date: LocalDate): Boolean {
        // 현재 선택된 날짜와 같은지 확인
        val isDoubleSelect = (date == selectedDate)
        previousSelectedDate = selectedDate
        selectedDate = date
        return isDoubleSelect
    }

    // CalendarState를 저장/복원하기 위한 Saver
    companion object {
        fun Saver(datePagerState: PagerState): Saver<CalendarState, Any> = listSaver(
            save = { listOf(it.selectedDate) },
            restore = { saved ->
                CalendarState(datePagerState).apply {
                    selectedDate = saved[0] as LocalDate
                }
            }
        )
    }
}

/**
 * CalendarState를 remember + saveable(화면 회전 등 구성 변경에도 유지)하여 생성
 */
@Composable
fun rememberCalendarState(
    datePagerState: PagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
): CalendarState {
    return rememberSaveable(datePagerState, saver = CalendarState.Saver(datePagerState)) {
        CalendarState(datePagerState)
    }
}