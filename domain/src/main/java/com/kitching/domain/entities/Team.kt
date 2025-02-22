package com.kitching.domain.entities

data class Team(
    val teamId: String = "",
    val teamName: String = "",
    val teamAmount: Int = -1,
    val inviteCode: String = ""
)
