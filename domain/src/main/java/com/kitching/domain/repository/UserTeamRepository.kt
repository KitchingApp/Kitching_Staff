package com.kitching.domain.repository

import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.Member

interface UserTeamRepository {
    fun getAllMembers(teamId: String): Flow<AppResult<List<Member>>>

    fun updateMember(
        userTeamId: String,
        staffLevelId: String,
        manager: Boolean
    ): Flow<AppResult<Boolean>>

}