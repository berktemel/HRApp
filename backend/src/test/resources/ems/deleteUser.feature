Feature: Delete User

  Scenario Outline: User deleted
    Given Admin tries to delete a user
    When <id> is given
    Then User should be deleted
    Examples:
      | id |
      | 110 |
      | 111 |