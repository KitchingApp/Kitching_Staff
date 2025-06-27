package com.kitching.domain.repository

import com.kitching.domain.entities.Recipe
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(teamId: String): Flow<AppResult<List<Recipe>>>
}