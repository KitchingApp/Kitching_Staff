package com.kitching.domain.repository

import com.kitching.domain.entities.User
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun checkAndSaveUser(uid: String, userName: String, userImage: String): Flow<AppResult<Boolean>>

    suspend fun getUserById(userId: String): Flow<AppResult<User>>
}