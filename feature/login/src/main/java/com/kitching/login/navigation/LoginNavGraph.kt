package com.kitching.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.login.InviteCodeScreen
import com.kitching.login.LoginMainScreen
import com.kitching.login.TeamScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    onLogin: () -> Unit
) {
    navigation(
        route = ScreenRouteDef.LoginGraph.routeName,
        startDestination = ScreenRouteDef.LoginMain.routeName,
    ) {
        composable(route = ScreenRouteDef.LoginMain.routeName) {
            LoginMainScreen(onLogin = {
                navController.navigate(ScreenRouteDef.TeamSelect.routeName) {
                    popUpTo(ScreenRouteDef.LoginMain.routeName) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = ScreenRouteDef.TeamSelect.routeName) {
            TeamScreen(onSelect = {
                onLogin()
            }, onJoin = {
                navController.navigate(ScreenRouteDef.TeamJoin.routeName)
            })
        }
        composable(route = ScreenRouteDef.TeamJoin.routeName) {
            InviteCodeScreen(onJoinTeam = {
                navController.navigate(ScreenRouteDef.TeamSelect.routeName) {
                    popUpTo(ScreenRouteDef.TeamJoin.routeName) {
                        inclusive = true
                    }
                }
            })
        }
    }
}