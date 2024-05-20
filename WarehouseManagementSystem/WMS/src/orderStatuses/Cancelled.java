package orderStatuses;

import modelModule.Order;

/**
 * @author Judy Kalenga
 */

public class Cancelled extends OrderStatus {

    @Override
    public String report(Order anOrder, double price) {
        // TODO Auto-generated method stub
        this.status = "Product \"" + anOrder.getProductName() + "\" not found in product database. Order cancelled.";
        return this.status;
        
    }
    
//    private static Cancelled instance = null;
//    
//    private Cancelled() {
//        super();
//        this.status = "Product ID not found in product database. Order cancelled.";
//    }
//    
//    private Cancelled Cancelled() {
//        return new Cancelled();
//    }
//    
//    public static Cancelled getInstance() {
//        if (instance == null) {
//            instance = new Cancelled();
//        }
//        return instance;
//    }

}
