package com.app.codehero.domain.model

/**
FELIPE
 */
data class ResponseDefault<T>(
    val code: Int,
    val status: String,
    val data: T
)
