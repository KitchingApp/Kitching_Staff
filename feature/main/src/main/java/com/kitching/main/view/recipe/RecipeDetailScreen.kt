package com.kitching.main.view.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.KitchingStaffTheme
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.domain.entities.Recipe
import com.kitching.main.view.recipe.detailsection.RecipeImageSection
import com.kitching.main.view.recipe.detailsection.RecipeIngredientsSection
import com.kitching.main.view.recipe.detailsection.RecipeStepsSection

@Composable
fun RecipeDetailScreen(
    commonState: CommonState,
    recipe: Recipe,
    onBack: () -> Unit
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = recipe.recipeName,
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    KitchingStaffTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(KitchingDimens.Spacing.xSmall)
        ) {
            item {
                RecipeImageSection(recipe.picture, recipe.recipeName)
            }

            item {
                RecipeIngredientsSection(recipe.ingredient)
            }

            item {
                RecipeStepsSection(recipe.steps)
            }
        }
    }
}