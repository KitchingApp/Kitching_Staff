package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.ChatTabScreen

fun NavGraphBuilder.chattingNavGraph(commonState: CommonState) {
    navigation<ScreenRouteDef.BottomTab.ChattingGraph>(
        startDestination = ScreenRouteDef.ChattingTab.ChattingMain
    ) {
        composable<ScreenRouteDef.ChattingTab.ChattingMain> {
            ChatTabScreen(commonState)
        }
    }
}