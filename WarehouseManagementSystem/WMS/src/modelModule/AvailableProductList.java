package modelModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AvailableProductList {

    private static AvailableProductList instance = null;

    private HashMap<String, Integer> availableProductList = new HashMap<String, Integer>();

    public HashMap<String, Integer> getAvailableProductList() {
        return availableProductList;
    }

    public static AvailableProductList getInstance() {
        if (instance == null)
            instance = new AvailableProductList();

        return instance;
    }

    private AvailableProductList() {}

    public HashMap<String, Integer> findAvailableProductsAndQuantities() {

        availableProductList.clear();

        // Here we query the Product DB and we get the product names or the product IDs

//        availableProductList.put("Product 1", 10);
//        availableProductList.put("Product 2", 20);
//        availableProductList.put("Product 3", 30);
//        availableProductList.put("Product 4", 20);
//        availableProductList.put("Product 5", 10);
        
        List<Product> productList;
        try {
            productList = ModelDispatcher.getInstance().getProducts();
            for (Product product: productList) {
                availableProductList.put(product.getProductName(), product.getAvailableQuantity());
            }
            
            return availableProductList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
        return availableProductList;
        
        

        
        

        

    }

}
