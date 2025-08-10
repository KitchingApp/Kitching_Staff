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
    targetRoute: ScreenRouteDef? = null
) {
    navigation<ScreenRouteDef.MainGraph>(
        startDestination = ScreenRouteDef.Entry,
    ) {
        composable<ScreenRouteDef.Entry> {
            EntryPointScreen(navController, commonState, targetRoute)
        }
    }
}