package orderStatuses;

import modelModule.Order;

/**
 * @author Judy Kalenga
 */

public class Rejected extends OrderStatus {

    @Override
    public String report(Order anOrder, double price) {
        // TODO Auto-generated method stub
        this.status = "Order exceeds the max quantity set for this product \"" + anOrder.getProductName() + "\" and cannot be processed.";
        return this.status;
    }
    
//    private static Rejected instance = null;
//    
//    private Rejected() {
//        super();
//        this.status = "Order exceeds the max quantity set for this product and cannot be processed.";
//    }
//    
//    private Rejected Rejected() {
//        return new Rejected();
//    }
//    
//    public static Rejected getInstance() {
//        if (instance == null) {
//            instance = new Rejected();
//        }
//        return instance;
//    }
    
    
    

}
