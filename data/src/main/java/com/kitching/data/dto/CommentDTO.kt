package com.kitching.data.dto

import com.kitching.domain.entities.Comment

data class CommentDTO(
    val id: String,
    val userId: String,
    val userName: String,
    val upLoadTime: String,
    val content: String,
) {
    fun toDomain(): Comment {
        return Comment(
            id = id,
            userId = userId,
            userName = userName,
            upLoadTime = upLoadTime,
            content = content,
        )
    }
}
