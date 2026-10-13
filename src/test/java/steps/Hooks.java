package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import shop.ShoppingCart;

/**
 * Hooks that run before and after scenarios.
 * The generic @Before/@After run for EVERY scenario, while the tagged hooks
 * (@special, @snapshot) run ONLY for scenarios carrying that exact tag.
 * The printed messages make the execution order visible in the console.
 */
public class Hooks {

    // The SAME ShoppingCart instance used by the steps (PicoContainer injects it).
    private final ShoppingCart cart;

    public Hooks(ShoppingCart cart) {
        this.cart = cart;
    }

    // Runs before each scenario.
    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println(">>> Starting scenario: " + scenario.getName());
    }

    // Runs after each scenario and shows the result (PASSED / FAILED).
    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("<<< Finished scenario: " + scenario.getName() + " - " + scenario.getStatus());
    }

    // Runs ONLY for scenarios tagged @special, before the scenario starts.
    @Before("@special")
    public void beforeSpecialScenario() {
        System.out.println(">>> [@special hook] Special setup running only because of the @special tag");
    }

    // Runs ONLY for scenarios tagged @special, after the scenario finishes.
    @After("@special")
    public void afterSpecialScenario() {
        System.out.println("<<< [@special hook] Special cleanup running only because of the @special tag. Cart items: " + cart.getItemCount());
    }

    // Runs ONLY for scenarios tagged @snapshot, and only when the scenario FAILED.
    // A snapshot is the extra information a tester wants when a test breaks.
    // scenario.log(...) writes the text into the HTML report as well.
    @After("@snapshot")
    public void snapshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            String snapshot = "CART SNAPSHOT: " + cart.describe();
            scenario.log(snapshot);
            System.out.println(snapshot);
        }
    }
}
