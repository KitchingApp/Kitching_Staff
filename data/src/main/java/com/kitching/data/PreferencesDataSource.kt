package com.kitching.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesDataSource @Inject constructor(
    private val preferencesData: DataStore<Preferences>
) {
    companion object {
        private val TEAM_ID = stringPreferencesKey("team_id")
        private val USER_ID = stringPreferencesKey("user_id")
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
    }

    suspend fun saveUserId(userId: String) = preferencesData.edit { preferences -> preferences[USER_ID] = userId }

    suspend fun getUserId(): String = preferencesData.data.first()[USER_ID] ?: ""

    suspend fun saveTeamId(teamId: String) = preferencesData.edit { preferences -> preferences[TEAM_ID] = teamId }

    suspend fun getTeamId(): String = preferencesData.data.first()[TEAM_ID] ?: ""

    /** 토큰이 있는지 확인하고 있으면 업데이트, 없으면 새로 저장 */
    suspend fun saveFcmToken(newToken: String) = preferencesData.edit { preferences -> preferences[FCM_TOKEN] = newToken }

    suspend fun getFcmToken(): String = preferencesData.data.first()[FCM_TOKEN] ?: ""
}