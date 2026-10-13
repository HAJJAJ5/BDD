package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import shop.Product;
import shop.ShoppingCart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Step definitions for the "Online Shopping Cart" feature.
 * A fresh ShoppingCart is injected by Cucumber (PicoContainer) for every scenario.
 */
public class ShoppingCartSteps {

    private final ShoppingCart cart;

    /** Holds the last message returned by applyCoupon() or checkout(). */
    private String lastMessage;

    public ShoppingCartSteps(ShoppingCart cart) {
        this.cart = cart;
    }

    // The cart must be empty at the start of every scenario (used by Background).
    @Given("the shopping cart is empty")
    public void theShoppingCartIsEmpty() {
        assertTrue(cart.isEmpty(), "The cart should start empty");
    }

    // Add a single product to the cart by name and price.
    @When("I add {string} priced {int} to the cart")
    public void iAddProductToTheCart(String name, int price) {
        cart.addProduct(new Product(name, price));
        
    }

    // Put a product in the cart (reused as a Given by several scenarios).
    @Given("the cart contains {string} priced {int}")
    public void theCartContains(String name, int price) {
        cart.addProduct(new Product(name, price));
    }

    // Check how many items the cart holds.
    @Then("the cart contains {int} item")
    public void theCartContainsItem(int expectedCount) {
        assertEquals(expectedCount, cart.getItemCount(), "Unexpected number of items");
    }

    // Remove a product by name.
    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String name) {
        cart.removeProduct(name);
    }

    // The cart should have no items left.
    @Then("the cart is empty")
    public void theCartIsEmpty() {
        assertTrue(cart.isEmpty(), "The cart should be empty");
    }

    // Apply a coupon code and remember the returned message.
    @When("I apply the coupon {string}")
    public void iApplyTheCoupon(String code) {
        lastMessage = cart.applyCoupon(code);
    }

    // Try to check out and remember the returned message.
    @When("I checkout")
    public void iCheckout() {
        lastMessage = cart.checkout();
    }

    // The last returned message should match the expected text.
    @Then("the message should be {string}")
    public void theMessageShouldBe(String expected) {
        assertEquals(expected, lastMessage, "Unexpected message");
    }

    // The cart total should match the expected amount.
    @Then("the total should be {int}")
    public void theTotalShouldBe(int expected) {
        assertEquals(expected, cart.getTotal(), 0.001, "Unexpected total");
    }
}
