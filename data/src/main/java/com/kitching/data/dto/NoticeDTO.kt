package com.kitching.data.dto

import com.kitching.domain.entities.Notice

data class NoticeDTO(
    val id: String = "",
    val teamId: String = "",
    val writerId: String = "",
    val title: String = "",
    val content: String = "",
    val date: String = "",
) {
    fun toDomain(): Notice {
        return Notice(
            noticeId = id,
            writerName = writerId,
            date = date,
            title = title,
            content = content
        )
    }
}