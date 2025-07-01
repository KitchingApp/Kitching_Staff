package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.order.OrderTabScreen

fun NavGraphBuilder.orderNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation<ScreenRouteDef.BottomTab.OrderGraph>(
        startDestination = ScreenRouteDef.OrderTab.OrderMain
    ) {
        composable<ScreenRouteDef.OrderTab.OrderMain> {
            OrderTabScreen(navController.context, commonState)
        }
    }
}