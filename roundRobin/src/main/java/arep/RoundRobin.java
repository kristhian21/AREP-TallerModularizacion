package arep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.PriorityQueue;

public class RoundRobin {
    private final PriorityQueue<String> queue;

    public RoundRobin() {
        queue =  new PriorityQueue<>();
        queue.add("http://localhost:35001");
        queue.add("http://localhost:35002");
        queue.add("http://localhost:35003");
    }

    public String sendRequest(String newWord){
        try {
            URL url = new URL(selectHost()+"/add?word="+newWord);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
        return "";
    }

    private String selectHost() {
        String returnHost = queue.poll();
        queue.add(returnHost);
        return returnHost;
    }
}
