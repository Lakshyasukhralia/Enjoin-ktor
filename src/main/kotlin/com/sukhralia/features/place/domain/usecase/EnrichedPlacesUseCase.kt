package com.sukhralia.features.place.domain.usecase

import com.sukhralia.features.place.data.models.toPlace
import com.sukhralia.features.place.domain.repository.PlaceRepository
import com.sukhralia.features.place.domain.models.Place
import com.sukhralia.features.place.util.distanceInKm

class EnrichedPlacesUseCase(private val placeRepository: PlaceRepository) {

    suspend operator fun invoke(lat: Double, lon: Double): List<Place> {
        val places = placeRepository.getNearbyPlacesByCoordinates(lat, lon).map { it.toPlace() }
        places.forEach {
            it.distance = distanceInKm(
                lat,
                lon,
                it.coordinates[1],
                it.coordinates[0]
            )
        }
        return places
    }
}