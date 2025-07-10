package com.kitching.domain.entities


data class Comment(
    val id: String,
    val userId: String,
    val userName: String,
    val upLoadTime: String,
    val content: String,
)
