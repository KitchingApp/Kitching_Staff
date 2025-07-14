package com.kitching.core.common.widget

import androidx.compose.foundation.Canvas
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp

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
    modifier: Modifier,
    color: Color
) {
    Canvas(
        modifier = modifier
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )

    }
}

@Composable
fun KitchingVerticalDivider(
    modifier: Modifier = Modifier,
    color: Color,
    thickness: Dp,
) {
    VerticalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}