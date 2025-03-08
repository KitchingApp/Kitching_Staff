package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.core.common.CommonState
import com.kitching.core.common.ScreenRouteDef
import com.kitching.main.schedule.ScheduleDetailScreen
import java.time.LocalDateTime

fun NavGraphBuilder.sliceNavGraph(
    commonState: CommonState
) {
    navigation(
        route = ScreenRouteDef.ScheduleDetailGraph.routeName,
        startDestination = ScreenRouteDef.ScheduleTab.routeName,
    ) {
        composable(
            route = "${ScreenRouteDef.InnerContent.ScheduleDetail.routeName}?date={date}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = LocalDateTime.now().toString()
                }
            )
        ) { backStackEntry ->
            val dateTimeString = backStackEntry.arguments?.getString("date") ?: LocalDateTime.now().toString()
            val date = LocalDateTime.parse(dateTimeString)

            ScheduleDetailScreen(commonState, date)
        }
    }
}