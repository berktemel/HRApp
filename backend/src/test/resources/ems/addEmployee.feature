Feature: Add User

  Scenario Outline: Employee added successfully
    Given User navigates to add employee page
    When User enters <name> <LastName> <email> <department> <StartDate> <salary>
    Then Add should be successful
    Examples:
      | name | LastName | email | department | StartDate | salary |
      | "test1" | "asdasd" | "test1@example.com" | "HR" | "2020/10/10" | 9876 |
      | "test2" | "qwewe" | "test2@example.com" | "IT" | "2017/01/02" | 10987 |