package com.mycompany.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionManager {
    private static final String URI = "mongodb+srv://dinhhung1508:hung15082004@cluster0.gnfqipw.mongodb.net/";
    private static final String DATABASE_NAME = "OOP_JAVA"; 

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DATABASE_NAME);
    }
}