package com.kitching.core.common.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.kitching.core.designsystem.theme.subTextColor
import java.time.LocalDateTime
import com.kitching.core.common.R
import com.kitching.core.designsystem.theme.KitchingDimens

@Composable
fun DateSelector(
    selectedDateTime: LocalDateTime,
    onDateChange: (LocalDateTime) -> Unit, // 날짜 변경시 호출
    onClickDateBtn: () -> Unit // 날짜 텍스트 버튼 클릭시 호출
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.date_selector_height)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
        ) {
            IconButton(onClick = {
                onDateChange(selectedDateTime.minusDays(1))
            }) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.icon_left_triangle),
                    contentDescription = "prev date button",
                    modifier = Modifier.size(KitchingDimens.Size.large),
                    colorFilter = ColorFilter.tint(subTextColor)
                )
            }
            TextButton(
                modifier = Modifier
                    .padding(horizontal = KitchingDimens.Margin.small)
                    .align(Alignment.CenterVertically),
                onClick = {
                    onClickDateBtn()
                },
            ) {
                Text(
                    text = selectedDateTime.toLocalDate().toString(),
                    color = subTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(onClick = {
                onDateChange(selectedDateTime.plusDays(1))
            }) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.icon_right_triangle),
                    contentDescription = "next date button",
                    modifier = Modifier.size(KitchingDimens.Size.large),
                    colorFilter = ColorFilter.tint(subTextColor)
                )
            }
        }
    }
}