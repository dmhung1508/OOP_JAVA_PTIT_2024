
package com.mycompany.database;
import com.mycompany.model.Seats;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SeatsDatabase {
    private final MongoCollection<Document> collection;

    public SeatsDatabase() {
        MongoDatabase database = ConnectionManager.getDatabase();
        this.collection = database.getCollection("seats");
    }
    private void initSeats(String name){
        Map<String, Object> seatMap = new HashMap<>();
        char[] row = {'G', 'H'};
        for (int i = 1; i <= 12; i++) {
            for (char c : row) {
                String seat = c + String.format("%02d", i);
                seatMap.put(seat, "available");
            }
        }
        Document document = new Document("name", name)
                .append("seats", seatMap);
        collection.insertOne(document);

    }
    public void addSeats(String name) {
        Document query = new Document("name", name);
        if (collection.find(query).first() == null) {
            initSeats(name);
        }
    }
    public void updateSeatStatus(String name, String seat) {
        String status = "booked";
        Document query = new Document("name", name);
        Document update = new Document("$set", new Document("seats." + seat, status));
        collection.updateOne(query, update);
    }
    public List<Seats> getSeats(String name) {
        List<Seats> seats = new ArrayList<>();
        Document query = new Document("name", name);
        Document document = collection.find(query).first();
        if (document != null) {
            @SuppressWarnings("unchecked")
            Map<String, String> seatMap = (Map<String, String>) document.get("seats");
            for (Map.Entry<String, String> entry : seatMap.entrySet()) {
                seats.add(new Seats(entry.getKey(), entry.getValue()));
            }
        }
        return seats;
    }
}
