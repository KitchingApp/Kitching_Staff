package com.kitching.main.view.other.detailsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kitching.core.common.widget.KitchingVerticalDivider
import com.kitching.core.designsystem.theme.Body1_m
import com.kitching.core.designsystem.theme.Body2_m
import com.kitching.core.designsystem.theme.H2
import com.kitching.core.designsystem.theme.KitchingDimens
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.defaultHorizontalPadding
import com.kitching.domain.entities.Notice

@Composable
fun NoticeContentSection(
    notice: Notice,
) {
    Column(
        modifier = Modifier
            .defaultHorizontalPadding(),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = KitchingDimens.Margin.large),
            text = notice.title,
            style = H2.copy(color = NeutralGray800)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = notice.writerName,
                style = Body1_m.copy(color = NeutralGray800)
            )

            KitchingVerticalDivider(
                modifier = Modifier
                    .padding(horizontal = KitchingDimens.Margin.xSmall)
                    .height(KitchingDimens.Border.xxLarge),
                color = NeutralGray800,
                thickness = KitchingDimens.Border.xxSmall
            )

            Text(
                text = notice.date,
                style = Body1_m.copy(color = NeutralGray800)
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = KitchingDimens.Margin.xLarge),
            text = notice.content,
            style = Body2_m.copy(color = NeutralGray800)
        )
    }
}