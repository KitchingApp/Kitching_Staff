package com.kitching.data.datasource.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.kitching.core.exception.ExceptionHandler
import com.kitching.core.exception.KitchingRuntimeException
import com.kitching.data.datasource.FcmTokenDataSource
import com.kitching.data.dto.FcmTokenDTO
import com.kitching.data.firebase.COLLECTION_FIREBASE_MESSAGING_TOKEN
import com.kitching.data.firebase.DOCUMENT_ID
import com.kitching.data.firebase.DOCUMENT_USER_ID
import com.kitching.data.firebase.FIELD_DEVICE_MODEL
import com.kitching.data.firebase.FILED_TOKEN
import kotlinx.coroutines.tasks.await

class FcmTokenDataSourceImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) :
    FcmTokenDataSource {
    override suspend fun updateToken(userId: String, token: String, deviceModel: String): Boolean = ExceptionHandler.safeCall {
        try {
            val fcmTokenQuerySnapshot = db.collection(COLLECTION_FIREBASE_MESSAGING_TOKEN).whereEqualTo(DOCUMENT_USER_ID, userId).whereEqualTo(FIELD_DEVICE_MODEL, deviceModel).get().await()

            if (!fcmTokenQuerySnapshot.isEmpty) {
                val existingDoc = fcmTokenQuerySnapshot.first()
                existingDoc.reference.update(FILED_TOKEN, token).await()

                true
            } else {
                createToken(userId = userId, token = token, deviceModel = deviceModel)
            }
        } catch (e: Exception) {
            throw KitchingRuntimeException.FcmTokenUpdateFailedException(cause = e)
        }
    }

    private suspend fun createToken(userId: String, token: String, deviceModel: String): Boolean = ExceptionHandler.safeCall {
        try {
            db.collection(COLLECTION_FIREBASE_MESSAGING_TOKEN).add(
                FcmTokenDTO(
                    id = "",
                    userId = userId,
                    token = token,
                    deviceModel = deviceModel
                )
            ).await().apply {
                update(DOCUMENT_ID, id).await()
            }

            true
        } catch (e: Exception) {
            throw KitchingRuntimeException.FcmTokenCreateFailedException(cause = e)
        }
    }
}