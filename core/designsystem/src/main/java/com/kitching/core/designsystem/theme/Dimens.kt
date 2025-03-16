package com.kitching.core.designsystem.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val defaultPadding = 20.dp

fun Modifier.defaultHorizontalPadding(): Modifier {
    return this.padding(horizontal = defaultPadding)
}