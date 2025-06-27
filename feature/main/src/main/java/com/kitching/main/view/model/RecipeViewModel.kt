package com.kitching.main.view.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitching.domain.entities.Recipe
import com.kitching.domain.repository.RecipeRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _recipeList = MutableStateFlow<AppResult<List<Recipe>>>(AppResult.Initial)
    val recipeList get() =_recipeList.asStateFlow()

    fun getRecipeListByTeamId(teamId: String) {
        viewModelScope.launch {
            repository.getRecipes(teamId).collectLatest {
                _recipeList.value = it
            }
        }
    }
}