package com.sukhralia.features.auth.rest.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
