package com.kitching.core.common.tabui

import androidx.compose.runtime.Composable

data class TabItem(
    val title: String,
    val content: @Composable () -> Unit
)