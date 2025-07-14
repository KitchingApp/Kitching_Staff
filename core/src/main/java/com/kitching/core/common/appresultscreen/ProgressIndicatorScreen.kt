package com.kitching.core.common.appresultscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.kitching.core.R
import com.kitching.core.designsystem.Caption1_m
import com.kitching.core.designsystem.H1
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray100
import com.kitching.core.designsystem.PrimaryGreen300

@Composable
fun ProgressIndicatorScreen() {
    val indicatorItem = ProgressIndicatorItem.getRandomItem()

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.defaultPadding)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier.width(100.dp),
                        model = indicatorItem.image,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(indicatorItem.title),
                        style = H1.copy(color = PrimaryGreen300)
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier.width(240.dp).height(16.dp),
                    trackColor = NeutralGray100,
                    color = PrimaryGreen300
                )
                Text(
                    text = stringResource(R.string.progress_indicator_common_message),
                    style = Caption1_m.copy(color = NeutralGray0)
                )
            }
        }
    }
}