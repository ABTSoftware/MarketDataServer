package com.scitrader.marketdataserver.datastore;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface IMongoDbService {

  abstract MongoDatabase getTickDatabase();

  boolean containsCollection(String collectionName);
}

