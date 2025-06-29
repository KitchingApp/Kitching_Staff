package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.order.OrderTabScreen

fun NavGraphBuilder.orderNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation(
        route = ScreenRouteDef.BottomTab.OrderGraph.routeName,
        startDestination = ScreenRouteDef.OrderTab.OrderMain.routeName
    ) {
        composable(ScreenRouteDef.OrderTab.OrderMain.routeName) {
            OrderTabScreen(navController.context, commonState)
        }
    }
}