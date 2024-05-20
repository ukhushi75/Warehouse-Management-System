package frontEndModule;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import controllerModule.IOrderProcessing;
import controllerModule.OrderProcessingServer;
import middlewareModule.MiddlewareServer;
import modelModule.Order;
import viewerModule.WarehouseServerUI;

public class FrontEndServer {
    
    // reference to http server
    HttpServer server;
    
    // reference to middleware server
    MiddlewareServer middleware = MiddlewareServer.getInstance();
    
    // singleton design pattern
    private static FrontEndServer instance = null;
    
    private FrontEndServer FrontEndServer() {
        return new FrontEndServer();
    }
    
    private FrontEndServer() {
        middleware.initLogger("C:/Users/Owner/Desktop/OrderLogFile.txt");
    }
    
    public static FrontEndServer getInstance() {
        if (instance == null) {
            instance = new FrontEndServer();
        }
        return instance;
    }
    
    public void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/PlaceOrder", new PlaceOrder());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
    }
    
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
      }
    
    static class PlaceOrder implements HttpHandler {
        // reference to the warehouse server ui
        private JFrame warehouseUI = WarehouseServerUI.getInstance();
        
        // reference to middleware server
        MiddlewareServer middleware = MiddlewareServer.getInstance();
//        IOrderProcessing orderProcessor = OrderProcessingServer.getInstance();
        
        
        
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> parms = queryToMap(exchange.getRequestURI().getQuery());
            
            System.out.println("Processing new order...");
            
            String productName = parms.get("productName");
            int productQuantity = Integer.parseInt(parms.get("productQuantity"));
            Order order = new Order(productName, productQuantity, new Date());

            String response = middleware.startOrderProcessing(order);
            
            // pass order information to order processing server to process the order
//            System.out.println("productName=" + parms.get("productName") + " productQuantity=" + Integer.parseInt(parms.get("productQuantity")));
            
            //uncomment me when needed
//            String response = orderProcessor.processOrder(parms.get("productName"), Integer.parseInt(parms.get("productQuantity")), new Date());

//          String response = "order received..." + "product = \"" + parms.get("productName") + "\" and quantity = " + parms.get("productQuantity");
//          exchange.sendResponseHeaders(200, response.length());
//          OutputStream os = exchange.getResponseBody();
//          os.write(response.getBytes());
//          try {
//              wait(1000);
//          } catch (InterruptedException e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//          }
//          
//          os.close();
            
//            String response = "order received... product = \"" + parms.get("productName") + "\" and quantity = " + parms.get("productQuantity");
            byte[] responseBytes = response.getBytes();

            // Set Content-Length header
            exchange.sendResponseHeaders(200, responseBytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
            
//          
//       
            
          
          
        }
    }
    
}
