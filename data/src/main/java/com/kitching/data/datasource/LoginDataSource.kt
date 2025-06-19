package com.kitching.data.datasource

import com.kitching.data.dto.UserDTO

interface LoginDataSource {
    suspend fun checkAndSaveUser(userId: String, userName: String, userImage: String): Boolean

    suspend fun getUserById(userId: String): UserDTO
}