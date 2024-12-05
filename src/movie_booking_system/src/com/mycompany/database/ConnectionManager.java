package com.mycompany.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {
    private static final String URI = "mongodb+srv://dinhhung1508:hung15082004@cluster0.gnfqipw.mongodb.net/";
    private static final String DATABASE_NAME = "OOP_JAVA"; 

    static {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DATABASE_NAME);
    }
}
