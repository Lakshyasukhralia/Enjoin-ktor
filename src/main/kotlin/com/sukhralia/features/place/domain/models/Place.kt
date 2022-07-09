package com.sukhralia.features.place.domain.models

import com.sukhralia.features.place.rest.models.PlaceResponse

data class Place(
    val name: String?,
    val address: String?,
    val coordinates: List<Double>,
    val categories: List<String>?,
    var distance: Double? = null
)

fun Place.toResponse(): PlaceResponse {
    return PlaceResponse(
        name = name,
        address = address,
        coordinates = coordinates,
        categories = categories,
        distance = distance
    )
}