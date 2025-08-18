package com.kitching.data.repository

import com.kitching.data.datasource.RecipeDataSource
import com.kitching.data.datasource.impl.RecipeDataSourceImpl
import com.kitching.domain.repository.RecipeRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource = RecipeDataSourceImpl()
) : RecipeRepository {
    override suspend fun getRecipes(teamId: String) = flow {
        emit(AppResult.Loading)

        val recipes = dataSource.getRecipes(teamId).map { it.toDomain() }

        emit(AppResult.Success(recipes))
    }.catch {
        emit(AppResult.Failure(it))
    }
}