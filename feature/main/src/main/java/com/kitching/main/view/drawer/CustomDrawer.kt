package com.kitching.main.view.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.core.common.widget.TeamCardItem
import com.kitching.core.designsystem.theme.H1
import com.kitching.core.designsystem.theme.NeutralGray800
import com.kitching.core.designsystem.theme.PrimaryGreen300

@Composable
fun CustomDrawer(
    drawerState: DrawerState,
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
                }
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}