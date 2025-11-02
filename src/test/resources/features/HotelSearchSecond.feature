@AI @Regression @Smoke @sanity
Feature: Hotel Search Functionality second file

  Background:
    Given Open Browser and Navigate to HotelBooker
    When user enters username and password
    And user clicks login button

@HotelSearch
  Scenario Outline: Verify hotel search with all filters second file
    And selects client "<client>"
    Then selected client should display on header
    When user selects country "<country>"
    And enters location "<location>" from suggestion
    And enters hotel name "<hotelName>"
    And selects distance "<distance>"
    And enters number of nights as "<nights>"
    And selects number of rooms as "<rooms>"
    And selects number of guests as "<guests>"
    And selects arrival date <days> days from today
    And clicks on search button

    Examples:
      | username     | password   | client                  | country | location             | hotelName   | distance  | days      | nights | rooms | guests |
      | QA_Sabre_AU | ZFQMWCQN    | Test QA Client(Sabre)   | USA     | New York - Location  | Holiday     | 5 Miles   | 90        | 1      | 1     | 1      |
