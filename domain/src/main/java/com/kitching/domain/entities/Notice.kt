package com.kitching.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Notice(
    val noticeId: String,
    val writerName: String,
    val date: String,
    val title: String,
    val content: String,
    val comments: List<Comment>
) : Parcelable
