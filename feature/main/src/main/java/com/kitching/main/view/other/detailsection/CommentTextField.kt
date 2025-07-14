package com.kitching.main.view.other.detailsection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import coil3.compose.AsyncImage
import com.kitching.core.designsystem.Body1_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.NeutralGray100
import com.kitching.core.designsystem.NeutralGray200
import com.kitching.core.designsystem.NeutralGray500
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.main.R

@Composable
fun CommentTextField(
    modifier: Modifier,
    commentText: String,
    onCommentTextChange: (String) -> Unit,
    onSendComment: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.other_comment_section_height))
            .border(
                width = KitchingDimens.Border.xxxSmall,
                color = NeutralGray200,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(KitchingDimens.Margin.xMedium))

        BasicTextField(
            value = commentText,
            onValueChange = onCommentTextChange,
            modifier = Modifier
                .weight(1f)
                .height(dimensionResource(R.dimen.other_comment_text_field_height))
                .background(
                    color = NeutralGray100,
                    shape = RoundedCornerShape(KitchingDimens.Corner.xLarge)
                )
                .border(
                    width = KitchingDimens.Border.xxxSmall,
                    color = NeutralGray200,
                    shape = RoundedCornerShape(KitchingDimens.Corner.xLarge)
                )
                .padding(KitchingDimens.Margin.small),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendComment()
                }
            ),
            textStyle = Body1_m.copy(color = NeutralGray800),
            decorationBox = { innerTextField ->
                if (commentText.isEmpty()) {
                    Text(
                        text = stringResource(R.string.other_comment_text_field_hint),
                        style = Body1_m.copy(color = NeutralGray500)
                    )
                }
                innerTextField()
            }
        )

        Box(
            modifier = Modifier
                .padding(horizontal = KitchingDimens.Margin.xMedium)
                .clickable(enabled = commentText.isNotEmpty()) {
                    onSendComment()
                },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(KitchingDimens.Size.large),
                model = R.drawable.icon_send,
                contentDescription = "send icon"
            )
        }
    }
}