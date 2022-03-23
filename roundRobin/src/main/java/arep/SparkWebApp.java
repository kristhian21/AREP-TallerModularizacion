package arep;

import spark.Filter;
import static spark.Spark.*;

public class SparkWebApp {

    public static void main(String[] args) {
        RoundRobin roundRobin  = new RoundRobin();
        // Set up the port
        port(getPort());
        // GET index
        staticFiles.location("/public");
        init();
        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });
        // POST word
        post("/add", ((request, response) -> {
            response.type("application/json");
            return roundRobin.sendRequest(request.queryParams("word"));
        }));
    }

    /**
     * Set up the port
     * @return the port number
     */
    private static int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}
