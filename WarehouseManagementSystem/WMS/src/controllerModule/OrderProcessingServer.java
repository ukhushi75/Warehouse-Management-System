package controllerModule;

import java.io.IOException;
import java.util.Date;

import modelModule.IModelManagement;
import modelModule.LastOrder;
import modelModule.ModelDispatcher;
import modelModule.Order;
import modelModule.Product;
import orderStatuses.OrderStatus;
import orderStatuses.Pending;

/**
 * @author Judy Kalenga
 */

public class OrderProcessingServer implements IOrderProcessing {
    
    private IModelManagement model = ModelDispatcher.getInstance();
    
    // singleton
    private static OrderProcessingServer instance = null;
    
    private static OrderProcessingServer OrderProcessingServer() {
        return new OrderProcessingServer();
    }
    
    public static OrderProcessingServer getInstance() {
        if (instance == null) {
            instance = new OrderProcessingServer();
        }
        return instance;
    }
         
    @Override
    public String processOrder(String productName, int requestedQuantity, Date timeStamp) throws IOException  {
        
        // facade design pattern
        
        // step 1: create order object and add to order repository
        Order anOrder = model.createOrder(productName, requestedQuantity, timeStamp);
        
        // step 2: fulfill order
        OrderStatus status = model.fulfillOrder(anOrder);
                
        // step 3: check if order status is pending
        if (status instanceof Pending) {
            System.out.println(status.report(anOrder, 0.0));
            System.out.println("Initiating restock...");
            RestockingServer.getInstance().performRestock(productName);
            
            // fulfill pending order
            status = model.fulfillOrder(anOrder);
        }
        
        // step 4: calculate cost of the order and potential discount
        Product aProduct = model.getProductInfo(anOrder.getProductName());
        
        IPrice pricingServer = new PricingServer();
        
        double price = 0.0;
        
        if (aProduct != null) {
            price = pricingServer.calculatePrice(anOrder, aProduct);
        } else {
            System.out.println("aProduct is null");
        }
        
        IDiscount discountStrategy = null;
        
        // factory design pattern
        if (anOrder.getRequestedQuantity() > 20) {
            discountStrategy = new DiscountStrategy1();   
            System.out.println("Discount Strategy #1 activated...");
        } else if (price > 100) {
            discountStrategy = new DiscountStrategy2();
            System.out.println("Discount Strategy #2 activated...");
        }
        
        if (discountStrategy != null) {
            // strategy design pattern
            price = discountStrategy.applyDiscount(price);
            System.out.println("Discount successfully applied.");
        }

        // step 5: set last order to current order for warehouse server ui
        LastOrder theLastOrder = LastOrder.getInstance().findLastOrder();
        theLastOrder.setProductName(productName);
        theLastOrder.setQuantity(requestedQuantity);
        theLastOrder.setDate(java.time.LocalDateTime.now());
        
        
        // step 6: respond to client or display status to server UI
        System.out.println(status.report(anOrder, price));
        return status.report(anOrder, price);

       
        

    }  

    public static void main(String[] args) throws IOException {
        
        IOrderProcessing orderProcessor = OrderProcessingServer.getInstance();
        orderProcessor.processOrder("BigFoot", 5, new Date());
        
    }

}
