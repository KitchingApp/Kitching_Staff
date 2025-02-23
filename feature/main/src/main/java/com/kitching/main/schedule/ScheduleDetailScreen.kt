package com.kitching.main.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.core.common.ActionIconInfo
import com.kitching.core.common.CommonState
import com.kitching.core.common.NavigationIconInfo
import com.kitching.core.common.TeamCardItem
import com.kitching.core.designsystem.theme.PrimaryGreen300
import com.kitching.core.designsystem.theme.subTextColor
import com.kitching.main.R
import kotlinx.coroutines.launch

@Composable
fun ScheduleDetailScreen(
    commonState: CommonState,
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = PrimaryGreen300,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(60.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_left_triangle),
                contentDescription = "prev date button",
                tint = subTextColor
            )


            Text(
                text = "  2025-02-24  ",
                color = subTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_right_triangle),
                contentDescription = "next date button",
                tint = subTextColor
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TeamCardItem("      박민수 / 미들") { }

        TeamCardItem("      김수연 / 오픈") { }

        TeamCardItem("      김채연 / D/O") { }

        TeamCardItem("      박지수 / D/O") { }

        TeamCardItem("      박채아 / 마감") { }
    }
}