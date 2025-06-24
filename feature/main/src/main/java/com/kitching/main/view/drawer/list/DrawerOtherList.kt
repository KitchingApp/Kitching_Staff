package com.kitching.main.view.drawer.list

import androidx.compose.foundation.layout.Arrangement
import com.kitching.main.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.kitching.core.common.widget.DrawerOtherItem

@Composable
fun DrawerOtherList(
    onInviteCodeClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onMemberClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.drawer_vertical_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.drawer_item_spaced_by))
    ) {
        DrawerOtherItem(
            text = stringResource(R.string.drawer_other_list_invite_code),
            onClick = onInviteCodeClick
        )

        DrawerOtherItem(
            text = stringResource(R.string.drawer_other_list_notice),
            onClick = onNoticeClick
        )

        DrawerOtherItem(
            text = stringResource(R.string.drawer_other_list_notification),
            onClick = onNotificationClick
        )

        DrawerOtherItem(
            text = stringResource(R.string.drawer_other_list_member),
            onClick = onMemberClick
        )
    }
}