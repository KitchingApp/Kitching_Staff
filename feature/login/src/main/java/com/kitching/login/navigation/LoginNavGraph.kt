package com.kitching.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.login.ui.screen.InviteCodeScreen
import com.kitching.login.ui.screen.LoginScreen
import com.kitching.login.ui.screen.TeamSelectScreen

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    commonState: CommonState
) {
    navigation<ScreenRouteDef.LoginGraph>(
        startDestination = ScreenRouteDef.Login.LoginMain,
    ) {
        composable<ScreenRouteDef.Login.LoginMain> {
            LoginScreen(navController.context, commonState,
                onLoginSuccess = {
                    navController.navigate(ScreenRouteDef.Login.TeamSelect) {
                        popUpTo(ScreenRouteDef.Login.LoginMain) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<ScreenRouteDef.Login.TeamSelect> {
            TeamSelectScreen(
                commonState,
                goMain = {
                    navController.navigate(ScreenRouteDef.MainGraph) {
                        popUpTo(ScreenRouteDef.LoginGraph) {
                            inclusive = true
                        }
                    }
                },
                goInviteCode = {
                    navController.navigate(ScreenRouteDef.Login.TeamJoin)
                }
            )
        }
        composable<ScreenRouteDef.Login.TeamJoin> {
            InviteCodeScreen(commonState,
                goTeamSelect = {
                    navController.navigate(ScreenRouteDef.Login.TeamSelect) {
                        popUpTo(ScreenRouteDef.Login.TeamJoin) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}