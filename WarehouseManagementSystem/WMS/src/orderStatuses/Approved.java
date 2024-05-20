package orderStatuses;

import java.text.DecimalFormat;

import modelModule.Order;

/**
 * @author Judy Kalenga
 */

public class Approved extends OrderStatus {

    @Override
    public String report(Order anOrder, double price) {
        // Create a DecimalFormat object with the desired pattern
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Round the price to two decimal places
        double roundedPrice = Double.parseDouble(decimalFormat.format(price));

        this.status = "Order is finalized for Product \"" + anOrder.getProductName() + "\" and quantity = " + anOrder.getRequestedQuantity() + " with total price $" + roundedPrice + ".";
        return this.status;
    }

}
