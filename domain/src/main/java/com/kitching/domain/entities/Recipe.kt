package com.kitching.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    val recipeId: String,
    val recipeName: String,
    val picture: String,
    val ingredient: List<Ingredient>,
    val steps: List<String>,
) : Parcelable
