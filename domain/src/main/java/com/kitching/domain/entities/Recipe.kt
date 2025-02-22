package com.kitching.domain.entities

data class Recipe(
    val recipeId: String,
    val recipeName: String,
    val picture: String,
    val ingredient: List<Ingredient>,
    val steps: List<String>,
)
