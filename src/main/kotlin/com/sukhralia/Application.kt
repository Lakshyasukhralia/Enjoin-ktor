package com.sukhralia

import com.sukhralia.features.app.rest.setupHealthRoutes
import com.sukhralia.features.auth.data.repository.AuthMongoRepository
import com.sukhralia.features.auth.rest.routes.setupAuthRoutes
import com.sukhralia.features.place.data.repository.PlacesMongoRepository
import com.sukhralia.features.place.rest.routes.setupPlaceRoutes
import com.sukhralia.plugins.configureMonitoring
import com.sukhralia.plugins.configureRouting
import com.sukhralia.plugins.configureSecurity
import com.sukhralia.plugins.configureSerialization
import com.sukhralia.security.hashing.SHA256HashingService
import com.sukhralia.security.token.JwtTokenService
import com.sukhralia.security.token.TokenConfig
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    //Dependencies
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()
    val authRepository = AuthMongoRepository()
    val placeRepository = PlacesMongoRepository()

    //Plugins
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureSecurity(tokenConfig)

    //Routes
    setupHealthRoutes()
    setupAuthRoutes(
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig,
        authRepository = authRepository
    )
    setupPlaceRoutes(placeRepository)
}
