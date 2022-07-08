package com.sukhralia.features.auth.data.repository

import com.mongodb.MongoWriteException
import com.sukhralia.database.MongoDatabase
import com.sukhralia.features.auth.data.models.UserAuthMongo
import com.sukhralia.features.auth.data.models.toUserAuth
import com.sukhralia.features.auth.domain.models.UserAuth
import com.sukhralia.features.auth.domain.models.toUserAuthMongo
import com.sukhralia.features.auth.domain.repository.AuthRepository
import org.litote.kmongo.eq

class AuthMongoRepository() : AuthRepository {

    private val collection = MongoDatabase.database.getCollection<UserAuthMongo>()

    override suspend fun insertUser(userAuth: UserAuth): Boolean {
        val userMongo = userAuth.toUserAuthMongo()
        return try {
            collection.insertOne(userMongo).wasAcknowledged()
        } catch (e: MongoWriteException) {
            false
        }
    }

    override suspend fun getUserByEmail(email: String): UserAuth? {
        val userAuthMongo = collection.findOne(UserAuthMongo::email eq email)
        return userAuthMongo?.toUserAuth()
    }

}