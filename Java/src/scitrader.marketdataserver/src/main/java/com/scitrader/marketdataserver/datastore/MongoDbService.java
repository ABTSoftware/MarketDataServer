package com.scitrader.marketdataserver.datastore;

import com.google.inject.Inject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.internal.MongoClientImpl;

import java.util.Set;

public class MongoDbService implements IMongoDbService{

  private final String Url = "mongodb://localhost:27017";
  private final String DatabaseName = "com_scitrader_marketdataserver";

  @Inject
  public MongoDbService(){

  }

  @Override
  public MongoDatabase getTickDatabase() {
    return getMongoClient().getDatabase(DatabaseName);
  }

  @Override
  public boolean containsCollection(final String collectionName) {
    MongoIterable<String> collectionNames = this.getTickDatabase().listCollectionNames();
    for (final String name : collectionNames) {
      if (name.equalsIgnoreCase(collectionName)) {
        return true;
      }
    }
    return false;
  }

  private MongoClient getMongoClient(){
    MongoClientSettings settings = MongoClientSettings
            .builder()
            .applyConnectionString(new ConnectionString(Url))
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    return mongoClient;
  }
}
