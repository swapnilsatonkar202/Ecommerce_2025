Feature: Login functionality

  Scenario: Valid login
    Given user is on login page
    When user enters valid username and password
    Then user should login successfully