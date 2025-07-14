package com.kitching.main.view.recipe.list

import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.appresultscreen.EmptyScreen
import com.kitching.main.view.recipe.item.RecipeCardItem
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.domain.entities.Recipe

@Composable
fun RecipeGridList(
    recipeList: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = KitchingDimens.Margin.small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (recipeList.isEmpty()) {
            EmptyScreen(stringResource(R.string.recipe_empty_screen_message))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = KitchingDimens.Margin.small,
                    end = KitchingDimens.Margin.small,
                    top = KitchingDimens.Margin.small,
                    bottom = KitchingDimens.Margin.large
                ),
                horizontalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.large),
                verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.large),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = recipeList,
                    key = { it.recipeId }
                ) { recipe ->
                    RecipeCardItem(
                        recipe = recipe,
                    ) {
                        onRecipeClick(recipe)
                    }
                }
            }
        }
    }
}