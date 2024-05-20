package orderStatuses;

import modelModule.Order;

/**
 * @author Judy Kalenga
 */

public abstract class OrderStatus {
    
    String status;
    
    public abstract String report(Order anOrder, double price);

}
