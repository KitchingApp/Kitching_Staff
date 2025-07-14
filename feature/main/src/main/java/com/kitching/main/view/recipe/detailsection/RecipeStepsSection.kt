package com.kitching.main.view.recipe.detailsection

import com.kitching.main.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kitching.core.designsystem.Caption1_R
import com.kitching.core.designsystem.H4_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray500
import com.kitching.core.designsystem.defaultHorizontalPadding

@Composable
fun RecipeStepsSection(steps: List<String>) {

    Column(modifier = Modifier
        .defaultHorizontalPadding()
        .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.recipe_detail_step),
            style = H4_m,
            modifier = Modifier.padding(top = KitchingDimens.Margin.large, bottom = KitchingDimens.Margin.small)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(KitchingDimens.Border.xxxSmall, NeutralGray500)
                .background(Color.White)
                .padding(KitchingDimens.Margin.small)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                steps.forEachIndexed { index, step ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = KitchingDimens.Margin.xxSmall)
                    ) {
                        Text(
                            text = "${index + 1}.",
                            style = Caption1_R,
                            modifier = Modifier.padding(end = KitchingDimens.Margin.xSmall)
                        )
                        Text(
                            text = step,
                            style = Caption1_R
                        )
                    }
                }
            }
        }
    }
}