package com.kitching.domain.repository

import com.kitching.domain.entities.Team
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeamsByUserId(userId: String): Flow<AppResult<List<Team>>>

    fun getTeam(teamId: String): Flow<AppResult<Team>>
}