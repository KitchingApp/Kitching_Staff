package com.kitching.data.dto

import com.kitching.domain.entities.User

data class UserDTO(val id: String = "", val userName: String = "", val userImage: String = "") {
    fun toDomain(): User {
        return User(
            userId = id,
            userName = userName,
            userImage = userImage
        )
    }
}