package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.prep.PrepTabScreen

fun NavGraphBuilder.prepNavGraph(commonState: CommonState) {
    navigation<ScreenRouteDef.BottomTab.PrepGraph>(
        startDestination = ScreenRouteDef.PrepTab.Prep
    ) {
        composable<ScreenRouteDef.PrepTab.Prep> {
            PrepTabScreen(commonState)
        }
    }
}