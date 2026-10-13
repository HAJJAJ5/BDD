# Online Shopping Cart - BDD with Cucumber

SWER 312 - Software Testing and QA assignment.

A tiny, plain-Java "Online Shopping" shopping cart tested with Behaviour-Driven
Development: the behaviour is written in plain-English Gherkin, and Cucumber
binds each sentence to Java code.

## Tech stack

- Java 17+ (built and tested with the installed JDK)
- Maven
- Cucumber 7.x (cucumber-java, cucumber-junit-platform-engine)
- JUnit 5 Platform Suite
- Plain Java only - no UI, no database, no Spring

## Project layout

```
BDD project/
  pom.xml
  README.md
  src/main/java/shop/Product.java                     Product: name + price
  src/main/java/shop/ShoppingCart.java                Cart behaviour under test
  src/test/resources/features/shopping_cart.feature   Gherkin scenarios
  src/test/resources/junit-platform.properties        Cucumber plugins + glue + features
  src/test/java/steps/ShoppingCartSteps.java          Step definitions
  src/test/java/steps/Hooks.java                      @Before / @After hooks
  src/test/java/runner/RunCucumberTest.java           JUnit 5 @Suite runner
```

## What the feature demonstrates

- **Background** - `the shopping cart is empty` runs before every scenario.
- **Scenario Outline + Examples** - the total of two products, tested with 3 rows.
- **Tags** - `@smoke`, `@discount`, `@negative`.
- **Hooks** - `@Before` and `@After` print a start/finish line so the execution
  order is visible in the console.

6 scenarios, where the Scenario Outline expands to 3 rows = **8 executions**.

## How to run

Open a terminal in the project folder (the path contains a space, so quote it):

```powershell
cd "C:\Users\Ahmad\OneDrive\Desktop\BDD project"
```

Run every scenario:

```powershell
mvn clean test
```

Run only the discount scenario:

```powershell
mvn test "-Dcucumber.filter.tags=@discount"
```

Run only the negative scenarios (invalid coupon + empty-cart checkout):

```powershell
mvn test "-Dcucumber.filter.tags=@negative"
```

> Note (Windows PowerShell): quote the whole `-D...` argument as shown, because
> PowerShell otherwise splits on the dot. In Command Prompt (cmd) or on
> macOS/Linux the plain form also works: `mvn test -Dcucumber.filter.tags=@discount`.

The HTML report is written to:

```
target/cucumber-report.html
```

Open it in any browser after a run.

## Expected result

```
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
