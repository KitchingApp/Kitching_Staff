package com.kitching.core.common.navigation

import com.kitching.domain.entities.Recipe
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRouteDef {
    @Serializable
    data object Splash : ScreenRouteDef()

    @Serializable
    data object LoginGraph : ScreenRouteDef()

    @Serializable
    data object MainGraph : ScreenRouteDef()

    @Serializable
    data object Entry : ScreenRouteDef()

    @Serializable
    sealed class Login : ScreenRouteDef() {
        @Serializable
        data object LoginMain : ScreenRouteDef()
        @Serializable
        data object TeamSelect : ScreenRouteDef()
        @Serializable
        data object TeamJoin : ScreenRouteDef()
    }

    @Serializable
    sealed class BottomTab : ScreenRouteDef() {
        @Serializable
        data object PrepGraph : BottomTab()
        @Serializable
        data object RecipeGraph : BottomTab()
        @Serializable
        data object ScheduleGraph : BottomTab()
        @Serializable
        data object OrderGraph : BottomTab()
        @Serializable
        data object ChattingGraph : BottomTab()
        @Serializable
        data object OtherGraph : BottomTab()
    }

    @Serializable
    sealed class PrepTab : ScreenRouteDef() {
        @Serializable
        data object Prep : PrepTab()
    }

    @Serializable
    sealed class RecipeTab : ScreenRouteDef() {
        @Serializable
        data object RecipeMain : RecipeTab()

        @Serializable
        data class RecipeDetail(val recipe: Recipe) : RecipeTab()
    }

    @Serializable
    sealed class ScheduleTab : ScreenRouteDef() {
        @Serializable
        data object ScheduleMain : ScheduleTab()

        @Serializable
        data class ScheduleDetail(val date: String) : ScheduleTab()
    }

    @Serializable
    sealed class OrderTab : ScreenRouteDef() {
        @Serializable
        data object OrderMain : OrderTab()
    }

    @Serializable
    sealed class ChattingTab : ScreenRouteDef() {
        @Serializable
        data object ChattingMain : ChattingTab()
    }

    @Serializable
    sealed class Other : ScreenRouteDef() {
        @Serializable
        data object InviteCode : Other()
        @Serializable
        data object Notice : Other()
        @Serializable
        data class NoticeDetail(val notice: com.kitching.domain.entities.Notice) : Other()
        @Serializable
        data object MemberList : Other()
    }
}