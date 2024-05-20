package middlewareModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import modelModule.Order;

public class OrderLogger {
    private String logFilePath;

    public OrderLogger(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    
    public synchronized void logOrder(Order order) {
        try (FileWriter fw = new FileWriter(logFilePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(formatOrderForLog(order));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    private String formatOrderForLog(Order order) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return timestamp + " - Order ID: " + order.getOrderID() + 
                ", Product ID: " + order.getProductName() + 
                ", Quantity: " + order.getRequestedQuantity();
    }

}
