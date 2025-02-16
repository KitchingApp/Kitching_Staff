package com.kitching.core.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

data class BottomNavItem(
    val tabName: String = "",
    val icon: ImageVector = Icons.Default.Home,
    val routeName: String = ""
) {
    @Composable
    fun renderBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabName = "프렙",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_prep),
                routeName = "prep"
            ),
            BottomNavItem(
                tabName = "레시피",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                routeName = "recipe"
            ),
            BottomNavItem(
                tabName = "스케줄",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_schedule),
                routeName = "schedule"
            ),
            BottomNavItem(
                tabName = "발주목록",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                routeName = "order"
            ),
            BottomNavItem(
                tabName = "Chatting",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                routeName = "chatting"
            )
        )
    }
}