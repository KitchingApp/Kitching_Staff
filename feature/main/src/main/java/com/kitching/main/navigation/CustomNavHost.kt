package com.kitching.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.core.common.CommonState
import com.kitching.core.common.ScreenRouteDef
import com.kitching.main.ChatTabScreen
import com.kitching.main.OrderTabScreen
import com.kitching.main.PrepTabScreen
import com.kitching.main.RecipeTabScreen
import com.kitching.main.schedule.ScheduleTabScreen

@Composable
fun CustomNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    commonState: CommonState
) {
    NavHost(
        navController = commonState.navController,
        startDestination = ScreenRouteDef.ScheduleTab.routeName,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(ScreenRouteDef.ScheduleTab.routeName) {
            ScheduleTabScreen(commonState)
        }
        composable(ScreenRouteDef.PrepTab.routeName) {
            PrepTabScreen(commonState)
        }
        composable(ScreenRouteDef.RecipeTab.routeName) {
            RecipeTabScreen(commonState)
        }
        composable(ScreenRouteDef.OrderTab.routeName) {
            OrderTabScreen(commonState)
        }
        composable(ScreenRouteDef.ChattingTab.routeName) {
            ChatTabScreen(commonState)
        }
    }
}