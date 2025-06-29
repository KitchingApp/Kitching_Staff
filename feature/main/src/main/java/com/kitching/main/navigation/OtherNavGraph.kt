package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState

fun NavGraphBuilder.otherNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation(
        route = ScreenRouteDef.BottomTab.OtherGraph.routeName,
        startDestination = ScreenRouteDef.Other.InviteCode.routeName
    ) {
        composable(ScreenRouteDef.Other.InviteCode.routeName) {

        }

        composable(ScreenRouteDef.Other.Notice.routeName) {

        }

        composable(ScreenRouteDef.Other.NoticeDetail.routeName) {

        }

        composable(ScreenRouteDef.Other.MemberList.routeName) {

        }

        composable(ScreenRouteDef.Other.MemberDetail.routeName) {

        }
    }
}