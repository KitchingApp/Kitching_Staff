package com.kitching.data.datasource

interface LoginDataSource {
    suspend fun checkAndSaveUser(userId: String, userName: String, userImage: String): Boolean
}