package com.sukhralia.features.place.data.repository

import com.sukhralia.database.MongoDatabase
import com.sukhralia.features.place.data.models.PlaceMongo
import com.sukhralia.features.place.domain.repository.PlaceRepository

class PlaceMongoRepository : PlaceRepository {

    private val collection = MongoDatabase.database.getCollection<PlaceMongo>()

    override suspend fun getNearbyPlacesByCoordinates(lat: Double, lon: Double): List<PlaceMongo> {

        //Todo:- Need better way to handle this query using kMongo extensions
        val query = "{\n" +
                "\"geometry\": {\n" +
                "\$near: {\n" +
                "\$geometry: {\n" +
                "type: \"Point\" ,\n" +
                "coordinates: [$lon,$lat]\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "}"

        val placesMongo = collection.find(query).limit(15).toList()
        return placesMongo.filter { it.properties.name != null }
    }
}
