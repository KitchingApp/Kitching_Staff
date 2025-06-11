package com.kitching.login.ui.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import com.kitching.login.SplashEntryPoint
import com.kitching.login.SplashResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _splashResult = MutableStateFlow<AppResult<SplashResult>>(AppResult.Initial)
    val splashResult: StateFlow<AppResult<SplashResult>> = _splashResult

    fun initializeAppInfoState(context: Context) {
        viewModelScope.launch {
            _splashResult.value = AppResult.Loading

            try {
                val userIdResult = PreferencesDataSource(context).getUserId().first()
                val teamIdResult = PreferencesDataSource(context).getTeamId().first()

                if (userIdResult !is AppResult.Success || teamIdResult !is AppResult.Success) {
                    _splashResult.value = AppResult.Success(
                        SplashResult(SplashEntryPoint.LOGIN)
                    )
                    return@launch
                }

                val userId = userIdResult.data
                val teamId = teamIdResult.data

                when {
                    // 둘 다 없으면 로그인 화면
                    userId.isEmpty() && teamId.isEmpty() -> {
                        _splashResult.value = AppResult.Success(
                            SplashResult(entryPoint = SplashEntryPoint.LOGIN)
                        )
                    }

                    // userId만 있으면 사용자 정보 로드 후 팀선택 화면
                    userId.isNotEmpty() && teamId.isEmpty() -> {
                        val user = loadUserFromFirebase(userId)
                        _splashResult.value = AppResult.Success(
                            SplashResult(
                                entryPoint = SplashEntryPoint.TEAM_SELECT,
                                user = user
                            )
                        )
                    }

                    // 둘 다 있으면 사용자 정보와 팀 정보 로드 후 메인 화면
                    userId.isNotEmpty() && teamId.isNotEmpty() -> {
                        val user = loadUserFromFirebase(userId)
                        val team = loadTeamFromFirebase(teamId)
                        _splashResult.value = AppResult.Success(
                            SplashResult(
                                entryPoint = SplashEntryPoint.MAIN,
                                user = user,
                                team = team
                            )
                        )
                    }

                    else -> {
                        _splashResult.value = AppResult.Success(
                            SplashResult(entryPoint = SplashEntryPoint.LOGIN)
                        )
                    }
                }


            } catch (e: Exception) {
                _splashResult.value = AppResult.Failure(e)
            }
        }
    }

    private suspend fun loadUserFromFirebase(userId: String): User? {
        return try {
            val userResult = loginRepository.getUserById(userId).first()
            if (userResult is AppResult.Success) {
                userResult.data
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun loadTeamFromFirebase(teamId: String): Team? {
        return try {
            val teamResult = teamRepository.getTeam(teamId).first()
            if (teamResult is AppResult.Success) {
                teamResult.data
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

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