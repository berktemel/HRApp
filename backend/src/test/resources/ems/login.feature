Feature: Login

  Scenario Outline: Successful Login
    Given User navigate to login page
    When User logs in by using <email>
    And By using <password>
    Then Login is "<result>"
    Examples:
      | email | password           | result |
      | "abc@example.com" | "1234" | true |
      | "asd@example.com" | "1623" | false |