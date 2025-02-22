package com.kitching.data.dto

import com.kitching.domain.entities.Team

data class TeamDTO(
    val id: String = "",
    val ownerId: String = "",
    val teamName: String = "",
    val inviteCode: String = "",
    val teamAmount: Int = -1,
) {
    fun toDomain(): Team {
        return Team(
            teamId = id,
            teamName = teamName,
            teamAmount = teamAmount,
            inviteCode = inviteCode
        )
    }
}
