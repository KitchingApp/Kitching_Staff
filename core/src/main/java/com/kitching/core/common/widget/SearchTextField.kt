package com.kitching.core.common.widget

import com.kitching.core.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray200
import com.kitching.core.designsystem.NeutralGray600

@Composable
fun SearchTextField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = NeutralGray0,
                shape = RoundedCornerShape(KitchingDimens.Corner.small)
            )
            .border(
                width = 1.dp,
                color = NeutralGray200,
                shape = RoundedCornerShape(KitchingDimens.Corner.small)
            )
            .padding(horizontal = KitchingDimens.Margin.medium, vertical = KitchingDimens.Margin.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                textStyle = Body1_m.copy(color = NeutralGray600),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    }
                )
            )

            AsyncImage(
                model = R.drawable.icon_search,
                contentDescription = "search icon",
                modifier = Modifier.size(KitchingDimens.Size.large)
                    .clickable {
                        onSearch()
                    }
            )
        }
    }
}