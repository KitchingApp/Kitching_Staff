package com.kitching.data.repository

import com.kitching.data.datasource.LoginDataSource
import com.kitching.data.datasource.impl.LoginDataSourceImpl
import com.kitching.domain.entities.User
import com.kitching.domain.repository.LoginRepository
import com.kitching.domain.util.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource = LoginDataSourceImpl(),
): LoginRepository {
    override fun checkAndSaveUser(
        uid: String,
        userName: String,
        userImage: String,
    ) = flow {
        emit(AppResult.Loading)
        emit(AppResult.Success(dataSource.checkAndSaveUser(uid, userName, userImage)))
    }.catch {
        emit(AppResult.Failure(it))
    }

    override fun getUserById(userId: String): Flow<AppResult<User>> = flow {
        emit(AppResult.Loading)
        try {
            val user = dataSource.getUserById(userId)
            emit(AppResult.Success(user.toDomain()))
        } catch (e: Exception) {
            emit(AppResult.Failure(e))
            }
    }
}