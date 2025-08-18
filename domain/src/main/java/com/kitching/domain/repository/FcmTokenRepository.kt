package com.kitching.domain.repository

import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface FcmTokenRepository {
    suspend fun updateToken(userId: String, token: String, deviceModel: String): Flow<AppResult<Boolean>>
}