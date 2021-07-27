package dev.eighteentech.test.model

sealed class Response<out T>{
    object Uninitialized: Response<Nothing>()
    object Loading: Response<Nothing>()
    data class Success<out T>(val data:T): Response<T>()
    data class Fail(val exception: Exception): Response<Nothing>()
}