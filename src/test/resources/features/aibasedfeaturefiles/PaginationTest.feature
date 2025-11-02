@AI @PaginationTest
Feature: Pagination Test
  Test to verify exact pagination count

  Background:
    Given Open Browser and Navigate to HotelBooker
    When user enters username and password
    And user clicks login button

  Scenario: Test pagination detection accuracy
    Given user selects client "Test QA Client(Sabre)"
    When user selects country "USA"
    And enters location "New York - Location" from suggestion
    And enters hotel name "Holiday"
    And selects distance "20 Miles"
    And enters number of nights as "1"
    And selects number of rooms as "1"
    And selects number of guests as "1"
    And selects arrival date 30 days from today
    And clicks on search button
    Then pagination links should be displayed and validated as working