package com.kitching.domain.util

data class UiState<T>(
    val loading: Boolean = false,
    val data: T? = null,
    val error: String? = null,
) {
    val isSuccess: Boolean
        get() = !loading && data != null && error == null

    val isLoading: Boolean
        get() = loading

    val isError: Boolean
        get() = !loading && error != null

    fun toLoading(): UiState<T> = this.copy(
        loading = true,
        data = null,
        error = null
    )

    fun toSuccess(data: T): UiState<T> = this.copy(
        loading = false,
        data = data,
        error = null
    )

    fun toError(message: String): UiState<T> = this.copy(
        loading = false,
        data = null,
        error = message
    )
}