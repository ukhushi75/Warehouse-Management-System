package controllerModule;

public class DiscountStrategy1 implements IDiscount {

    @Override
    public double applyDiscount(double initialPrice) {
        // Implement discount strategy 1 logic
        return initialPrice * 0.9f; // 10% discount
    }

}
