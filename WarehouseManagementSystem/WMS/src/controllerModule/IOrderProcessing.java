package controllerModule;

import java.io.IOException;
import java.util.Date;

/**
 * @author Judy Kalenga
 */

public interface IOrderProcessing {
    
//    public void processOrder(String orderID, String productID, int requestedQuantity, Date timeStamp) throws IOException;
    public String processOrder(String productName, int requestedQuantity, Date timeStamp) throws IOException;

}
