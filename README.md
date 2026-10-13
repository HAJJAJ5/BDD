# Online Shopping Cart - BDD with Cucumber

SWER 312 - Software Testing and QA assignment.

A tiny, plain-Java "Online Shopping" shopping cart tested with Behaviour-Driven
Development: the behaviour is written in plain-English Gherkin, and Cucumber
binds each sentence to Java code. The demo also shows how a **tag** can switch on
extra behaviour (a tagged hook, a data table, a failure snapshot) for only one
scenario, while the normal scenarios stay untouched.

## Tech stack

- Java 17+ (built and tested with the installed JDK)
- Maven
- Cucumber 7.x (cucumber-java, cucumber-junit-platform-engine)
- JUnit 5 Platform Suite
- PicoContainer for a fresh cart per scenario
- Plain Java only - no UI, no database, no Spring

## Project layout

```
BDD project/
  pom.xml
  README.md
  src/main/java/shop/Product.java                     Product: name + price
  src/main/java/shop/ShoppingCart.java                Cart behaviour + describe() snapshot
  src/test/resources/features/shopping_cart.feature   Gherkin scenarios
  src/test/resources/junit-platform.properties        Cucumber plugins + glue + default tag filter
  src/test/java/steps/ShoppingCartSteps.java          Step definitions (incl. data table)
  src/test/java/steps/Hooks.java                      Generic @Before/@After + tagged hooks
  src/test/java/runner/RunCucumberTest.java           JUnit 5 @Suite runner
```

## What the feature demonstrates

- **Background** - `the shopping cart is empty` runs before every scenario.
- **Scenario Outline + Examples** - the total of two products, 3 rows, tagged `@outline`.
- **Data Table** - many products passed into ONE step, in the `@datatable` scenario.
  (A Data Table feeds many rows to one step; a Scenario Outline repeats the whole
  scenario once per row.)
- **Tags** - `@smoke`, `@discount`, `@negative`, `@outline`, `@datatable`,
  `@special`, `@fail-normal`, `@fail-special`, `@snapshot`.
- **Generic hooks** - the plain `@Before` / `@After` run for every scenario.
- **Tagged hook** - `@Before("@special")` / `@After("@special")` print extra lines
  ONLY for the `@special` scenario. This is the "normal vs tagged" comparison.
- **Failure snapshot** - a separate `@After("@snapshot")` hook runs ONLY when a
  scenario tagged `@snapshot` fails, and logs `CART SNAPSHOT: ...` to the console
  and to the HTML report.

By default there are **10 passing executions**: 8 original (5 scenarios + 3 Outline
rows) + 1 `@special` + 1 `@datatable`. The two `@fail-*` demo scenarios are skipped
by default (see the tag filter below).

## How to run

Open a terminal in the project folder (the path contains a space, so quote it):

```powershell
cd "C:\Users\Ahmad\OneDrive\Desktop\BDD project"
```

> Windows PowerShell note: quote the whole `-D...` argument as shown, because
> PowerShell otherwise splits on the dot. In Command Prompt (cmd) or macOS/Linux
> the plain form also works: `mvn test -Dcucumber.filter.tags=@discount`.

### Normal run (stays green)

```powershell
mvn clean test
```

### Run one tag at a time

```powershell
mvn test "-Dcucumber.filter.tags=@smoke"        # original scenario, NO @special hook lines
mvn test "-Dcucumber.filter.tags=@outline"      # Scenario Outline: 3 passing rows
mvn test "-Dcucumber.filter.tags=@datatable"    # Data Table scenario: 1 passing
mvn test "-Dcucumber.filter.tags=@discount"     # valid coupon
mvn test "-Dcucumber.filter.tags=@negative"     # invalid coupon + empty checkout
mvn test "-Dcucumber.filter.tags=@special"      # tagged hook prints extra @special lines
```

### Run the intentionally failing demos

Both fail on purpose (the real total is 900, the test expects 800). Only the
second one prints a `CART SNAPSHOT`.

```powershell
mvn test "-Dcucumber.filter.tags=@fail-normal"   # 1 FAILURE, NO snapshot
mvn test "-Dcucumber.filter.tags=@fail-special"  # 1 FAILURE, CART SNAPSHOT in console + report
```

The HTML report is written to:

```
target/cucumber-report.html
```

Open it in any browser after a run. The snapshot text from `@fail-special`
appears in the report.

## Default filter

`junit-platform.properties` contains:

```
cucumber.filter.tags=not @fail-normal and not @fail-special
```

so the two failing demo scenarios are skipped during a normal `mvn clean test`.
A command-line `-Dcucumber.filter.tags=...` **overrides** this value, which is how
the `@fail-normal` / `@fail-special` demos are run on demand.

## Expected result

```
Tests run: 12, Failures: 0, Errors: 0, Skipped: 2
BUILD SUCCESS
```

(10 scenarios executed + 2 skipped failed-demo scenarios = 12 reported.)
