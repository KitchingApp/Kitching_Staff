package com.kitching.data.datasource

import com.kitching.data.dto.RecipeDTO

interface RecipeDataSource {
    suspend fun getRecipes(teamId: String): List<RecipeDTO>
}