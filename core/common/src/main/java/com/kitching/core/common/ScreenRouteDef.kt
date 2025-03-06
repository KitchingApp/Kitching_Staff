package com.kitching.core.common

sealed class ScreenRouteDef(val routeName: String) {
    data object Splash : ScreenRouteDef("splash")

    data object Login : ScreenRouteDef("login")
    data object TeamSelect : ScreenRouteDef("teamSelect")
    data object TeamJoin : ScreenRouteDef("teamJoin")

    data object LoginGraph : ScreenRouteDef("loginGraph")
    data object MainGraph : ScreenRouteDef("mainGraph")

    data object Entry : ScreenRouteDef("entry")

    data object PrepTab : ScreenRouteDef("prep")
    data object RecipeTab : ScreenRouteDef("recipe")
    data object ScheduleTab : ScreenRouteDef("schedule")
    data object OrderTab : ScreenRouteDef("order")
    data object ChattingTab : ScreenRouteDef("chatting")

    data object Profile : ScreenRouteDef("profile")
    data object InviteCode : ScreenRouteDef("inviteCode")
    data object Notice : ScreenRouteDef("notice")
    data object MemberList : ScreenRouteDef("memberList")

    data object ScheduleDetailGraph : ScreenRouteDef("schedule_detail")

    sealed interface InnerContent {
        data object RecipeDetail : ScreenRouteDef("recipe/detail")
        data object ScheduleDetail : ScreenRouteDef("schedule/detail")
        data object ProfileEdit : ScreenRouteDef("edit")
        data object NoticeDetail : ScreenRouteDef("detail")
        data object MemberDetail : ScreenRouteDef("detail")
    }
}