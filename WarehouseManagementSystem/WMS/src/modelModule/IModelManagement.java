package modelModule;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import orderStatuses.OrderStatus;

/**
 * @author Judy Kalenga
 */

public interface IModelManagement {
    
    public Order createOrder(String productName, int requestedQuantity, Date timeStamp);
    public OrderStatus fulfillOrder(Order anOrder) throws IOException;  
    public Product getProductInfo(String productID) throws IOException;
    public List<Product> getProducts() throws IOException;
    public void updateDatabase(String productID, int newQuantity) throws IOException;
    public void notifyViewers();

}
