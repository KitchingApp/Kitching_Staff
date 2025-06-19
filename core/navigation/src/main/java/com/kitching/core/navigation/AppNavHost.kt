package com.kitching.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.rememberCommonState
import com.kitching.login.ui.screen.splash.SplashScreen
import com.kitching.login.navigation.loginNavGraph
import com.kitching.main.navigation.mainNavGraph

@Composable
fun AppNavHost(
    startDestination: String = ScreenRouteDef.Splash.routeName,
) {
    val navController = rememberNavController()
    val commonState = rememberCommonState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = commonState.snackBarState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
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
}