package com.kitching.login.ui.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.login.R

@Composable
fun SplashUi() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.logo_dish_width))
                .height(dimensionResource(R.dimen.logo_dish_height)),
            model = R.drawable.logo_dish,
            contentDescription = null,
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.logo_with_text_padding)))
        Text(
            text = stringResource(R.string.Kitching),
            fontWeight = FontWeight.Black,
            color = PrimaryGreen300,
            fontSize = dimensionResource(R.dimen.logo_font_size).value.sp
        )
    }
}