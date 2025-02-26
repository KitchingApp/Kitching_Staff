package com.kitching.domain.entities

data class Ingredient(
    val ingredientId: String,
    val ingredientName: String,
    val once: Int,
    val twice: Int,
    val unit: String,
)
