package clientModule;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClientCaller {
    
    public String placeOrder(String productName, String productQuantity) {

        String urlString = String.format("http://localhost:8000/PlaceOrder?productName=%s&productQuantity=%s", productName, productQuantity);
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                String inline = "";
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
            
                return inline;
            }

        } catch (IOException e) {
            System.out.println("Something went wrong with the API call.");
            e.printStackTrace();
        }
        return null;
    }

}
