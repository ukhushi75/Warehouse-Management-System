package middlewareModule;

import java.io.IOException;
import java.text.SimpleDateFormat;

import controllerModule.IOrderProcessing;
import controllerModule.OrderProcessingServer;
import modelModule.Order;

public class MiddlewareServer implements IMiddlewareManagement {

    private IOrderProcessing controller = OrderProcessingServer.getInstance();
    private OrderLogger logger;
    private OrderQueue orderQueue;

    // singleton design pattern
    private static MiddlewareServer instance = null;

    private MiddlewareServer() {}

    public static MiddlewareServer getInstance() {
        if (instance == null) {
            instance = new MiddlewareServer();
        }
        return instance;
    }

    @Override
    public void initLogger(String logFilePath) {
        this.logger = new OrderLogger(logFilePath);
    }

    @Override
    public String startOrderProcessing(Order order) throws IOException {
        if (logger != null) {
            logger.logOrder(order);
        }
        System.out.println("Order logged");

        if (orderQueue != null) {
            orderQueue.enqueue(order);
        }
        System.out.println("Order queued");

        String response = controller.processOrder(order.getProductName(), order.getRequestedQuantity(), order.getTimeStamp());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(order.getTimeStamp());

        return response;
        // "Order received for " + order.getProductName() + " with quantity " + order.getRequestedQuantity() + " on " + formattedDate;
    }
}
