package com.sukhralia.features.place.data.models

import com.sukhralia.features.place.domain.models.Place
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class PlaceMongo(
    @BsonId
    val id: Id<PlaceMongo>? = null,
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

@Serializable
data class Geometry(
    val coordinates: List<Double>,
    val type: String
)

@Serializable
data class Properties(
    val address_line1: String?,
    val address_line2: String?,
    val categories: List<String>?,
    val city: String?,
    val commercial: String?,
    val country: String?,
    val country_code: String?,
    val datasource: Datasource?,
    val details: List<String>?,
    val district: String?,
    val formatted: String?,
    val housenumber: String?,
    val lat: Double?,
    val lon: Double?,
    val name: String?,
    val neighbourhood: String?,
    val place_id: String?,
    val postcode: String?,
    val state: String?,
    val street: String?,
    val suburb: String?
)

@Serializable
data class Datasource(
    val attribution: String?,
    val license: String?,
    val sourcename: String?,
    val url: String?
)

fun PlaceMongo.toPlace(): Place {
    return Place(
        geometry, properties, type
    )
}