package arep.services;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoDBService {

    private final MongoCollection<Document> collection;

    public MongoDBService() {
        // Set up de connection parameters
        String uri = "mongodb://localhost:27017";
        String dataBaseName = "arep";
        String collectionName = "words";
        // Connect with MongoDB
        MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
        MongoDatabase database = mongoClient.getDatabase(dataBaseName);
        collection = database.getCollection(collectionName);
    }

    /**
     * Insert a given word into de database
     * @param word that will be inserted into the database
     * @return last 10 elements of the database
     */
    public String insertWord(String word){
        String result = "";
        try {
            Document newWord = new Document("data", word).append("date", new Date());
            collection.insertOne(newWord);
            result = getLatElements();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return string with de last words in a JSON format
     */
    private String getLatElements(){
        List<String> wordsList = new ArrayList<>();
        FindIterable<Document> result = collection.find().sort(new Document("_id", -1)).limit(10);
        for (Document document : result){
            wordsList.add(document.toJson());
        }
        return wordsList.toString();
    }

}
