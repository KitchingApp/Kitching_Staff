package com.kitching.core.common.widget

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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