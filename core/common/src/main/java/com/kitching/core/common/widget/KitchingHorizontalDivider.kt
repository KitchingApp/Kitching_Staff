package com.kitching.core.common.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray300

@Composable
fun KitchingHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color,
    thickness: Dp,
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}

@Composable
fun DottedDivider(
    color: Color
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(KitchingDimens.Margin.small)
            .height(KitchingDimens.Border.xxxSmall),
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )

    }
}