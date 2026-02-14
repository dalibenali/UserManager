@UserManagement
Feature: User management

  @GetUserById
  Scenario Outline: Get a user by ID number <userId>
    Given I GET the user by id <userId>	
    Then I check the API statusCode 200
    And The "id" field of body response is not null
    And The "email" field of body response is not null
    And The "username" field of body response is not null
    And The "name" object of body response contains the not null "firstname" field
    And The "name" object of body response contains the not null "lastname" field

    Examples: 
      | userId |
      |      1 |
      |      2 |

  @CreateUser
	Scenario Outline: Create a user using <fileName>.json
    Given I create the user from <fileName>
    Then I check the API statusCode 201
    And The "id" field of body response is not null 
  Examples:
    | fileName       |
    | "user1Payload" |
    | "user2Payload" | 
