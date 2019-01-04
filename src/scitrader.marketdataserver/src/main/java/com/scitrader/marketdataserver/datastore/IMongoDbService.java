package com.scitrader.marketdataserver.datastore;

import com.mongodb.client.MongoClient;

public interface IMongoDbService {
  MongoClient getMongoClient();
}

