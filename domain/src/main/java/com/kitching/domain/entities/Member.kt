package com.kitching.domain.entities

data class Member(
    val userTeamId: String,
    val userId: String,
    val userName: String,
    val userImage: String,
    val staffLevelId: String,
    val staffLevelName: String,
    val manager: Boolean
)
