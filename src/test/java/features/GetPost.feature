Feature: Verify Lists

  # This scenario checks the GET operation for '/users' and prints the received data.
  Scenario: Verify Collections
    Given I perform GET operation for "/users"
    And I print the data in console

  # This scenario verifies the POST operation for '/users' with a specific body, and checks if the response is as expected.
  Scenario:Verify Post Operation for User
    Given I perform POST operation for "/users" with body
      | id | name   | job |
      | 1  | Jayson | QA  |
    Then I have a 201 status code response
    And I should see the body has name as "Jayson"

  # This scenario verifies the POST, PUT and DELETE operations for '/users' with specific bodies.
  # It checks if the response of each operation is as expected.
  Scenario:Verify Operations for User
    Given I perform POST operation for "/users" with body
      | id | name   | job |
      | 1  | Jayson | QA  |
    Then I have a 201 status code response
    And I should see the body has name as "Jayson"
    When I perform PUT operation for "/users/1" with body
      | id | name     | Job    |
      | 1  | Jayson 2 | Senior |
    Then I have a 200 status code response
    And I should see the body has name as "Jayson 2"
    When I perform DELETE operation for "/users/1" with body
      | id |
      | 1  |
    Then I have a 204 status code response
    And I should not see a body in the response
    And I print the data in console