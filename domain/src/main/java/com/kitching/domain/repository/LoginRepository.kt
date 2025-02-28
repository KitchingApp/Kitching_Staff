package com.kitching.domain.repository

import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun checkAndSaveUser(uid: String, userName: String, userImage: String): Flow<AppResult<Boolean>>
}