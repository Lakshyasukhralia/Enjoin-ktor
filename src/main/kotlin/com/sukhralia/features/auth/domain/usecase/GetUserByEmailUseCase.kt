package com.sukhralia.features.auth.domain.usecase

import com.sukhralia.features.auth.data.models.toUserAuth
import com.sukhralia.features.auth.domain.models.UserAuth
import com.sukhralia.features.auth.domain.repository.AuthRepository

class GetUserByEmailUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String): UserAuth? = authRepository.getUserByEmail(email)?.toUserAuth()

}