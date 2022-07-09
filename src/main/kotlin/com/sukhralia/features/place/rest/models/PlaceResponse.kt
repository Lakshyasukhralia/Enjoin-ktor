package com.sukhralia.features.place.rest.models

import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    val name: String?,
    val address: String?,
    val coordinates: List<Double>,
    val categories: List<String>?,
    var distance: Double?
)