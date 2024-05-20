package controllerModule;

import modelModule.Order;
import modelModule.Product;

public class PricingServer implements IPrice {

    @Override
    public double calculatePrice(Order order, Product product) {        
        return order.getRequestedQuantity() * product.getUnitPrice();        
    }

}
