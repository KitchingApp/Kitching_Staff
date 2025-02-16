package com.kitching.core.common

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kitching.core.designsystem.theme.H3
import com.kitching.core.designsystem.theme.H3_m
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray600
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    topAppBarState: TopAppBarState
) {
    CenterAlignedTopAppBar(
        modifier = if (topAppBarState.containerColor == PrimaryGreen300) {
            Modifier
        } else {
            Modifier.drawBehind {
                drawLine(
                    color = NeutralGray800,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
        },
        title = {
            Text(
                text = topAppBarState.title,
                style = if(topAppBarState.containerColor == PrimaryGreen300) H3 else H3_m
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarState.containerColor,
            titleContentColor = if(topAppBarState.containerColor == PrimaryGreen300) NeutralGray0 else NeutralGray800,
            actionIconContentColor = if(topAppBarState.containerColor == PrimaryGreen300) NeutralGray0 else NeutralGray600,
            navigationIconContentColor = if(topAppBarState.containerColor == PrimaryGreen300) NeutralGray0 else NeutralGray600
        ),
        navigationIcon = {
            IconButton(onClick = topAppBarState.onClickNavIcon) {
                Icon(
                    imageVector = ImageVector.vectorResource(topAppBarState.navIconInfo.icon),
                    contentDescription = topAppBarState.navIconInfo.description,
                )
            }
        },
        actions = {
            if(topAppBarState.actionIconInfo !== ActionIconInfo.NULL) {
                IconButton(onClick = topAppBarState.onClickActionIcon) {
                    Icon(
                        imageVector = ImageVector.vectorResource(topAppBarState.actionIconInfo.icon),
                        contentDescription = topAppBarState.actionIconInfo.description,
                    )
                }
            }
        }
    )
}