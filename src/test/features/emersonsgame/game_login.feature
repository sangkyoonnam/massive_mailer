Feature: Game login
  As the player I want to be able to input my email address
  so that I can play the game.

  Scenario Outline: Checking invalid email
    When Login with email "<email>"
    Then Element "error_message" Should Contain "Invalid email provided!"

    Examples:
    | email      |
    | abc@     |
    | @@       |
    | @abc.com |

  Scenario: Ensure valid new email is saved before redirecting to game page
    When Login with email "new_terry@odd-e.com"
    Then Page should be redirected to "emersonsgame"
    Then Contacts page should contain "new_terry@odd-e.com"

  Scenario: Ensure valid existing email redirects to game page
    Given "terry@odd-e.com" is a contact already
    When Login with email "terry@odd-e.com"
    Then Page should be redirected to "emersonsgame"
    And Contacts page should contain exactly 1 "terry@odd-e.com"