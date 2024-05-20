package controllerModule;

import modelModule.Order;
import modelModule.Product;

public interface IPrice {
    public double calculatePrice(Order order, Product product);
}
