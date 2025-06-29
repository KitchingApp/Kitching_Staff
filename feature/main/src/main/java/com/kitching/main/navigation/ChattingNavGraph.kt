package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.ChatTabScreen

fun NavGraphBuilder.chattingNavGraph(commonState: CommonState) {
    navigation(
        route = ScreenRouteDef.BottomTab.ChattingGraph.routeName,
        startDestination = ScreenRouteDef.ChattingTab.ChattingMain.routeName
    ) {
        composable(ScreenRouteDef.ChattingTab.ChattingMain.routeName) {
            ChatTabScreen(commonState)
        }
    }
}