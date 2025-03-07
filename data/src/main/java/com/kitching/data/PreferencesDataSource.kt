package com.kitching.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "team")

class PreferencesDataSource(private val context: Context) {
    companion object {
        private val TEAM_ID = stringPreferencesKey("team_id")
        private val USER_ID = stringPreferencesKey("user_id")
        private val FCM_TOKEN = stringPreferencesKey("fcm_token")
    }

    fun saveUserId(userId: String): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)
        context.dataStore.edit { preferences -> preferences[USER_ID] = userId }
        emit(AppResult.Success(true))
    }.catch {
        emit(AppResult.Failure(it))
    }

    fun getUserId(): Flow<AppResult<String>> = context.dataStore.data.map { preferences ->
        AppResult.Loading
        AppResult.Success(preferences[USER_ID] ?: "")
    }.catch {
        AppResult.Failure(it)
    }

    fun clearUserId(): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)
        context.dataStore.edit { preferences -> preferences.remove(USER_ID) }
        emit(AppResult.Success(true))
    }.catch {
        emit(AppResult.Failure(it))
    }

    fun saveTeamId(teamId: String): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)
        context.dataStore.edit { preferences -> preferences[TEAM_ID] = teamId }
        emit(AppResult.Success(true))
    }.catch {
        emit(AppResult.Failure(it))
    }

    fun getTeamId(): Flow<AppResult<String>> = context.dataStore.data.map { preferences ->
        AppResult.Loading
        AppResult.Success(preferences[USER_ID] ?: "")
    }.catch {
        AppResult.Failure(it)
    }

    fun clearTeamId(): Flow<AppResult<Boolean>> = flow {
        emit(AppResult.Loading)
        context.dataStore.edit { preferences -> preferences.remove(TEAM_ID) }
        emit(AppResult.Success(true))
    }.catch {
        emit(AppResult.Failure(it))
    }

    /** 토큰이 있는지 확인하고 있으면 업데이트, 없으면 새로 저장 */
    suspend fun updateTokenAtDatastore(newToken: String) {
        context.dataStore.edit { preferences -> preferences[FCM_TOKEN] = newToken }
    }
}