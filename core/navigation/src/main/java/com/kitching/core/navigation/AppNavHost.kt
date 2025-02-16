package com.kitching.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.splash.SplashScreen
import com.kitching.login.navigation.loginNavGraph
import com.kitching.main.navigation.mainNavGraph

@Composable
fun AppNavHost(
    startDestination: String = ScreenRouteDef.Splash.routeName,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(ScreenRouteDef.Splash.routeName) {
            SplashScreen(
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
                }
            )
        }

        loginNavGraph(navController, onLogin = {
            navController.navigate(ScreenRouteDef.Splash.routeName) {
                popUpTo(ScreenRouteDef.LoginGraph.routeName) {
                    inclusive = true
                }
            }
        })

        mainNavGraph(navController)
    }
}