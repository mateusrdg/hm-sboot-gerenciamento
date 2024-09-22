Feature: Product functionalities

  Scenario: Insert a new product
    Given I have the product's data
    When I send a request to insert the product
    Then the product is successfully inserted

  Scenario: Update a product
    Given I have a product with SKU "12345"
    When I send a request to update the product
    Then the product is successfully updated

  Scenario: Delete a product by SKU
    Given I have a product with SKU "12345"
    When I send a request to delete the product by SKU "12345"
    Then the product is successfully deleted

  Scenario: List products by category
    Given I have products in the category "lanche"
    When I send a request to list products by category "lanche"
    Then I receive a list of products in the category "lanche"
