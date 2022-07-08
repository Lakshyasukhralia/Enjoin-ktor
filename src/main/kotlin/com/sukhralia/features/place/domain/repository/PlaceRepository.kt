package com.sukhralia.features.place.domain.repository

import com.sukhralia.features.place.domain.models.Place

interface PlaceRepository {
    suspend fun getNearbyPlacesByCoordinates(lat: Double, lon: Double): List<Place>?
}