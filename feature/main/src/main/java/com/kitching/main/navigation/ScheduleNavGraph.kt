package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kitching.core.common.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.schedule.ScheduleDetailScreen
import com.kitching.main.view.schedule.ScheduleTabScreen
import java.time.LocalDate

fun NavGraphBuilder.scheduleNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation(
        route = ScreenRouteDef.BottomTab.ScheduleGraph.routeName,
        startDestination = ScreenRouteDef.ScheduleTab.ScheduleMain.routeName
    ) {
        composable(ScreenRouteDef.ScheduleTab.ScheduleMain.routeName) {
            ScheduleTabScreen(
                commonState = commonState,
                onNavigateToDetail = { selectedDate ->
                    navController.navigate("${ScreenRouteDef.ScheduleTab.ScheduleDetail.routeName}?date=${selectedDate}")
                }
            )
        }

        composable(
            route = "${ScreenRouteDef.ScheduleTab.ScheduleDetail.routeName}?date={date}",
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