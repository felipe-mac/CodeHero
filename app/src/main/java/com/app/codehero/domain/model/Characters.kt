package com.app.codehero.domain.model

/**
FELIPE
 */
data class Characters(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Character>
)
