package com.kitching.data.util

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Kitching 앱에서 사용하는 커스텀 RuntimeException
 *
 * @param message 에러 메시지
 * @param cause 원인이 되는 Exception
 * @param errorCode 에러 코드
 */
sealed class KitchingRuntimeException(
    message: String,
    cause: Throwable? = null,
    val errorCode: String? = null
) : RuntimeException(message, cause) {

    /**
     * 네트워크 관련 에러
     */
    class NetworkException(
        message: String = NETWORK_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = NETWORK_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 서버 에러
     */
    class ServerException(
        message: String = SERVER_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = SERVER_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 데이터 파싱 에러
     */
    class DataParsingException(
        message: String = DATA_PARSING_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = DATA_PARSING_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 인증 에러
     */
    class AuthenticationException(
        message: String = AUTHENTICATION_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = AUTHENTICATION_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 권한 에러
     */
    class PermissionException(
        message: String = PERMISSION_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = PERMISSION_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 일반적인 비즈니스 로직 에러
     */
    class BusinessLogicException(
        message: String,
        cause: Throwable? = null,
        errorCode: String? = BUSINESS_LOGIC_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 사용자 관련 에러
     */
    class UserNotFoundException(
        message: String = USER_NOT_FOUND_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = USER_NOT_FOUND_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 사용자 저장 실패 에러
     */
    class UserSaveFailedException(
        message: String = USER_SAVE_FAILED_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = USER_SAVE_FAILED_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 파이어스토어 문서 찾기 실패 에러
     */
    class FirestoreDocumentNotFoundException(
        message: String = FIRESTORE_DOCUMENT_NOT_FOUND_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = FIRESTORE_DOCUMENT_NOT_FOUND_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)

    /**
     * 알 수 없는 에러
     */
    class UnknownException(
        message: String = UNKNOWN_EXCEPTION_MESSAGE,
        cause: Throwable? = null,
        errorCode: String? = UNKNOWN_ERROR_CODE
    ) : KitchingRuntimeException(message, cause, errorCode)
}

/**
 * Exception을 KitchingRuntimeException으로 변환하는 확장 함수
 */
fun Throwable.toKitchingException(): KitchingRuntimeException {
    return when (this) {
        is KitchingRuntimeException -> this
        is UnknownHostException,
        is SocketTimeoutException,
        is ConnectException -> KitchingRuntimeException.NetworkException(
            message = NETWORK_CONNECTION_MESSAGE,
            cause = this
        )

        is IOException -> KitchingRuntimeException.NetworkException(
            message = NETWORK_EXCEPTION_MESSAGE,
            cause = this
        )

        is FirebaseNetworkException -> KitchingRuntimeException.NetworkException(
            message = NETWORK_CONNECTION_MESSAGE,
            cause = this
        )

        is FirebaseFirestoreException -> {
            when (this.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                    KitchingRuntimeException.PermissionException(
                        message = FIRESTORE_PERMISSION_DENIED_MESSAGE,
                        cause = this,
                        errorCode = FIRESTORE_PERMISSION_DENIED_ERROR_CODE
                    )
                FirebaseFirestoreException.Code.NOT_FOUND ->
                    KitchingRuntimeException.FirestoreDocumentNotFoundException(
                        cause = this
                    )
                FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED ->
                    KitchingRuntimeException.ServerException(
                        message = FIRESTORE_QUOTA_EXCEEDED_MESSAGE,
                        cause = this,
                        errorCode = FIRESTORE_QUOTA_EXCEEDED_ERROR_CODE
                    )
                FirebaseFirestoreException.Code.UNAUTHENTICATED ->
                    KitchingRuntimeException.AuthenticationException(
                        cause = this
                    )
                else -> KitchingRuntimeException.ServerException(
                    message = this.message ?: SERVER_EXCEPTION_MESSAGE,
                    cause = this
                )
            }
        }

        is FirebaseException -> KitchingRuntimeException.ServerException(
            message = SERVER_EXCEPTION_MESSAGE,
            cause = this
        )

        is IllegalArgumentException -> KitchingRuntimeException.BusinessLogicException(
            message = this.message ?: INVALID_REQUEST_MESSAGE,
            cause = this
        )

        else -> KitchingRuntimeException.UnknownException(
            message = this.message ?: UNKNOWN_EXCEPTION_MESSAGE,
            cause = this
        )
    }
}