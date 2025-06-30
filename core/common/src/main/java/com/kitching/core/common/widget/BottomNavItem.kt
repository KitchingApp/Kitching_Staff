package com.kitching.core.common.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kitching.core.common.R
import com.kitching.core.common.navigation.ScreenRouteDef

data class BottomNavItem(
    val tabName: String = "",
    val icon: ImageVector = Icons.Default.Home,
    val routeName: String = "",
    val graphRoute: String = ""
) {
    @Composable
    fun renderBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabName = "프렙",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_prep),
                routeName = ScreenRouteDef.PrepTab.Prep.routeName,
                graphRoute = ScreenRouteDef.BottomTab.PrepGraph.routeName
            ),
            BottomNavItem(
                tabName = "레시피",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                routeName = ScreenRouteDef.RecipeTab.RecipeMain.routeName,
                graphRoute = ScreenRouteDef.BottomTab.RecipeGraph.routeName
            ),
            BottomNavItem(
                tabName = "스케줄",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_schedule),
                routeName = ScreenRouteDef.ScheduleTab.ScheduleMain.routeName,
                graphRoute = ScreenRouteDef.BottomTab.ScheduleGraph.routeName
            ),
            BottomNavItem(
                tabName = "발주목록",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                routeName = ScreenRouteDef.OrderTab.OrderMain.routeName,
                graphRoute = ScreenRouteDef.BottomTab.OrderGraph.routeName
            ),
            BottomNavItem(
                tabName = "Chatting",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                routeName = ScreenRouteDef.ChattingTab.ChattingMain.routeName,
                graphRoute = ScreenRouteDef.BottomTab.ChattingGraph.routeName
            )
        )
    }
}