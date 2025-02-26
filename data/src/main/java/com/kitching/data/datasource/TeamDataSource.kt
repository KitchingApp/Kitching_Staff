package com.kitching.data.datasource

import com.kitching.data.dto.TeamDTO

interface TeamDataSource {
    suspend fun getTeam(teamId: String): TeamDTO?

    /** return: teamId */
    suspend fun createTeam(ownerId: String, inviteCode: String, teamName: String, teamAmount: Int): String

    suspend fun getTeamList(teamId: String): List<TeamDTO>
}