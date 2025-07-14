package com.kitching.main.view.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.kitching.main.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.core.common.commonstate.ActionIconInfo
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.common.commonstate.NavigationIconInfo
import com.kitching.core.designsystem.Caption1_m
import com.kitching.core.designsystem.H5_m
import com.kitching.core.designsystem.KitchingDimens
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.core.designsystem.NeutralGray0
import com.kitching.core.designsystem.NeutralGray800
import com.kitching.core.designsystem.PrimaryGreen300
import com.kitching.core.designsystem.SecondaryLightGreen500
import com.kitching.core.designsystem.ShadowColor
import com.kitching.core.designsystem.defaultHorizontalPadding
import com.kitching.core.designsystem.dropShadow

@Composable
fun InviteCodeScreen(
    context: Context,
    commonState: CommonState,
    onBack: () -> Unit
) {
    val team = commonState.appInfoState.value.teamInfo

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = stringResource(R.string.drawer_other_list_invite_code),
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = onBack,
        actionIconInfo = ActionIconInfo.NULL,
    )

    KitchingStaffTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .defaultHorizontalPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(dimensionResource(R.dimen.other_invite_code_main_icon_size)),
                model = R.drawable.icon_invite,
                contentDescription = null,
                colorFilter = ColorFilter.tint(SecondaryLightGreen500)
            )
            Text(
                modifier = Modifier.padding(bottom = KitchingDimens.Margin.xxLarge),
                text = stringResource(R.string.other_invite_code_message),
                style = H5_m.copy(NeutralGray800)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = KitchingDimens.Margin.xxLarge, bottom = KitchingDimens.Margin.large)
                    .background(
                        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall),
                        color = Color.Transparent
                    )
                    .border(
                        width = KitchingDimens.Border.xxxSmall,
                        color = PrimaryGreen300,
                        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(KitchingDimens.Margin.small),
                    text = team?.inviteCode ?: "",
                    style = Caption1_m.copy(color = NeutralGray800)
                )

                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        copyInviteCodeToClipboard(
                            context = context,
                            inviteCode = team?.inviteCode ?: ""
                        )
                    }
                ) {
                    AsyncImage(
                        modifier = Modifier.size(KitchingDimens.Size.large),
                        model = R.drawable.icon_copy_right,
                        contentDescription = "초대코드 복사"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .width(dimensionResource(R.dimen.other_invite_code_kakao_width))
                    .height(dimensionResource(R.dimen.other_invite_code_kakao_height))
                    .dropShadow(
                        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall),
                        offsetX = 0.dp,
                        offsetY = 2.dp,
                        blur = 8.dp,
                        spread = 0.dp,
                        color = ShadowColor
                    )
                    .background(
                        color = NeutralGray0,
                        shape = RoundedCornerShape(KitchingDimens.Corner.xSmall)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.size(KitchingDimens.Size.large),
                    model = R.drawable.icon_kakao_share,
                    contentDescription = "kakao share icon"
                )

                Text(
                    modifier = Modifier.padding(start = KitchingDimens.Margin.small),
                    text = "카카오톡으로 공유",
                    style = H5_m.copy(NeutralGray800)
                )
            }
        }
    }
}

private fun copyInviteCodeToClipboard(context: Context, inviteCode: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("초대코드", inviteCode)
    clipboard.setPrimaryClip(clip)
}