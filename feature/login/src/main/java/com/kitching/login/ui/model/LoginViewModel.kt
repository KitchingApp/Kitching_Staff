package com.kitching.login.ui.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.repository.FcmTokenRepository
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val teamRepository: TeamRepository,
    private val tokenRepository: FcmTokenRepository
) : ViewModel() {

    private val _kakaoLoginState = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val kakaoLoginState: StateFlow<AppResult<Boolean>> = _kakaoLoginState

    fun performKakaoLogin(context: Context) {
        viewModelScope.launch {
            _kakaoLoginState.value = AppResult.Loading
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    handleKakaoLoginResult(token, error, context)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    handleKakaoLoginResult(token, error, context)
                }
            }
        }
    }

    private fun handleKakaoLoginResult(token: OAuthToken?, error: Throwable?, context: Context) {
        if (error != null) {
            _kakaoLoginState.value = AppResult.Failure(error)
        } else if (token != null) {
            UserApiClient.instance.me { user, userApiClientError ->
                if (userApiClientError != null) {
                    _kakaoLoginState.value = AppResult.Failure(userApiClientError)
                } else if (user != null) {
                    _kakaoLoginState.value = AppResult.Success(true)
                    val kakaoUid = user.id.toString()

                    checkAndSaveUser(
                        kakaoUid,
                        user.kakaoAccount?.profile?.nickname ?: "",
                        user.kakaoAccount?.profile?.profileImageUrl ?: ""
                    )
                    saveUserIdToDatastore(kakaoUid, context)
                }
            }
        }
    }

    private val _checkAndSaveUserResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val checkAndSaveUserResult get() = _checkAndSaveUserResult.asStateFlow()

    private fun checkAndSaveUser(
        kakaoUid: String,
        kakaoNickname: String,
        kakaoProfileImage: String
    ) {
        viewModelScope.launch {
            loginRepository.checkAndSaveUser(kakaoUid, kakaoNickname, kakaoProfileImage)
                .collectLatest {
                    _checkAndSaveUserResult.value = it
                }
        }
    }

    private val _saveUserIdResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val saveUserIdResult get() = _saveUserIdResult.asStateFlow()

    private fun saveUserIdToDatastore(userId: String, context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveUserId(userId).collectLatest {
                _saveUserIdResult.value = it
            }
        }
    }


    private val _teamList = MutableStateFlow<AppResult<List<Team>>>(AppResult.Initial)
    val teamList get() = _teamList.asStateFlow()

    fun getTeamList(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest {
                _teamList.value = it
            }
        }
    }

    private val _userId = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val userId = _userId.asStateFlow()

    fun getUserId(context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).getUserId().collectLatest {
                _userId.value = it
            }
        }
    }

    private val _userIdSaveResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val userIdSaveResult get() = _userIdSaveResult.asStateFlow()

    fun saveUserId(userId: String, context: Context) {
        viewModelScope.launch {
            try {
                _userIdSaveResult.value = AppResult.Loading
                PreferencesDataSource(context).saveUserId(userId)
                _userIdSaveResult.value = AppResult.Success(true)
            } catch (e: Throwable) {
                AppResult.Failure(e)
            }

        }
    }

    private val _teamId = MutableStateFlow<AppResult<String>>(AppResult.Initial)
    val teamId = _teamId.asStateFlow()

    fun getTeamIdFromDataStore(context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).getTeamId().collectLatest {
                _teamId.value = it
            }
        }
    }

    private var _teamIdSaveResult = MutableStateFlow<AppResult<Boolean>>(AppResult.Initial)
    val teamIdSaveResult get() = _teamIdSaveResult.asStateFlow()

    fun saveTeamIdToDataStore(teamId: String, context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveTeamId(teamId).collectLatest {
                _teamIdSaveResult.value = it
            }
        }
    }
}