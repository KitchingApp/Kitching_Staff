package com.kitching.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.data.dto.FcmTokenDTO
import com.kitching.data.firebase.COLLECTION_FIREBASE_MESSAGING_TOKEN
import kotlinx.coroutines.tasks.await

class FcmTokenDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    FcmTokenDataSource {
    override suspend fun updateToken(userId: String, token: String, deviceModel: String): Boolean =
        runCatching {
            val fcmTokenQuerySnapshot = db.collection(COLLECTION_FIREBASE_MESSAGING_TOKEN).whereEqualTo("userId", userId).whereEqualTo("deviceModel", deviceModel).get().await()

            if (!fcmTokenQuerySnapshot.isEmpty) fcmTokenQuerySnapshot.first().reference.delete()
            createToken(userId = userId, token = token, deviceModel = deviceModel)
        }.isSuccess

    private suspend fun createToken(userId: String, token: String, deviceModel: String): Boolean =
        runCatching {
            db.collection(COLLECTION_FIREBASE_MESSAGING_TOKEN).add(
                FcmTokenDTO(
                    id = "",
                    userId = userId,
                    token = token,
                    deviceModel = deviceModel
                )
            ).await().apply {
                update("id", id).await()
            }
        }.isSuccess
}