package com.kitching.main.view.recipe.detailsection

import com.kitching.main.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
fun RecipeImageSection(
    picture: String,
    recipeName: String
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        model = picture,
        contentDescription = recipeName,
        placeholder = painterResource(R.drawable.recipe_loding_img),
        error = painterResource(R.drawable.recipe_error_img),
        fallback = painterResource(R.drawable.recipe_empty_img)
    )
}