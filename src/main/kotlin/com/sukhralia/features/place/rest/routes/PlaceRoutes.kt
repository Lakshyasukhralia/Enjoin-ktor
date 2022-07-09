package com.sukhralia.features.place.rest.routes

import com.sukhralia.features.place.domain.models.toResponse
import com.sukhralia.features.place.domain.repository.PlaceRepository
import com.sukhralia.features.place.domain.usecase.EnrichedPlacesUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.setupPlaceRoutes(placeRepository: PlaceRepository) {

    routing {

        authenticate {

            route("/place") {

                get("/nearby") {

                    val latitude = call.request.queryParameters["lat"] ?: kotlin.run {
                        call.respond(HttpStatusCode.BadRequest)
                        return@get
                    }

                    val longitude = call.request.queryParameters["lon"] ?: kotlin.run {
                        call.respond(HttpStatusCode.BadRequest)
                        return@get
                    }

                    val enrichedPlacesUseCase = EnrichedPlacesUseCase(placeRepository)
                    val enrichedPlaces = enrichedPlacesUseCase(latitude.toDouble(), longitude.toDouble())

                    call.respond(enrichedPlaces.map { it.toResponse() })
                }

            }
        }

    }
}