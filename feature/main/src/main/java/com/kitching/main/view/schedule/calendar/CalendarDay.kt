package com.kitching.main.view.schedule.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.core.designsystem.theme.Body1
import com.kitching.domain.entities.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CalendarDay(
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    isVisibleMonth: Boolean,
    schedules: List<Schedule>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 요일 체크
    val dayOfWeek = date.dayOfWeek
    val isSaturday = (dayOfWeek == DayOfWeek.SATURDAY)
    val isSunday = (dayOfWeek == DayOfWeek.SUNDAY)

    // 배경색: 오늘이면 진한색(검정), 아니면 투명
    val backgroundColor = Color.Transparent

    // 테두리: 선택된 날짜면 빨강 테두리
    val borderStroke = if (isSelected) BorderStroke(1.dp, Color.Red) else null

    // 텍스트 색상
    val textColor = remember(isToday, isVisibleMonth) {
        when {
            !isVisibleMonth && isSunday -> Color.Red.copy(alpha = 0.3f)
            !isVisibleMonth && isSaturday -> Color.Blue.copy(alpha = 0.3f)
            !isVisibleMonth -> Color.Black.copy(alpha = 0.3f)
            isSunday -> Color.Red
            isSaturday -> Color.Blue
            else -> Color.Black
        }
    }

    // Surface로 네모박스(사각형) 형태를 만듦
    Surface(
        onClick = onClick,
        shape = RectangleShape,
        color = backgroundColor,
        border = borderStroke,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
            .padding(2.dp)
            .fillMaxSize(),
        contentColor = Color.Unspecified // 내부 텍스트 color 직접 지정
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.size(36.dp)
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
                modifier = Modifier.padding(4.dp)
            )

            // 해당 날짜의 스케줄 찾기
            val schedule = schedules.find { it.date == date.toString() }

            if (schedule != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(android.graphics.Color.parseColor(schedule.color))),
                    text = schedule.scheduleTimeName,
                    style = Body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Ripple 효과를 끄기 위한 구현
 */
class NoRippleInteractionSource : MutableInteractionSource {
    private val noInteractions = mutableStateListOf<Interaction>()

    override val interactions: Flow<Interaction>
        get() = noInteractions.asFlow()

    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}