package modelModule;

import java.util.Date;

/**
 * @author Judy Kalenga
 */

public class Order {
    
    private String orderID;
    private String productName;
    private String productID;
    private int requestedQuantity;
    private Date timeStamp;
    
//    Order(String OID, String PID, int quantity, Date time) {
//         orderID = OID;
//         productID = PID;
//         requestedQuantity = quantity;
//         timeStamp = time; 
//    }
     
    public Order(String name, int quantity, Date time) {
        productName = name;
        requestedQuantity = quantity;
        timeStamp = time; 
    }
     

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }


     
     

}
