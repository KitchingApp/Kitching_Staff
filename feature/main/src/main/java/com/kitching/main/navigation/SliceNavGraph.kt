package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.core.common.CommonState
import com.kitching.core.common.ScreenRouteDef
import com.kitching.main.view.schedule.ScheduleDetailScreen
import java.time.LocalDate

fun NavGraphBuilder.sliceNavGraph(
    commonState: CommonState
) {
    navigation(
        route = ScreenRouteDef.InnerContentGraph.routeName,
        startDestination = ScreenRouteDef.InnerContentGraph.routeName,
    ) {
        composable(
            route = "${ScreenRouteDef.InnerContent.ScheduleDetail.routeName}?date={date}",
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = LocalDate.now().toString()
                }
            )
        ) { backStackEntry ->
            val dateString = backStackEntry.arguments?.getString("date") ?: LocalDate.now().toString()
            val date = LocalDate.parse(dateString)

            ScheduleDetailScreen(commonState, date)
        }
    }
}