Feature: Add New User

  Scenario Outline: User added successfully
    Given Someone navigate to signup page
    When Someone enters <name> <LastName> <email> <password>
    Then Signup should be successful
    Examples:
      | name | LastName | email | password |
      | "test1" | "asdasd" | "test1@example.com" | "1234" |
      | "test2" | "qwewe" | "test2@example.com" | "123" |