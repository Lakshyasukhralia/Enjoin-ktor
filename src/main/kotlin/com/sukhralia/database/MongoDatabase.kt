package com.sukhralia.database

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object MongoDatabase {

    var database: CoroutineDatabase

    init {
        val mongoPw = System.getenv("ENJOIN_MONGO_PW")
        val client =
            KMongo.createClient("mongodb+srv://lakshyasukhralia:${mongoPw}@cluster0.3ykmu.mongodb.net/?retryWrites=true&w=majority").coroutine
        database = client.getDatabase("enjoin_db")
    }

}