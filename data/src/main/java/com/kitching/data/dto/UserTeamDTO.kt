package com.kitching.data.dto

data class UserTeamDTO(
    val id: String = "",
    val teamId: String = "",
    val userId: String = "",
    val staffLevelId: String = "",
    val manager: Boolean = false,
)
