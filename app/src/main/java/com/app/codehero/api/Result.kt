package com.app.codehero.api

/**
FELIPE
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T?): Result<T?>()
    data class Failure(val statusCode: Int, val exception: Exception): Result<Nothing>()
    data class Error(val exception: Exception): Result<Nothing>()
}
