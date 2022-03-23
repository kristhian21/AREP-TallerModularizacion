package arep.controllers;

import arep.services.MongoDBService;
import static spark.Spark.*;

public class LogService {

    public static void main(String[] args) {
        // Set up the port
        port(getPort());
        System.out.println("Escuchando peticiones...");
        // Connect with MongoDB
        MongoDBService service = new MongoDBService();
        // POST word
        post("/add", (request, response) -> {
            String newWord = request.queryParams("word");
            System.out.println("New word: " + newWord);
            String result = service.insertWord(newWord);
            System.out.println(result);
            return result;
        });
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}
