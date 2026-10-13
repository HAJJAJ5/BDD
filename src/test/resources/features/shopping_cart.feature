Feature: Online Shopping Cart
  As an online shopper
  I want to manage the products in my cart
  So that I can buy what I need and use discount coupons

  Background:
    Given the shopping cart is empty

  @smoke
  Scenario: Add a product to the cart
    When I add "Laptop" priced 1000 to the cart
    Then the cart contains 1 item

  Scenario: Remove a product from the cart
    Given the cart contains "Laptop" priced 1000
    When I remove "Laptop" from the cart
    Then the cart is empty

  @outline
  Scenario Outline: Calculate the total of two products
    Given the cart contains "Item A" priced <price1>
    And the cart contains "Item B" priced <price2>
    Then the total should be <total>

    Examples:
      | price1 | price2 | total |
      | 10     | 20     | 30    |
      | 100    | 50     | 150   |
      | 5      | 5      | 10    |

  @discount
  Scenario: Apply a valid discount coupon
    Given the cart contains "Laptop" priced 1000
    When I apply the coupon "SAVE10"
    Then the message should be "Coupon applied"
    And the total should be 900

  @negative
  Scenario: Apply an invalid coupon
    Given the cart contains "Laptop" priced 1000
    When I apply the coupon "FAKE123"
    Then the message should be "Invalid coupon"
    And the total should be 1000

  @negative
  Scenario: Checkout with an empty cart
    When I checkout
    Then the message should be "Cart is empty"

  @special
  Scenario: SPECIAL - add a product (same as scenario 1 but with a tagged hook)
    When I add "Laptop" priced 1000 to the cart
    Then the cart contains 1 item

  @datatable
  Scenario: Calculate the total using a data table
    Given the cart contains the following products:
      | name   | price |
      | Laptop | 1000  |
      | Mouse  | 50    |
      | Phone  | 500   |
    Then the total should be 1550

  @fail-normal
  Scenario: DEMO - failing scenario WITHOUT snapshot
    Given the cart contains "Laptop" priced 1000
    When I apply the coupon "SAVE10"
    Then the total should be 800

  @fail-special @snapshot
  Scenario: DEMO - failing scenario WITH snapshot
    Given the cart contains "Laptop" priced 1000
    When I apply the coupon "SAVE10"
    Then the total should be 800
