package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.core.exception.KitchingRuntimeException
import com.kitching.data.datasource.LoginDataSource
import com.kitching.data.dto.UserDTO
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.FIELD_USER_IMAGE
import com.kitching.data.firebase.FIELD_USER_NAME
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : LoginDataSource {
    override suspend fun checkAndSaveUser(
        userId: String,
        userName: String,
        userImage: String,
    ): Boolean = ExceptionHandler.safeCall {
        val userDoc = db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(UserDTO::class.java)

        if (userDoc == null) {
            db.collection(COLLECTION_USER)
                .add(UserDTO(id = userId, userName = userName, userImage = userImage)).await()

            val savedUser = db.collection(COLLECTION_USER).document(userId).get().await()
                .toObject(UserDTO::class.java)

            if (savedUser == null) {
                throw KitchingRuntimeException.UserSaveFailedException()
            }

            true
        } else {
            db.collection(COLLECTION_USER).document(userId)
                .update(FIELD_USER_NAME, userName, FIELD_USER_IMAGE, userImage).await()

            true
        }
    }

    override suspend fun getUserById(userId: String): UserDTO = ExceptionHandler.safeCall {
        val userDoc = db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(UserDTO::class.java)
        userDoc ?: throw KitchingRuntimeException.UserNotFoundException()
    }
}