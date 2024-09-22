Feature: Order functionalities

  Scenario: Create a new order
    Given I have the order's data
    When I send a request to create the order
    Then the order is successfully created

  Scenario: Retrieve an order by CPF
    Given I have an order registered with CPF "12345678999"
    When I send a request to retrieve the order by CPF "12345678999"
    Then I receive the order's data

  Scenario: Update an order status
    Given I have an order
    When I send a request to retrieve the order by CPF "12345678999"
    When I send a request to update the order status
    Then the order status is successfully updated

  Scenario: List all orders
    Given I have an order
    When I send a request to list all orders
    Then I receive a list of all orders
