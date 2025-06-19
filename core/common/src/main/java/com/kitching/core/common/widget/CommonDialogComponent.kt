package com.kitching.core.common.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kitching.core.designsystem.theme.H5
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray200
import com.kitching.core.designsystem.theme.NeutralGray300
import com.kitching.core.designsystem.theme.NeutralGray400
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.core.designsystem.theme.defaultPadding

@Composable
fun CommonDialogComponent(
    height: Dp,
    paddingTop: Dp,
    paddingBottom: Dp,
    radius: Dp,
    confirmText: String,
    onClickConfirm: () -> Unit,
    cancelText: String,
    onClickCancel: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onClickCancel() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = NeutralGray0,
                    shape = RoundedCornerShape(radius)
                )
                .border(1.dp, NeutralGray300, RoundedCornerShape(radius))
                .height(height)
                .width(296.dp)
                .padding(defaultPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Row(
                    modifier = Modifier
                        .width(240.dp)
                        .height(32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            onClickConfirm()

                            // 작업 완료 후 닫기
                            onClickCancel()
                        },
                        modifier = Modifier
                            .width(112.dp)
                            .height(32.dp),
                        colors = ButtonColors(
                            containerColor = PrimaryGreen300,
                            contentColor = NeutralGray0,
                            disabledContainerColor = NeutralGray200,
                            disabledContentColor = NeutralGray400
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = confirmText,
                            style = H5
                        )
                    }
                    Button(
                        onClick = { onClickCancel() },
                        modifier = Modifier
                            .width(112.dp)
                            .height(32.dp),
                        colors = ButtonColors(
                            containerColor = NeutralGray0,
                            contentColor = NeutralGray400,
                            disabledContainerColor = NeutralGray200,
                            disabledContentColor = NeutralGray400
                        ),
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, NeutralGray300)
                    ) {
                        Text(
                            text = cancelText,
                            style = H5
                        )
                    }
                }
            }
        }
    }
}