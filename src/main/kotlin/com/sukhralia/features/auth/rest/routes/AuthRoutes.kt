package com.sukhralia.features.auth.rest.routes

import com.sukhralia.features.auth.domain.models.UserAuth
import com.sukhralia.features.auth.domain.repository.AuthRepository
import com.sukhralia.features.auth.domain.usecase.CreateUserUseCase
import com.sukhralia.features.auth.domain.usecase.GetUserByEmailUseCase
import com.sukhralia.features.auth.rest.models.AuthRequest
import com.sukhralia.features.auth.rest.models.AuthResponse
import com.sukhralia.security.hashing.HashingService
import com.sukhralia.security.hashing.SaltedHash
import com.sukhralia.security.token.TokenClaim
import com.sukhralia.security.token.TokenConfig
import com.sukhralia.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils
import org.koin.ktor.ext.inject

fun Application.setupAuthRoutes(
    hashingService: HashingService, tokenService: TokenService,
    tokenConfig: TokenConfig
) {

    val authRepository: AuthRepository by inject()

    routing {

        route("/auth") {

            post("/signup") {
                val request = call.receiveOrNull<AuthRequest>() ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val areFieldsBlank = request.email.isBlank() || request.password.isBlank()
                val isPwTooShort = request.password.length < 8
                if (areFieldsBlank || isPwTooShort) {
                    call.respond(HttpStatusCode.Conflict)
                    return@post
                }

                val saltedHash = hashingService.generateSaltedHash(request.password)
                val userAuth = UserAuth(
                    email = request.email,
                    password = saltedHash.hash,
                    salt = saltedHash.salt
                )

                val createUserUseCase = CreateUserUseCase(authRepository)

                val wasAcknowledged = createUserUseCase(userAuth)
                if (!wasAcknowledged) {
                    call.respond(HttpStatusCode.Conflict)
                    return@post
                }

                call.respond(HttpStatusCode.OK)
            }

            post("/signin") {
                val request = call.receiveOrNull<AuthRequest>() ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val getUserByEmailUseCase = GetUserByEmailUseCase(authRepository)

                val user = getUserByEmailUseCase(request.email)
                if (user == null) {
                    call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                    return@post
                }

                val isValidPassword = hashingService.verify(
                    value = request.password,
                    saltedHash = SaltedHash(
                        hash = user.password,
                        salt = user.salt
                    )
                )
                if (!isValidPassword) {
                    println("Entered hash: ${DigestUtils.sha256Hex("${user.salt}${request.password}")}, Hashed PW: ${user.password}")
                    call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                    return@post
                }

                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.id.toString()
                    )
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthResponse(
                        token = token
                    )
                )
            }

            authenticate {
                get("/secret") {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.getClaim("userId", String::class)
                    call.respond(HttpStatusCode.OK, "Your userId is $userId")
                }
            }

        }

    }

}