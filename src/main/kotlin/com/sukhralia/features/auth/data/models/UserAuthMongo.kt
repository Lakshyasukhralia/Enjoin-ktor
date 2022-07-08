package com.sukhralia.features.auth.data.models

import com.sukhralia.features.auth.domain.models.UserAuth
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class UserAuthMongo(
    @BsonId
    val id: Id<UserAuthMongo>? = null,
    val email: String,
    val password: String,
    val salt: String
)

fun UserAuthMongo.toUserAuth(): UserAuth {
    return UserAuth(
        id = id.toString(),
        email = email,
        password = password,
        salt = salt
    )
}