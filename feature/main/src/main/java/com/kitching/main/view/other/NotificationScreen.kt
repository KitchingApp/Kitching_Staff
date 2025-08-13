package com.kitching.main.view.other

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.kitching.core.common.commonstate.CommonState
import com.kitching.core.designsystem.KitchingStaffTheme
import com.kitching.main.view.model.NotificationViewModel

@Composable
fun NotificationScreen(
    context: Context,
    commonState: CommonState
) {
    val viewModel: NotificationViewModel by lazy {
        NotificationViewModel(context)
    }

    KitchingStaffTheme {
        Column {
            Text(
                text = "알람 화면"
            )
        }
    }
}