package com.kitching.core.common.navigation

sealed class ScreenRouteDef(val routeName: String) {
    data object Splash : ScreenRouteDef("splash")

    data object LoginGraph : ScreenRouteDef("loginGraph")
    data object Login : ScreenRouteDef("login")
    data object TeamSelect : ScreenRouteDef("teamSelect")
    data object TeamJoin : ScreenRouteDef("teamJoin")

    data object MainGraph : ScreenRouteDef("mainGraph")
    data object Entry : ScreenRouteDef("entry")

    sealed class BottomTab(routeName: String) : ScreenRouteDef(routeName) {
        data object PrepGraph : BottomTab("prepGraph")
        data object RecipeGraph : BottomTab("recipeGraph")
        data object ScheduleGraph : BottomTab("scheduleGraph")
        data object OrderGraph : BottomTab("orderGraph")
        data object ChattingGraph : BottomTab("chattingGraph")
        data object OtherGraph : BottomTab("otherGraph")
    }

    sealed class PrepTab(routeName: String) : ScreenRouteDef(routeName) {
        data object Prep : PrepTab("prep")
    }

    sealed class RecipeTab(routeName: String) : ScreenRouteDef(routeName) {
        data object RecipeMain : RecipeTab("recipe")
        data object RecipeDetail : RecipeTab("recipe/detail")
    }

    sealed class ScheduleTab(routeName: String) : ScreenRouteDef(routeName) {
        data object ScheduleMain : ScheduleTab("schedule")
        data object ScheduleDetail : ScheduleTab("schedule/detail")
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