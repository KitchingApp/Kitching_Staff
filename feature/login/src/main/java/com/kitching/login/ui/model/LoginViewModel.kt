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
import com.kitching.domain.util.UiState
import com.kitching.login.SplashEntryPoint
import com.kitching.login.SplashResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _splashResult = MutableStateFlow(UiState<SplashResult>())
    val splashResult get() = _splashResult.asStateFlow()

    fun initializeAppInfoState(context: Context) {
        viewModelScope.launch {
            _splashResult.value = _splashResult.value.toLoading()

            try {
                /**
                 * 추후 AppResult에 따라 핸들리필요할듯 지금은 빠르게 불러와져서 문제 없는듯함
                 * */

                val userIdResult = PreferencesDataSource(context).getUserId().first()
                val teamIdResult = PreferencesDataSource(context).getTeamId().first()


                val userId = (userIdResult as AppResult.Success<String>).data
                val teamId = (teamIdResult as AppResult.Success<String>).data

                when {
                    // 둘 다 없으면 로그인 화면
                    userId.isEmpty() && teamId.isEmpty() -> {
                        _splashResult.value = _splashResult.value.toSuccess(
                            SplashResult(entryPoint = SplashEntryPoint.LOGIN)
                        )
                    }

                    // userId만 있으면 사용자 정보 로드 후 팀선택 화면
                    userId.isNotEmpty() && teamId.isEmpty() -> {
                        val user = loadUserFromFirebase(userId)

                        _splashResult.value = _splashResult.value.toSuccess(
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

                        _splashResult.value = _splashResult.value.toSuccess(
                            SplashResult(
                                entryPoint = SplashEntryPoint.MAIN,
                                user = user,
                                team = team
                            )
                        )
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

    private suspend fun loadUserFromFirebase(userId: String): User? {
        try {
            var userResult: User? = null

            loginRepository.getUserById(userId).collectLatest { result ->
                if (result is AppResult.Success) {
                   userResult = result.data
                }
            }

            return userResult
        } catch (e: Exception) {
            return null
        }
    }

    private suspend fun loadTeamFromFirebase(teamId: String): Team? {
        try {
            var teamResult: Team? = null

            teamRepository.getTeam(teamId).collectLatest { result ->
                if (result is AppResult.Success) {
                    teamResult = result.data
                }
            }

            return teamResult
        } catch (e: Exception) {
            return null
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
                    is AppResult.Initial -> {
                        _kakaoLoginState.value = _kakaoLoginState.value
                    }

                    is AppResult.Loading -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toLoading()
                    }

                    is AppResult.Success -> {
                        saveUserIdToDataStore(context, userId)
                    }

                    is AppResult.Failure -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private fun saveUserIdToDataStore(context: Context, userId: String) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveUserId(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _kakaoLoginState.value = _kakaoLoginState.value
                    }

                    is AppResult.Loading -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toLoading()
                    }

                    is AppResult.Success -> {
                        loadUserData(userId)
                    }

                    is AppResult.Failure -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private fun loadUserData(userId: String) {
        viewModelScope.launch {
            loginRepository.getUserById(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _kakaoLoginState.value = _kakaoLoginState.value
                    }

                    is AppResult.Loading -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _kakaoLoginState.value = _kakaoLoginState.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private var _teamIdSaveResult = MutableStateFlow(UiState<Team>())
    val teamIdSaveResult get() = _teamIdSaveResult.asStateFlow()

    fun saveTeamIdToDataStore(teamId: String, context: Context) {
        viewModelScope.launch {
            PreferencesDataSource(context).saveTeamId(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value
                    }
                    is AppResult.Loading -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toLoading()
                    }
                    is AppResult.Success -> {
                        loadTeamData(teamId)
                    }
                    is AppResult.Failure -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private fun loadTeamData(teamId: String) {
        viewModelScope.launch {
            teamRepository.getTeam(teamId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value
                    }

                    is AppResult.Loading -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamIdSaveResult.value = _teamIdSaveResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }

    private val _teamList = MutableStateFlow(UiState<List<Team>>())
    val teamList get() = _teamList.asStateFlow()

    fun getTeamList(userId: String) {
        viewModelScope.launch {
            teamRepository.getTeamsByUserId(userId).collectLatest { result ->
                when (result) {
                    is AppResult.Initial -> {
                        _teamList.value = _teamList.value
                    }

                    is AppResult.Loading -> {
                        _teamList.value = _teamList.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _teamList.value = _teamList.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _teamList.value = _teamList.value.toError(result.exception.message.toString())
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
                    is AppResult.Initial -> {
                        _joinTeamResult.value = _joinTeamResult.value
                    }

                    is AppResult.Loading -> {
                        _joinTeamResult.value = _joinTeamResult.value.toLoading()
                    }

                    is AppResult.Success -> {
                        _joinTeamResult.value = _joinTeamResult.value.toSuccess(result.data)
                    }

                    is AppResult.Failure -> {
                        _joinTeamResult.value = _joinTeamResult.value.toError(result.exception.message.toString())
                    }
                }
            }
        }
    }
}