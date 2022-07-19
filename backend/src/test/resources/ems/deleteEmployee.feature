Feature: Delete Employee

  Scenario Outline: Employee deleted
    Given User tries to delete an employee
    When <id> is given
    Then Employee should be deleted
    Examples:
      | id |
      | 112 |
      | 113 |