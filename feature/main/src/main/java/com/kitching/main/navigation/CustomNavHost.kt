package com.kitching.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.navigation.ScreenRouteDef

@Composable
fun CustomNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    commonState: CommonState
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRouteDef.BottomTab.ScheduleGraph.routeName,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        prepNavGraph(commonState)

        recipeNavGraph(commonState, navController)

        scheduleNavGraph(commonState, navController)

        orderNavGraph(commonState, navController)

        chattingNavGraph(commonState)

        otherNavGraph(commonState, navController)
    }
}