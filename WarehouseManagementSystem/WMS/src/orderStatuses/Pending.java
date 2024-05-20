package orderStatuses;

import modelModule.Order;

/**
 * @author Judy Kalenga
 */

public class Pending extends OrderStatus {

    @Override
    public String report(Order anOrder, double price) {
        // TODO Auto-generated method stub
        this.status = "Order for Product \"" + anOrder.getProductName() + "\" and quantity = " + anOrder.getRequestedQuantity() + " is pending... order exceeds available quantity.";
        return this.status;
    }
    
// private static Pending instance = null;
//    
//    private Pending() {
//        super();
//        this.status = "Order for Product X Quantity Y is pending – order exceeds available quantity.";
//    }
//    
//    private Pending Pending() {
//        return new Pending();
//    }
//    
//    public static Pending getInstance() {
//        if (instance == null) {
//            instance = new Pending();
//        }
//        return instance;
//    }
    
    

}
