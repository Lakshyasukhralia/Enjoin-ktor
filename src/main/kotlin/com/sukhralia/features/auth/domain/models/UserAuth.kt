package com.sukhralia.features.auth.domain.models

import com.sukhralia.features.auth.data.models.UserAuthMongo

data class UserAuth(
    val id: String? = null,
    val email: String,
    val password: String,
    val salt: String
)

fun UserAuth.toUserAuthMongo(): UserAuthMongo {
    return UserAuthMongo(
        email = email,
        password = password,
        salt = salt
    )
}
