package com.kitching.data.datasource

interface FcmTokenDataSource {
    suspend fun updateToken(userId: String, token: String, deviceModel: String): Boolean
}