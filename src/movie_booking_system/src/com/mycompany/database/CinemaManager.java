
package com.mycompany.database;
import com.mycompany.model.Cinema;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class CinemaManager {
    private final MongoCollection<Document> collection;
    public CinemaManager()
    {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("cinema");
    }
    public void addCinema(String name, List<String>showHours)
    {
        Document cinema = new Document("cinema", name)
                .append("time", showHours);
        collection.insertOne(cinema);
    }
    public void updateCinema(String name, List<String>showHours)
    {
        Document cinema = new Document("cinema", name)
                .append("time", showHours);
        collection.replaceOne(new Document("cinema", name), cinema);
    }
    public void deleteCinema(String name)
    {
        collection.deleteOne(new Document("cinema", name));
    }
    public List<Cinema> getAllCinemas() {
        List<Cinema> Cinemas = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Cinema cinema = new Cinema(
                    doc.getString("cinema"),
                    (List<String>) doc.get("time")   
                );
                Cinemas.add(cinema);
            }
        }
        return Cinemas;
    }
}