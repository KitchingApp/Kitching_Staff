package com.kitching.domain.entities

data class Notice(
    val noticeId: String,
    val writerName: String,
    val date: String,
    val title: String,
    val content: String,
)
