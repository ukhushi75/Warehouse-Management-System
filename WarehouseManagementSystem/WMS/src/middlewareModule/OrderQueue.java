package middlewareModule;

import java.util.LinkedList;
import java.util.Queue;

import modelModule.Order;

public class OrderQueue implements IOrderQueue {
    
    private Queue<Order> orderQueue = new LinkedList<>();

    @Override
    public void enqueue(Order order) {
        orderQueue.add(order);
    }

    @Override
    public Order dequeue() {
        return orderQueue.poll();
    }

}
