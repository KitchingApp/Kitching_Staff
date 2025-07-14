package com.kitching.main.view.recipe.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.kitching.main.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Caption1_R
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.ShadowColor
import com.kitching.core.designsystem.dropShadow
import com.kitching.domain.entities.Recipe

@Composable
fun RecipeCardItem(
    recipe: Recipe,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(
                width = dimensionResource(R.dimen.recipe_card_item_width),
                height = dimensionResource(R.dimen.recipe_card_item_height)
            )
            .dropShadow(
                RoundedCornerShape(KitchingDimens.Corner.xSmall),
                ShadowColor,
                blur = dimensionResource(R.dimen.recipe_card_item_blur)
            )
            .clickable { onCardClick() },
        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(NeutralGray0, RoundedCornerShape(KitchingDimens.Corner.xSmall)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = recipe.picture,
                contentDescription = recipe.recipeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.recipe_card_item_image_height)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.recipe_loding_img),
                error = painterResource(R.drawable.recipe_error_img),
                fallback = painterResource(R.drawable.recipe_empty_img)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.recipe_card_item_box_height)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = recipe.recipeName,
                    style = Caption1_R.copy(color = NeutralGray800),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}