package com.scitrader.marketdataserver.datastore

import com.mongodb.client.MongoDatabase

interface IMongoDbService {

    val tickDatabase: MongoDatabase

    fun containsCollection(collectionName: String): Boolean
}