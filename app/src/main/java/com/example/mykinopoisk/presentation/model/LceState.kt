package com.example.mykinopoisk.presentation.model

sealed class LceState<out T> {
    object Loading : LceState<Nothing>()
    data class Success<T>(val data: T) : LceState<T>()
    data class Fail(val error: Throwable) : LceState<Nothing>()
}
