package com.kitching.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.kitching.core.common.navigation.ScreenRouteDef
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.navigation.parcelableType
import com.kitching.domain.entities.Notice
import com.kitching.main.view.other.NotificationScreen
import com.kitching.main.view.other.InviteCodeScreen
import com.kitching.main.view.other.MemberListScreen
import com.kitching.main.view.other.NoticeDetailScreen
import com.kitching.main.view.other.NoticeScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.otherNavGraph(commonState: CommonState, navController: NavHostController) {
    navigation<ScreenRouteDef.BottomTab.OtherGraph>(
        startDestination = ScreenRouteDef.Other.InviteCode
    ) {
        composable<ScreenRouteDef.Other.InviteCode> {
            InviteCodeScreen(
                context = navController.context,
                commonState = commonState,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRouteDef.Other.Notice> {
            NoticeScreen(
                commonState = commonState,
                onNoticeClick = { notice ->
                    navController.navigate(
                        ScreenRouteDef.Other.NoticeDetail(notice)
                    )
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRouteDef.Other.NoticeDetail> (
            typeMap = mapOf(typeOf<Notice>() to parcelableType<Notice>())
        ) { backStackEntry ->
            val notice = backStackEntry.toRoute<ScreenRouteDef.Other.NoticeDetail>()

            NoticeDetailScreen(
                commonState = commonState,
                notice = notice.notice,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<ScreenRouteDef.Other.MemberList> {
            MemberListScreen(commonState) {
                navController.popBackStack()
            }
        }

        composable<ScreenRouteDef.Other.Alarm> {
            NotificationScreen(
                context = navController.context,
                commonState = commonState
            )
        }
    }
}