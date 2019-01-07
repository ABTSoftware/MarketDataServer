package com.scitrader.marketdataserver.datastore

import com.google.inject.Inject
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

class MongoDbService @Inject
constructor() : IMongoDbService {

    private val Url = "mongodb://localhost:27017"
    private val DatabaseName = "com_scitrader_marketdataserver"

    override val tickDatabase: MongoDatabase
        get() = mongoClient.getDatabase(DatabaseName)

    private val mongoClient: MongoClient
        get() {
            val settings = MongoClientSettings
                    .builder()
                    .applyConnectionString(ConnectionString(Url))
                    .build()
            return MongoClients.create(settings)
        }

    override fun containsCollection(collectionName: String): Boolean {
        val collectionNames = this.tickDatabase.listCollectionNames()
        for (name in collectionNames) {
            if (name.equals(collectionName, ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}