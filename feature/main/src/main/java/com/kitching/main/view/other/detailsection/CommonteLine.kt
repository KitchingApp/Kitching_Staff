package com.kitching.main.view.other.detailsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.kitching.core.common.widget.KitchingHorizontalDivider
import com.kitching.core.designsystem.theme.H5
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray200
import com.kitching.core.designsystem.theme.NeutralGray500
import com.kitching.core.designsystem.theme.defaultHorizontalPadding
import com.kitching.main.R

@Composable
fun CommentLine() {
    Column() {
        KitchingHorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = NeutralGray200,
            thickness = KitchingDimens.Border.xxxSmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultHorizontalPadding()
                .padding(vertical = KitchingDimens.Margin.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(KitchingDimens.Size.medium),
                model = R.drawable.icon_comment,
                contentDescription = "comment icon"
            )

            Text(
                modifier = Modifier.padding(horizontal = KitchingDimens.Margin.xMedium),
                text = stringResource(R.string.other_comment_title),
                style = H5.copy(NeutralGray500)
            )
        }

        KitchingHorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = NeutralGray200,
            thickness = KitchingDimens.Border.xxxSmall
        )
    }
}