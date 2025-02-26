package com.kitching.data.datasource

import com.kitching.data.dto.UserDTO

interface UserDataSource {
    suspend fun getUser(userId: String): UserDTO

    suspend fun checkAndSaveUser(userId: String, userName: String, userImage: String): Boolean
}