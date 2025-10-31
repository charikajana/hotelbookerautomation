@login @smoke
Feature: Login

  @positive
  Scenario Outline: Successful login with valid credentials
    Given Open Browser and Navigate to HotelBooker
    When user enters username "<username>" and password "<password>"
    And user clicks login button
    Then user should be logged in

    Examples:
      | username   | password   |
      | QA_Sabre_AU      | ZFQMWCQN      |
      | QA_Sabre_AU      | ZFQMWCQN      |