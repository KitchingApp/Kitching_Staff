package com.kitching.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.rememberCommonState
import com.kitching.login.ui.screen.splash.SplashScreen
import com.kitching.login.navigation.loginNavGraph
import com.kitching.main.navigation.mainNavGraph

@Composable
fun AppNavHost(
    startDestination: String = ScreenRouteDef.Splash.routeName,
) {
    val navController = rememberNavController()
    val commonState = rememberCommonState()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(ScreenRouteDef.Splash.routeName) {
            SplashScreen(
                context = navController.context,
                commonState = commonState,
                goLogin = {
                    navController.navigate(ScreenRouteDef.LoginGraph.routeName) {
                        popUpTo(ScreenRouteDef.Splash.routeName) {
                            inclusive = true
                        }
                    }
                },
                goMain = {
                    navController.navigate(ScreenRouteDef.MainGraph.routeName) {
                        popUpTo(ScreenRouteDef.Splash.routeName) {
                            inclusive = true
                        }
                    }
                },
                goTeamSelect = {
                    navController.navigate(ScreenRouteDef.TeamSelect.routeName) {
                        popUpTo(ScreenRouteDef.Splash.routeName) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        loginNavGraph(navController, commonState)
        mainNavGraph(navController, commonState)
    }
}