package com.kitching.main.view.recipe.detailsection

import com.kitching.main.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.common.widget.KitchingHorizontalDivider
import com.kitching.core.designsystem.Caption1_R
import com.kitching.core.designsystem.H4_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray500
import com.kitching.core.designsystem.PrimaryGreen50
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.domain.entities.Ingredient

@Composable
fun RecipeIngredientsSection(ingredients: List<Ingredient>) {

    Column(
        modifier = Modifier
            .defaultHorizontalPadding()
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.recipe_detail_ingredient),
            style = H4_m,
            modifier = Modifier.padding(top = KitchingDimens.Margin.large, bottom = KitchingDimens.Margin.small)
        )

        Column(
            modifier = Modifier
                .border(KitchingDimens.Border.xxxSmall, NeutralGray500)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryGreen50)
                    .padding(KitchingDimens.Margin.small),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(R.string.recipe_detail_once),
                    style = Caption1_R,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.recipe_detail_twice),
                    style = Caption1_R,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.recipe_detail_unit),
                    style = Caption1_R,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.recipe_detail_ingredient),
                    style = Caption1_R,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
            }

            KitchingHorizontalDivider(
                color = NeutralGray500,
                thickness = KitchingDimens.Border.xxxSmall
            )

            ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(KitchingDimens.Margin.small),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "${ingredient.once}",
                        style = Caption1_R,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${ingredient.twice}",
                        style = Caption1_R,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = ingredient.unit,
                        style = Caption1_R,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = ingredient.ingredientName,
                        style = Caption1_R,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )

                }

                KitchingHorizontalDivider(
                    color = NeutralGray500,
                    thickness = KitchingDimens.Border.xxxSmall
                )
            }
        }
    }
}