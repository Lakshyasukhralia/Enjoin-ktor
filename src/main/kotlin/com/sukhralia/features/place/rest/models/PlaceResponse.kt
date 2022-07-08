package com.sukhralia.features.place.rest.models

import com.sukhralia.features.place.data.models.Geometry
import com.sukhralia.features.place.data.models.Properties
import kotlinx.serialization.Serializable

@Serializable
data class PlaceResponse(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)