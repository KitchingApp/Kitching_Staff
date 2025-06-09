package com.kitching.core.common

data class AppInfoState(
    val userInfo: UserInfo = UserInfo(),
    val teamInfo: TeamInfo = TeamInfo(),
)

data class UserInfo(
    val userId: String = "",
    val userName: String = "",
    val userImage: String = "",
)

data class TeamInfo(
    val teamId: String = "",
    val teamName: String = "",
)