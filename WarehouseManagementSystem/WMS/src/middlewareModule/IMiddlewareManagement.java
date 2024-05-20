package middlewareModule;

import java.io.IOException;

import modelModule.Order;

public interface IMiddlewareManagement {
    
    public void initLogger(String logFilePath);
    public String startOrderProcessing(Order order) throws IOException;

}
