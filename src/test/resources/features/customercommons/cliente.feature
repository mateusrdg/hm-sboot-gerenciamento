Feature: Customer functionalities

  Scenario: Register a new customer
    Given I have the customer's data
    When I send a request to register the customer
    Then the customer is successfully registered

  Scenario: Retrieve a customer by CPF
    Given I have a customer registered with CPF "12345678901"
    When I send a request to retrieve the customer by CPF "12345678901"
    Then I receive the customer's data
    And the customer's name is "João"
