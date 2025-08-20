package com.kitching.data.repository

import com.kitching.data.datasource.FcmTokenDataSource
import com.kitching.domain.repository.FcmTokenRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenRepositoryImpl @Inject constructor(
    private val fcmTokenDataSource: FcmTokenDataSource
) : FcmTokenRepository {
    override suspend fun updateToken(
        userId: String,
        token: String,
        deviceModel: String
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(fcmTokenDataSource.updateToken(userId, token, deviceModel)))
    }.catch {
        emit(AppResult.Failure(it))
    }
}