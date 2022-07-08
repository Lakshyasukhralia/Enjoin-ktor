package com.sukhralia.features.place.domain.models

import com.sukhralia.features.place.data.models.Geometry
import com.sukhralia.features.place.data.models.Properties
import com.sukhralia.features.place.rest.models.PlaceResponse

data class Place(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

fun Place.toResponse():PlaceResponse{
    return PlaceResponse(geometry, properties, type)
}