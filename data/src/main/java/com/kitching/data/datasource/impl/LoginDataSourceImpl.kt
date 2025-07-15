package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.datasource.LoginDataSource
import com.kitching.data.dto.UserDTO
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.data.firebase.FIELD_USER_IMAGE
import com.kitching.data.firebase.FIELD_USER_NAME
import kotlinx.coroutines.tasks.await

class LoginDataSourceImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): LoginDataSource {
    override suspend fun checkAndSaveUser(
        userId: String,
        userName: String,
        userImage: String,
    ): Boolean = runCatching {
        val userDoc = db.collection(COLLECTION_USER).document(userId).get().await().toObject(UserDTO::class.java)
        if(userDoc == null) {
            db.collection(COLLECTION_USER).add(UserDTO(id = userId, userName = userName, userImage = userImage)).await()
        } else {
            db.collection(COLLECTION_USER).document(userId).update(FIELD_USER_NAME, userName, FIELD_USER_IMAGE, userImage).await()
        }
    }.isSuccess

    override suspend fun getUserById(userId: String): UserDTO {
        return db.collection(COLLECTION_USER).document(userId).get().await().toObject(UserDTO::class.java) ?: UserDTO()
    }
}