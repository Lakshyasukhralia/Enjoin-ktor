package com.sukhralia.features.auth.rest.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    var email: String,
    var password: String,
)
