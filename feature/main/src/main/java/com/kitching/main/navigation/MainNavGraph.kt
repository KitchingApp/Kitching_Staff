package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.main.EntryPointScreen
import com.kitching.main.fcm.getFcmToken

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    getFcmToken()
    navigation(
        route = ScreenRouteDef.MainGraph.routeName,
        startDestination = ScreenRouteDef.Entry.routeName,
    ) {
        composable(ScreenRouteDef.Entry.routeName) {
            EntryPointScreen(navController)
        }
    }
}