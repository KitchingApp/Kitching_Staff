package com.kitching.core.common.widget

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kitching.core.designsystem.theme.NeutralGray200
import com.kitching.core.designsystem.theme.NeutralGray400
import com.kitching.core.designsystem.theme.PrimaryGreen300

@Composable
fun CustomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier.drawBehind {
            drawLine(
                color = NeutralGray400,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
            )
        },
        containerColor = Color.White,
        contentColor = NeutralGray200,
    ) {
        BottomNavItem().renderBottomNavItems()
            .forEachIndexed { _, bottomNavItem ->
                NavigationBarItem(
                    selected = currentDestination?.route?.startsWith(bottomNavItem.routeName) == true,
                    label = {
                        Text(
                            text = bottomNavItem.tabName,
                        )
                    },
                    icon = {
                        Icon(
                            bottomNavItem.icon,
                            contentDescription = bottomNavItem.tabName,
                        )
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.routeName) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = PrimaryGreen300,
                        selectedTextColor = PrimaryGreen300,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = NeutralGray200,
                        unselectedTextColor = NeutralGray400,
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                    )
                )
            }
    }
}