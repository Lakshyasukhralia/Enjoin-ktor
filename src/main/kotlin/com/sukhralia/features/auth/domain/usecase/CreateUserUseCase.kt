package com.sukhralia.features.auth.domain.usecase

import com.sukhralia.features.auth.domain.models.UserAuth
import com.sukhralia.features.auth.domain.repository.AuthRepository

class CreateUserUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(userAuth: UserAuth): Boolean = authRepository.insertUser(userAuth)

}