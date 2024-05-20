package middlewareModule;

import modelModule.Order;

public interface IOrderQueue {
    
    // queue orders
    void enqueue(Order order);
    Order dequeue();

}
