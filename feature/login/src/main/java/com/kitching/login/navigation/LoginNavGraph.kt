package com.kitching.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.CommonState
import com.kitching.core.common.ScreenRouteDef
import com.kitching.login.ui.screen.InviteCodeScreen
import com.kitching.login.ui.screen.LoginScreen
import com.kitching.login.ui.screen.TeamSelectScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    commonState: CommonState
) {
    navigation(
        route = ScreenRouteDef.LoginGraph.routeName,
        startDestination = ScreenRouteDef.Login.routeName,
    ) {
        composable(route = ScreenRouteDef.Login.routeName) {
            LoginScreen(navController, commonState)
        }
        composable(route = ScreenRouteDef.TeamSelect.routeName) {
            TeamSelectScreen(navController, commonState)
        }
        composable(route = ScreenRouteDef.TeamJoin.routeName) {
            InviteCodeScreen(navController, commonState)
        }
    }
}