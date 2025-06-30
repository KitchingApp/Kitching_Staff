package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.main.view.schedule.ScheduleDetailScreen
import com.kitching.main.view.schedule.ScheduleTabScreen
import java.time.LocalDate

fun NavGraphBuilder.scheduleNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation<ScreenRouteDef.BottomTab.ScheduleGraph>(
        startDestination = ScreenRouteDef.ScheduleTab.ScheduleMain
    ) {
        composable<ScreenRouteDef.ScheduleTab.ScheduleMain> {
            ScheduleTabScreen(commonState) { selectedDate ->
                navController.navigate(
                    ScreenRouteDef.ScheduleTab.ScheduleDetail(selectedDate.toString())
                )
            }
        }

        composable<ScreenRouteDef.ScheduleTab.ScheduleDetail> { backStackEntry ->
            val scheduleDate = backStackEntry.toRoute<ScreenRouteDef.ScheduleTab.ScheduleDetail>()
            val parseDate = LocalDate.parse(scheduleDate.date)

            ScheduleDetailScreen(commonState, parseDate)
        }
    }
}