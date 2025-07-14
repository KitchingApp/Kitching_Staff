package com.kitching.main.view.drawer.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import com.kitching.main.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.kitching.core.common.widget.TeamCardItem
import com.kitching.core.designsystem.H3_m
import com.kitching.core.designsystem.NeutralGray400
import com.kitching.domain.entities.Team

@Composable
fun DrawerTeamList(
    teamList: List<Team>,
    onTeamClick: (Team) -> Unit,
) {
    if (teamList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "다른 팀이 없습니다",
                style = H3_m.copy(color = NeutralGray400),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.drawer_item_spaced_by))
        ) {
            items(
                items = teamList,
                key = { team -> team.teamId }
            ) { team ->
                TeamCardItem(team) {
                    onTeamClick(team)
                }
            }
        }
    }
}