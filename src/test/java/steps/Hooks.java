package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Hooks that run before and after every scenario.
 * The printed messages make the execution order visible in the console.
 */
public class Hooks {

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
}
