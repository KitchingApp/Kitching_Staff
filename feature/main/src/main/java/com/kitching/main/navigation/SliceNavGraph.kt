package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kitching.core.common.CommonState
import com.kitching.core.common.ScreenRouteDef
import com.kitching.main.schedule.ScheduleDetailScreen

fun NavGraphBuilder.sliceNavGraph(
    commonState: CommonState
) {
    navigation(
        route = ScreenRouteDef.ScheduleDetailGraph.routeName,
        startDestination = ScreenRouteDef.ScheduleTab.routeName,
    ) {
        composable(ScreenRouteDef.InnerContent.ScheduleDetail.routeName) {
            ScheduleDetailScreen(commonState)
        }
    }
}