package modelModule;

import java.time.LocalDateTime;


public class LastOrder {
    
    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        LastOrder.productName = productName;
    }

    public static int getQuantity() {
        return quantity;
    }

    public static void setQuantity(int quantity) {
        LastOrder.quantity = quantity;
    }

    public static LocalDateTime getDate() {
        return date;
    }

    public static void setDate(LocalDateTime date) {
        LastOrder.date = date;
    }

    private static LastOrder instance = null;

    private static String productName;
    private static int quantity;
    private static LocalDateTime date;

    public static LastOrder getInstance() {
        if (instance == null)
            instance = new LastOrder();

        return instance;
    }

    private LastOrder() {
        //findAvailableProductsAndQuantities();
    }

    public LastOrder findLastOrder() {

        // Here we query the Product DB and we get the product names or the product IDs
        
//        productName = "Product1";
//        quantity = 22;
//        date = java.time.LocalDateTime.now();       

        return this;




    }

}
