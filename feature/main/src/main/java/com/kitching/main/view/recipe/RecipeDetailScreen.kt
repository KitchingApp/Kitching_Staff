package com.kitching.main.view.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.theme.H2
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.domain.entities.Recipe

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        AsyncImage(
            model = recipe.picture,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        Text(
            text = "레시피 ID: ${recipe.recipeId}",
            style = H2
        )
        Text(
            text = "레시피 이름: ${recipe.recipeName}",
            style = H2
        )
        Text(
            text = "재료 개수: ${recipe.ingredient.size}개",
            style = H2
        )
        Text(
            text = "단계 수: ${recipe.steps.size}단계",
            style = H2
        )
    }
}