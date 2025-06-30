package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.main.EntryPointScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    commonState: CommonState,
) {
    navigation(
        route = ScreenRouteDef.MainGraph.routeName,
        startDestination = ScreenRouteDef.Entry.routeName,
    ) {
        composable(ScreenRouteDef.Entry.routeName) {
            EntryPointScreen(navController, commonState)
        }
    }
}