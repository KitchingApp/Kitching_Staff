package com.kitching.data.dto

import com.kitching.domain.entities.FcmToken

data class FcmTokenDTO(
    val id: String = "",
    val userId: String = "",
    val deviceModel: String = "",
    val token: String = ""
) {
    fun toDomain() = FcmToken(
        deviceModel = deviceModel,
        token = token
    )
}