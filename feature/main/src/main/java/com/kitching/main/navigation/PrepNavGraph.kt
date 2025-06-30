package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.PrepTabScreen

fun NavGraphBuilder.prepNavGraph(commonState: CommonState) {
    composable(ScreenRouteDef.PrepTab.Prep.routeName) {
        PrepTabScreen(commonState)
    }
}