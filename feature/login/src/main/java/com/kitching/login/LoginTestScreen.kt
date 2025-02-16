package com.kitching.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginMainScreen(onLogin: () -> Unit) {
    Button(onClick = onLogin) {
        Text("로그인")
    }
}

@Composable
fun TeamScreen(onSelect: () -> Unit, onJoin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = onSelect) {
            Text("팀선택")
        }

        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = onJoin) {
            Text("초대코드")
        }
    }
}

@Composable
fun InviteCodeScreen(onJoinTeam: () -> Unit) {
    Button(onClick = onJoinTeam) {
        Text("초대코드입력")
    }
}