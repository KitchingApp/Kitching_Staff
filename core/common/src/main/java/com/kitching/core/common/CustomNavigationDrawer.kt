package com.kitching.core.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.core.designsystem.theme.H1
import com.kitching.core.designsystem.theme.NeutralGray0
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300

@Composable
fun CustomNavigationDrawer(
    drawerState: DrawerState,
    onLogout: () -> Unit,
    content: @Composable () -> Unit
) {
    val teamListMockData = listOf(
        "A 레스토랑",
        "B 레스토랑",
        "C 레스토랑",
        "D 레스토랑",
        "E 레스토랑"
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
            ) {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(20.dp, 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            text = "팀 리스트",
                            style = H1.copy(color = NeutralGray800)
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 3.dp,
                            color = PrimaryGreen300
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        itemsIndexed(teamListMockData) { index, team ->
                            TeamCardItem(
                                teamName = team,
                                onCardClick = {}
                            )
                        }
                    }
                    TextButton(
                        modifier = Modifier
                            .width(260.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonColors(
                            containerColor = PrimaryGreen300,
                            contentColor = NeutralGray0,
                            disabledContainerColor = PrimaryGreen300,
                            disabledContentColor = NeutralGray0
                        ),
                        onClick = {
                            onLogout()
                        },
                    ) {
                        Text(
                            text = "로그아웃",
                            color = NeutralGray0,
                            style = H1.copy(
                                color = NeutralGray800,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}