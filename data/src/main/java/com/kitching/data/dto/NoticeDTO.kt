package com.kitching.data.dto

data class NoticeDTO(
    val id: String = "",
    val teamId: String = "",
    val writerId: String = "",
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val comments: List<CommentDTO> = emptyList()
)