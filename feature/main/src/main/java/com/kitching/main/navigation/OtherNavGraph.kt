package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.other.InviteCodeScreen

fun NavGraphBuilder.otherNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation<ScreenRouteDef.BottomTab.OtherGraph>(
        startDestination = ScreenRouteDef.Other.InviteCode
    ) {
        composable<ScreenRouteDef.Other.InviteCode> {
            InviteCodeScreen(
                context = navController.context,
                commonState = commonState,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRouteDef.Other.Notice> {

        }

        composable<ScreenRouteDef.Other.NoticeDetail> {

        }

        composable<ScreenRouteDef.Other.MemberList> {

        }

        composable<ScreenRouteDef.Other.MemberDetail> {

        }
    }
}