package com.kitching.data.util

object ExceptionHandler {
    /**
     * 코루틴에서 안전하게 코드를 실행하고 KitchingRuntimeException으로 변환
     */
    suspend inline fun <T> safeCall(crossinline action: suspend () -> T): T {
        return runCatching {
            action()
        }.getOrElse { exception ->
            throw exception.toKitchingException()
        }
    }

    /**
     * 에러 메시지를 사용자 친화적으로 변환
     */
    fun getDisplayMessage(exception: KitchingRuntimeException): String {
        return when (exception) {
            is KitchingRuntimeException.NetworkException ->
                NETWORK_CONNECTION_MESSAGE
            is KitchingRuntimeException.ServerException ->
                SERVER_EXCEPTION_MESSAGE
            is KitchingRuntimeException.DataParsingException ->
                DATA_PARSING_EXCEPTION_MESSAGE
            is KitchingRuntimeException.AuthenticationException ->
                LOGIN_REQUIRED_MESSAGE
            is KitchingRuntimeException.PermissionException ->
                PERMISSION_EXCEPTION_MESSAGE
            is KitchingRuntimeException.BusinessLogicException ->
                exception.message ?: INVALID_REQUEST_MESSAGE
            is KitchingRuntimeException.UnknownException ->
                UNKNOWN_EXCEPTION_MESSAGE
            is KitchingRuntimeException.FirestoreDocumentNotFoundException ->
                FIRESTORE_DOCUMENT_NOT_FOUND_MESSAGE
            is KitchingRuntimeException.UserNotFoundException ->
                USER_NOT_FOUND_MESSAGE
            is KitchingRuntimeException.UserSaveFailedException ->
                USER_SAVE_FAILED_MESSAGE
            is KitchingRuntimeException.TodoPrepCreateFailedException ->
                TODO_PREP_CREATE_FAILED_MESSAGE
            is KitchingRuntimeException.TodoPrepDeleteFailedException ->
                TODO_PREP_UPDATE_FAILED_MESSAGE
            is KitchingRuntimeException.TodoPrepUpdateFailedException ->
                TODO_PREP_DELETE_FAILED_MESSAGE
        }
    }

    /**
     * 로그용 메시지 생성
     */
    fun getLogMessage(exception: KitchingRuntimeException): String {
        return buildString {
            append("ErrorCode: ${exception.errorCode ?: "UNKNOWN"}")
            append(", Message: ${exception.message}")
            exception.cause?.let {
                append(", Cause: ${it.javaClass.simpleName} - ${it.message}")
            }
        }
    }
}