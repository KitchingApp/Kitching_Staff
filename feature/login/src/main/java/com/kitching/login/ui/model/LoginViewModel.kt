package com.kitching.login.ui.model

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kitching.data.PreferencesDataSource
import com.kitching.domain.entities.Team
import com.kitching.domain.entities.User
import com.kitching.domain.repository.FcmTokenRepository
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.repository.TeamRepository
import com.kitching.domain.util.AppResult
import com.kitching.domain.util.UiState
import com.kitching.domain.util.getDisplayMessage
import com.kitching.login.SplashEntryPoint
import com.kitching.login.SplashResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val teamRepository: TeamRepository,
    private val fcmTokenRepository: FcmTokenRepository
) : ViewModel() {

    private val _splashResult = MutableStateFlow(UiState<SplashResult>())
    val splashResult get() = _splashResult.asStateFlow()

    fun initializeAppInfoState(context: Context) {
        viewModelScope.launch {
            _splashResult.value = _splashResult.value.toLoading()

            try {
                val userId = PreferencesDataSource(context).getUserId()
                val teamId = PreferencesDataSource(context).getTeamId()

                when {
                    // 둘 다 없으면 로그인 화면
                    userId.isEmpty() && teamId.isEmpty() -> {
                        _splashResult.value = _splashResult.value.toSuccess(
                            SplashResult(entryPoint = SplashEntryPoint.LOGIN)
                        )
                    }

                    // userId만 있으면 사용자 정보 로드 후 팀선택 화면
                    userId.isNotEmpty() && teamId.isEmpty() -> {
                        loadUserFromFirebase(userId) { user ->
                            _splashResult.value = _splashResult.value.toSuccess(
                                SplashResult(
                                    entryPoint = SplashEntryPoint.TEAM_SELECT,
                                    user = user
                                )
                            )
                        }
                    }

                    // 둘 다 있으면 사용자 정보와 팀 정보 로드 후 메인 화면
                    userId.isNotEmpty() && teamId.isNotEmpty() -> {
                        loadUserAndTeam(userId, teamId)
                    }

                    else -> {
                        _splashResult.value = _splashResult.value.toSuccess(
                            SplashResult(entryPoint = SplashEntryPoint.LOGIN)
                        )
                    }
                }
            } catch (e: Exception) {
                _splashResult.value = _splashResult.value.toError(e.message.toString())
            }
        }
    }

    private fun loadUserFromFirebase(userId: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            loginRepository.getUserById(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _splashResult.value = _splashResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        onResult(result.data)
                    }

                    is AppResult.Failure -> {
                        _splashResult.value = _splashResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private fun loadTeamFromFirebase(teamId: String, onResult: (Team?) -> Unit) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _splashResult.value = _splashResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        onResult(result.data)
                    }

                    is AppResult.Failure -> {
                        _splashResult.value = _splashResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private fun loadUserAndTeam(userId: String, teamId: String) {
        var user: User? = null
        var team: Team? = null
        var userLoaded = false
        var teamLoaded = false

        fun checkBothLoaded() {
            if (userLoaded && teamLoaded) {
                _splashResult.value = _splashResult.value.toSuccess(
                    SplashResult(
                        entryPoint = SplashEntryPoint.MAIN,
                        user = user,
                        team = team
                    )
                )
            }
        }

        loadUserFromFirebase(userId) { loadedUser ->
            user = loadedUser
            userLoaded = true
            checkBothLoaded()
        }

        loadTeamFromFirebase(teamId) { loadedTeam ->
            team = loadedTeam
            teamLoaded = true
            checkBothLoaded()
        }
    }

    private val _kakaoLoginState = MutableStateFlow(UiState<User>())
    val kakaoLoginState get() = _kakaoLoginState.asStateFlow()

    fun performKakaoLogin(context: Context) {
        viewModelScope.launch {
            _kakaoLoginState.value = _kakaoLoginState.value.toLoading()

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
            _kakaoLoginState.value = _kakaoLoginState.value.toError(error.message.toString())
        } else if (token != null) {
            UserApiClient.instance.me { user, userApiClientError ->
                if (userApiClientError != null) {
                    _kakaoLoginState.value = _kakaoLoginState.value.toError(userApiClientError.message.toString())
                } else if (user != null) {
                    val kakaoUid = user.id.toString()
                    val kakaoNickname = user.kakaoAccount?.profile?.nickname ?: ""
                    val kakaoImage = user.kakaoAccount?.profile?.profileImageUrl ?: ""

                    processLoginUserData(context, kakaoUid, kakaoNickname, kakaoImage)
                }
            }
        }
    }

    private fun processLoginUserData(context: Context, userId: String, userNickname: String, userImage: String) {
        viewModelScope.launch {
            loginRepository.checkAndSaveUser(userId, userNickname, userImage).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toLoading()
                    }

                    is AppResult.Success -> {
                        saveUserIdToDataStore(context, userId)
                    }

                    is AppResult.Failure -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private fun saveUserIdToDataStore(context: Context, userId: String) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveUserId(userId)

            loginRepository.getUserById(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private var _teamIdSaveResult = MutableStateFlow(UiState<Team>())
    val teamIdSaveResult get() = _teamIdSaveResult.asStateFlow()

    fun saveTeamIdToDataStore(teamId: String, context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveTeamId(teamId)

            teamRepository.getTeam(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _teamList = MutableStateFlow(UiState<List<Team>>())
    val teamList get() = _teamList.asStateFlow()

    fun updateToken(context: Context, userId: String) {
        viewModelScope.launch {
            val token = PreferencesDataSource(context).getFcmToken()
            val deviceModel = Build.MODEL

            fcmTokenRepository.updateToken(userId, token, deviceModel).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _teamList.value = _teamList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        getTeamList(userId)
                    }

                    is AppResult.Failure -> {
                        _teamList.value = _teamList.value.toError(result.getDisplayMessage())
                    }
                }

            }
        }
    }

    fun getTeamList(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _teamList.value = _teamList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamList.value = _teamList.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamList.value = _teamList.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }

    private val _joinTeamResult = MutableStateFlow(UiState<Team>())
    val joinTeamResult get() = _joinTeamResult.asStateFlow()

    fun joinTeamByInviteCode(userId: String, inviteCode: String) {
        viewModelScope.launch {
            teamRepository.joinTeamByInviteCode(userId, inviteCode).collectLatest { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _joinTeamResult.value = _joinTeamResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _joinTeamResult.value = _joinTeamResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _joinTeamResult.value = _joinTeamResult.value.toError(result.getDisplayMessage())
                    }
                }
            }
        }
    }
}