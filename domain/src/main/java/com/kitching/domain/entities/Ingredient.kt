package com.kitching.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Ingredient(
    val ingredientId: String,
    val ingredientName: String,
    val once: Int,
    val twice: Int,
    val unit: String,
) : Parcelable
