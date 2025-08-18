package com.kitching.core.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder

/**
 * 바텀 탭 스마트 네비게이션 확장함수
 * 현재 위치가 클릭한 탭의 메인 화면이 아닐 때만 이동합니다.
 */
fun NavController.navigateToBottomTab(
    route: ScreenRouteDef,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    val currentRoute = this.currentDestination?.route
    val targetRoute = route::class.qualifiedName

    if (currentRoute != targetRoute) {
        this.navigate(route) {
            popUpTo(this@navigateToBottomTab.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
            builder?.invoke(this)
        }
    }
}