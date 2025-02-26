package com.kitching.data.dto

data class TeamDTO(
    val id: String = "",
    val ownerId: String = "",
    val teamName: String = "",
    val inviteCode: String = "",
    val teamAmount: Int = -1,
)
