package com.sukhralia.plugins

import com.sukhralia.features.auth.data.repository.AuthMongoRepository
import com.sukhralia.features.auth.domain.repository.AuthRepository
import com.sukhralia.features.place.data.repository.PlaceMongoRepository
import com.sukhralia.features.place.domain.repository.PlaceRepository
import io.ktor.server.application.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDependencyInjection() {

    val placeModule = module {
        singleOf(::PlaceMongoRepository) { bind<PlaceRepository>() }
    }

    val authModule = module {
        singleOf(::AuthMongoRepository) { bind<AuthRepository>() }
    }

    install(Koin) {
        modules(placeModule,authModule)
    }
}