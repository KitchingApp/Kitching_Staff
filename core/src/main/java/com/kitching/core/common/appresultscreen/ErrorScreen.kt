package com.kitching.core.common.appresultscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.core.R
import com.kitching.core.designsystem.Body1
import com.kitching.core.designsystem.Caption1_m
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray500
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(dimensionResource(R.dimen.error_screen_icon_size)),
                model = R.drawable.logo_dish,
                contentDescription = null
            )
            Text(
                text = message,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = NeutralGray800
            )
            Text(
                text = stringResource(R.string.error_retry_message),
                style = Caption1_m,
                textAlign = TextAlign.Center,
                color = NeutralGray500
            )
            TextButton(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.error_retry_btn_width))
                    .height(dimensionResource(R.dimen.error_retry_btn_height)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.error_retry_btn_radius)),
                colors = ButtonDefaults.textButtonColors().copy(
                    containerColor = PrimaryGreen300,
                    contentColor = NeutralGray0
                ),
                onClick = onRetry
            ) {
                Text(
                    text = stringResource(R.string.retry),
                    style = Body1
                )
            }
        }
    }
}