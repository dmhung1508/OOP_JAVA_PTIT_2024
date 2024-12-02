
package com.mycompany.database;
import com.mycompany.model.Movie;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {
    private final MongoCollection<Document> collection;

    public MovieDatabase() {
        MongoDatabase database = ConnectionManager.getDatabase();
        collection = database.getCollection("movies");
    }

    public void addMovie(String id, String title, List<String> cinemas, List<String> showDates, String genre, String imagePath, String director, String description, int duration, String releaseDate, String mainActors) {
        Document movie = new Document("id", id)
                    .append("title", title)
                    .append("cinemas", cinemas)
                    .append("showDates", showDates)
                    .append("genre", genre)
                    .append("imagePath", imagePath)
                    .append("director", director)
                    .append("description", description)
                    .append("duration", duration)
                    .append("releaseDate", releaseDate)
                    .append("mainActors", mainActors);
        collection.insertOne(movie);
//        System.out.println("added Movie");
    }

    public void updateMovie(String id, String newTitle, List<String> newCinemas, List<String> newShowDates, String newGenre, String newImagePath, String newDirector, String newDescription, int newDuration, String newReleaseDate, String newMainActors) {
        Document query = new Document("id", id);
        Document update = new Document("$set", new Document("title", newTitle)
                            .append("cinemas", newCinemas)
                            .append("showDates", newShowDates)
                            .append("genre", newGenre)
                            .append("imagePath", newImagePath)
                            .append("director", newDirector)
                            .append("description", newDescription)
                            .append("duration", newDuration)
                            .append("releaseDate", newReleaseDate)
                            .append("mainActors", newMainActors));
        collection.updateOne(query, update);
    }

    public void deleteMovie(String id) {
        Document query = new Document("id", id);
        collection.deleteOne(query);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Movie movie = new Movie(
                    doc.getString("id"),
                    doc.getString("title"),
                    (List<String>) doc.get("cinemas"),
                    (List<String>) doc.get("showDates"),
                    doc.getString("genre"),
                    doc.getString("imagePath"),
                    doc.getString("director"),
                    doc.getString("description"),
                    doc.getInteger("duration"),
                    doc.getString("releaseDate"),
                    doc.getString("mainActors")
                );
                movies.add(movie);
            }
        }
        return movies;
    }
}