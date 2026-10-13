package shop;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple in-memory shopping cart.
 * No UI, no database - just enough behaviour to demonstrate BDD.
 */
public class ShoppingCart {

    /** The valid discount coupon code. */
    public static final String VALID_COUPON = "SAVE10";

    private final List<Product> items = new ArrayList<>();

    /** Current discount rate (0.10 = 10% off). */
    private double discountRate = 0.0;

    /** Add one product to the cart. */
    public void addProduct(Product product) {
        items.add(product);
    }

    /** Remove every product with the given name. Returns true if something was removed. */
    public boolean removeProduct(String name) {
        return items.removeIf(product -> product.getName().equals(name));
    }

    /** How many items are currently in the cart. */
    public int getItemCount() {
        return items.size();
    }

    /** True when there are no items in the cart. */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /** Total price of all items, after any discount, rounded to 2 decimals. */
    public double getTotal() {
        double sum = 0.0;
        for (Product product : items) {
            sum += product.getPrice();
        }
        double discounted = sum * (1.0 - discountRate);
        return Math.round(discounted * 100.0) / 100.0;
    }

    /** Apply a coupon. "SAVE10" gives 10% off; anything else is rejected. */
    public String applyCoupon(String code) {
        if (VALID_COUPON.equals(code)) {
            discountRate = 0.10;
            return "Coupon applied";
        }
        return "Invalid coupon";
    }

    /** Finish the purchase. An empty cart cannot be checked out. */
    public String checkout() {
        if (isEmpty()) {
            return "Cart is empty";
        }
        return "Checkout successful";
    }
}
