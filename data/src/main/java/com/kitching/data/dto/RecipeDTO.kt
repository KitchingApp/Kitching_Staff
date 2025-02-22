package com.kitching.data.dto

import com.kitching.domain.entities.Recipe

data class RecipeDTO(
    val id: String = "",
    val teamId: String = "",
    val name: String = "",
    val picture: String = "",
    val ingredient: List<IngredientDTO> = emptyList(),
    val steps: List<String> = emptyList(),
) {
    fun toDomain(): Recipe {
        return Recipe(
            recipeId = id,
            recipeName = name,
            picture = picture,
            ingredient = ingredient.map { it.toDomain() },
            steps = steps
        )
    }
}