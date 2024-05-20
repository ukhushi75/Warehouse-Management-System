package controllerModule;

import java.io.IOException;

import modelModule.ModelDispatcher;
import modelModule.Product;

public class RestockingServer implements IRestock {
    
    // singleton design pattern
    private static RestockingServer instance = null;
    
    private RestockingServer() {}
    
    public static RestockingServer getInstance() {
        if (instance == null) {
            instance = new RestockingServer();
        }
        return instance;
    }

    @Override
    public void performRestock(String productName) throws IOException {
//        Product aProduct = ModelDispatcher.getInstance().getProductInfo(productName);
//        if (aProduct == null) {
//            System.out.println("Product not found in database: " + productName);
//            return;
//        }
//
//        int currentStock = aProduct.getAvailableQuantity();
//        int maxStockQuantity = aProduct.getMaxStockQuantity();
//        int restockAmount = maxStockQuantity - currentStock;
//
//        if (restockAmount > 0) {
//            ModelDispatcher.getInstance().updateDatabase(aProduct.getProductID(), restockAmount+currentStock);
//            
//            ModelDispatcher.getInstance().notifyViewers(aProduct.getProductName(), restockAmount+currentStock);
//            
//            System.out.println("Restocking completed for product: " + productName);
//            
//        } else {
//            System.out.println("No restocking needed for product: " + productName);
//        }
        
        Product product = ModelDispatcher.getInstance().getProductInfo(productName);
        if (product == null) {
            System.out.println("Product not found in database: " + productName);
            return;
        }

        int currentStock = product.getAvailableQuantity();
        int maxStockQuantity = product.getMaxStockQuantity();
        int restockSchedule = product.getRestockSchedule(); // Assuming this method exists
        int restockAmount = maxStockQuantity - currentStock;

        if (restockAmount <= 0) {
            System.out.println("No restocking needed for product: " + productName);
            return;
        }

        System.out.println("Restocking Operation for Product " + productName + " initiated");

        while (restockAmount > 0) {
            int thisRestockAmount = Math.min(restockAmount, restockSchedule);
            ModelDispatcher.getInstance().updateDatabase(product.getProductID(), currentStock + thisRestockAmount);

            restockAmount -= thisRestockAmount;
            currentStock += thisRestockAmount;
        }

        ModelDispatcher.getInstance().notifyViewers();
        

        System.out.println("Restocking Operation for Product " + productName + " completed");
        
    }
        
    

}
