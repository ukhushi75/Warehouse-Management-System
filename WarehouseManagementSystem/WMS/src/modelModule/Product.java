package modelModule;

/**
 * @author Judy Kalenga
 */

public class Product {
    
    private String productID;
    private String productName;
    private int availableQuantity;
    private int maxStockQuantity;
    private double unitPrice;
    private int restockSchedule;
    
    Product(String ID, String name, int quantity, int maxQuantity, double price, int units) {
        productID = ID;
        productName = name;
        availableQuantity = quantity;
        maxStockQuantity = maxQuantity;
        unitPrice = price;
        restockSchedule = units;
    }
    
    public int getRestockSchedule() {
        return restockSchedule;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getMaxStockQuantity() {
        return maxStockQuantity;
    }

    public void setMaxStockQuantity(int maxStockQuantity) {
        this.maxStockQuantity = maxStockQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
    

}
