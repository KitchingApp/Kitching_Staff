package com.kitching.domain.entities

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
data class Comment(
    val id: String,
    val userId: String,
    val userName: String,
    val upLoadTime: String,
    val content: String,
) : Parcelable {
    val formattedDate: String
        @SuppressLint("DefaultLocale")
        get() = try {
            val dateTime = LocalDateTime.parse(upLoadTime)
            String.format("%02d / %02d", dateTime.monthValue, dateTime.dayOfMonth)
        } catch (e: Exception) {
            upLoadTime
        }
}
