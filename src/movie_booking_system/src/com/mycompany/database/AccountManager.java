package com.mycompany.database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;


public class AccountManager {
    private final MongoCollection<Document> collection;

    public AccountManager() {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("accounts");
    }

   
    public boolean createAccount(String username, String password, String email) {
        if (check_exit(username)) {
            System.out.println("Account existed: " + username);
            return false;
        }
        Document account = new Document("username", username)
                            .append("password", password)
                            .append("email", email)
                            .append("role", "user");
        collection.insertOne(account);
        System.out.println("Account created: " + username);
        return true;
    }

    public void deleteAccount(String username) {
        Document query = new Document("username", username);
        collection.deleteOne(query);
        System.out.println("Account deleted: " + username);
    }

    public boolean check_exit(String username) {
        Document query = new Document("username", username);
        Document account = collection.find(query).first();
        if (account == null) {
            System.out.println("Account not found: " + username);
            return false;
        } else {
            return true;
        }
    }


    public boolean check_correct(String username, String password) {
        Document query = new Document("username", username);
        Document account = collection.find(query).first();
        if (account == null) {
            System.out.println("Account not found: " + username);
            return false;
        } else {
            if (account.getString("password").equals(password)) {
                System.out.println("Login successful: " + username);
                return true;

            } else {
                System.out.println("Incorrect password: " + username);
                return false;
            }
        }
        
    }

    public String getEmail(String username) {
        Document query = new Document("username", username);
        Document account = collection.find(query).first();
        if (account == null) {
            System.out.println("Account not found: " + username);
            return null;
        } else {
            return account.getString("email");
        }
    }
    public boolean check_admin(String username) {
        Document query = new Document("username", username);
        Document account = collection.find(query).first();

        if (account == null) {
            System.out.println("Account not found: " + username);
            return false;
        } else {
            String role = account.getString("role");
            if ("admin".equals(role)) {
                System.out.println("Admin account: " + username);
                return true;
            } else {
                System.out.println("Not an admin account: " + username);
                return false;
            }
        }
    }

    public List<Document> getAllAccounts() {
        List<Document> accounts = new ArrayList<>();
        collection.find().into(accounts);
        return accounts;
    }
    
    public String getEmailByUsername(String username) {
        Document query = new Document("username", username);
        Document account = collection.find(query).first();
        if (account == null) {
            System.out.println("Account not found: " + username);
            return null;
        } else {
            return account.getString("email");
        }
    }

}