package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.UserDTO
import com.kitching.data.firebase.COLLECTION_USER
import com.kitching.domain.entities.User
import kotlinx.coroutines.tasks.await

class UserDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    UserDataSource {
    override suspend fun getUser(userId: String) =
        db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(UserDTO::class.java) ?: throw Throwable("User Not Exist")

    override suspend fun checkAndSaveUser(
        userId: String,
        userName: String,
        userImage: String
    ) = runCatching {
        val userDoc = db.collection(COLLECTION_USER).document(userId).get().await()
            .toObject(User::class.java)
        if (userDoc == null) {
            db.collection(COLLECTION_USER)
                .add(UserDTO(id = userId, userName = userName, userImage = userImage)).await()
        }
    }.isSuccess
}