
package com.mycompany.database;
import com.mycompany.model.Transaction;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private final MongoCollection<Document> collection;

    public TransactionHistory() {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("transactions");
    }

    public void addTransaction(Transaction transaction) {
        Document doc = new Document("username", transaction.getUsername())
                .append("amount", transaction.getAmount())
                .append("timestamp", transaction.getTimestamp())
                .append("seats", transaction.getSeats())
                .append("movieTitle", transaction.getMovieTitle())
                .append("movieDate", transaction.getMovieDate())
                .append("isPaid", transaction.getIsPaid());
        collection.insertOne(doc);
        System.out.println("Inserted transaction: " + doc);
    }

    public void insertTransaction(String username, String amount, String timestamp, String seats, String movieTitle, String movieDate, Boolean isPaid) {
        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String formattedTimestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(Long.parseLong(currentTimestamp)));
        Transaction transaction = new Transaction(username, amount, formattedTimestamp, seats, movieTitle, movieDate, isPaid);
        
        addTransaction(transaction);
    }

    public void updatePaymentStatus(String username, Boolean isPaid) {
        Document query = new Document("username", username);
        Document update = new Document("$set", new Document("isPaid", isPaid));
        collection.updateOne(query, update);
    }

    public void generateFakeTransactions(int numTransactions) {
        for (int i = 0; i < numTransactions; i++) {
            String username = "user" + i;
            String amount = "10";
            String timestamp = "2021-10-01 12:00:00";
            String seats = "A1, A2";
            String movieTitle = "Movie " + i;
            String movieDate = "2021-10-01";
            Boolean isPaid = true;
            insertTransaction(username, amount, timestamp, seats, movieTitle, movieDate, isPaid);
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (Document doc : collection.find()) {
            Transaction transaction = new Transaction(
                    doc.getString("username"),
                    doc.getString("amount"),
                    doc.getString("timestamp"),
                    doc.getString("seats"),
                    doc.getString("movieTitle"),
                    doc.getString("movieDate"),
                    doc.getBoolean("isPaid")
            );
            transactions.add(transaction);
            System.out.println("Transaction: " + transaction);
        }
        return transactions;
    }
}