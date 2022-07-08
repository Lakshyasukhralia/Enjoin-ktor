package com.sukhralia.features.auth.domain.repository

import com.sukhralia.features.auth.domain.models.UserAuth

interface AuthRepository {
    suspend fun insertUser(userAuth: UserAuth): Boolean
    suspend fun getUserByEmail(email: String): UserAuth?
}