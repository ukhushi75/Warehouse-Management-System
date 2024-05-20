package controllerModule;

public class DiscountStrategy2 implements IDiscount {

    @Override
    public double applyDiscount(double initialPrice) {
        // Implement discount strategy 2 logic
        return initialPrice * 0.85f; // 15% discount
    }

}
