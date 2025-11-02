@AI
Feature: Hotel Availability Functionality
  As a travel agent
  I want to search for hotels and check their availability
  So that I can book hotels for my clients

  Background:
    Given Open Browser and Navigate to HotelBooker
    When user enters username and password
    And user clicks login button

  @HotelAvailability
  Scenario Outline: Verify hotel availability and booking flow
    Given user selects client "<client>"
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
    
    Then hotel Availability page should be displayed
    And Validate pagination links are displayed and working
    
    When user clicks on Check Availability for Active Content Provider "<provider>"
    Then rate information should be loaded and displayed
    
    When user clicks on Full Rate Information link
    Then cancellation policy should be displayed
    And capture cancellation policy details to extent report
    
    When user clicks on Select Rate button
    Then Booking Summary page should be displayed

    Examples:
      | username    | password | client                | country | location            | hotelName | distance | days | nights | rooms | guests | provider    |
      | QA_Sabre_AU | ZFQMWCQN | Test QA Client(Sabre) | USA     | New York - Location | Holiday   | 20 Miles | 30   | 1      | 1     | 1      | Booking.com |