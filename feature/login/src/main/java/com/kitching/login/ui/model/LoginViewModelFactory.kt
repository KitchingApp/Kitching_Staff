package com.kitching.login.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kitching.data.repository.FcmTokenRepositoryImpl
import com.kitching.data.repository.LoginRepositoryImpl
import com.kitching.data.repository.TeamRepositoryImpl

class LoginViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                loginRepository = LoginRepositoryImpl(),
                teamRepository = TeamRepositoryImpl(),
                tokenRepository = FcmTokenRepositoryImpl()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
