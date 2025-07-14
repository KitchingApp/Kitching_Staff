package com.kitching.core.common.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kitching.core.R
import com.kitching.core.common.navigation.ScreenRouteDef

data class BottomNavItem(
    val tabName: String = "",
    val icon: ImageVector = Icons.Default.Home,
    val routeName: ScreenRouteDef = ScreenRouteDef.ScheduleTab.ScheduleMain,
    val graphRoute: ScreenRouteDef = ScreenRouteDef.BottomTab.ScheduleGraph
) {
    @Composable
    fun renderBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabName = "프렙",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_prep),
                routeName = ScreenRouteDef.PrepTab.Prep,
                graphRoute = ScreenRouteDef.BottomTab.PrepGraph
            ),
            BottomNavItem(
                tabName = "레시피",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                routeName = ScreenRouteDef.RecipeTab.RecipeMain,
                graphRoute = ScreenRouteDef.BottomTab.RecipeGraph
            ),
            BottomNavItem(
                tabName = "스케줄",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_schedule),
                routeName = ScreenRouteDef.ScheduleTab.ScheduleMain,
                graphRoute = ScreenRouteDef.BottomTab.ScheduleGraph
            ),
            BottomNavItem(
                tabName = "발주목록",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                routeName = ScreenRouteDef.OrderTab.OrderMain,
                graphRoute = ScreenRouteDef.BottomTab.OrderGraph
            ),
            BottomNavItem(
                tabName = "Chatting",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                routeName = ScreenRouteDef.ChattingTab.ChattingMain,
                graphRoute = ScreenRouteDef.BottomTab.ChattingGraph
            )
        )
    }
}