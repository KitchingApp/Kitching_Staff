package com.kitching.core.common.navigation

import com.kitching.domain.entities.Recipe
import kotlinx.serialization.Serializable

sealed class ScreenRouteDef(val routeName: String) {
    data object Splash : ScreenRouteDef("splash")

    data object LoginGraph : ScreenRouteDef("loginGraph")
    data object Login : ScreenRouteDef("login")
    data object TeamSelect : ScreenRouteDef("teamSelect")
    data object TeamJoin : ScreenRouteDef("teamJoin")

    data object MainGraph : ScreenRouteDef("mainGraph")
    data object Entry : ScreenRouteDef("entry")

    @Serializable
    sealed class BottomTab(routeName: String) : ScreenRouteDef(routeName) {
        @Serializable
        data object PrepGraph : BottomTab("prepGraph")
        @Serializable
        data object RecipeGraph : BottomTab("recipeGraph")
        @Serializable
        data object ScheduleGraph : BottomTab("scheduleGraph")
        @Serializable
        data object OrderGraph : BottomTab("orderGraph")
        @Serializable
        data object ChattingGraph : BottomTab("chattingGraph")
        @Serializable
        data object OtherGraph : BottomTab("otherGraph")
    }

    sealed class PrepTab(routeName: String) : ScreenRouteDef(routeName) {
        data object Prep : PrepTab("prep")
    }

    @Serializable
    sealed class RecipeTab(routeName: String) : ScreenRouteDef(routeName) {
        @Serializable
        data object RecipeMain : RecipeTab("recipe")

        @Serializable
        data class RecipeDetail(val recipe: Recipe) : RecipeTab("recipe/detail")
    }

    @Serializable
    sealed class ScheduleTab(routeName: String) : ScreenRouteDef(routeName) {
        @Serializable
        data object ScheduleMain : ScheduleTab("schedule")

        @Serializable
        data class ScheduleDetail(val date: String) : ScheduleTab("schedule/detail")
    }

    sealed class OrderTab(routeName: String) : ScreenRouteDef(routeName) {
        data object OrderMain : OrderTab("order")
    }

    sealed class ChattingTab(routeName: String) : ScreenRouteDef(routeName) {
        data object ChattingMain : ChattingTab("chatting")
    }

    sealed class Other(routeName: String) : ScreenRouteDef(routeName) {
        data object InviteCode : Other("inviteCode")
        data object Notice : Other("notice")
        data object NoticeDetail : Other("notice/detail")
        data object MemberList : Other("member")
        data object MemberDetail : Other("member/detail")
    }
}