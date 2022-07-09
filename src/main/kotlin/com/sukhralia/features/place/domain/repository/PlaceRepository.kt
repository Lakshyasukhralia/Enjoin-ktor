package com.sukhralia.features.place.domain.repository

import com.sukhralia.features.place.data.models.PlaceMongo

interface PlaceRepository {
    suspend fun getNearbyPlacesByCoordinates(lat: Double, lon: Double): List<PlaceMongo>
}