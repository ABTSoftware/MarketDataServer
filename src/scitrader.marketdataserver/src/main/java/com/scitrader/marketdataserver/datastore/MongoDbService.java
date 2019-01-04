package com.scitrader.marketdataserver.datastore;

import com.google.inject.Inject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;

public class MongoDbService implements IMongoDbService{

  private final String Url = "mongodb://localhost:27017";

  @Inject
  public MongoDbService(){

  }

  @Override
  public MongoClient getMongoClient(){
    MongoClientSettings settings = MongoClientSettings
            .builder()
            .applyConnectionString(new ConnectionString(Url))
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    return mongoClient;
  }
}
