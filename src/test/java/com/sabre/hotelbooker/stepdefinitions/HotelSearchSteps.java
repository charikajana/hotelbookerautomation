package com.sabre.hotelbooker.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

import com.sabre.hotelbooker.base.BaseTest;
import com.sabre.hotelbooker.hotelbooker.HotelBookerUtility;

public class HotelSearchSteps {
    private HotelBookerUtility hotelBookerUtility;

     @Before
    public void assignUtility() {
        hotelBookerUtility = new HotelBookerUtility(BaseTest.page);
    }
  

    @When("selects client {string}")
    public void selects_client(String clientName) {
        hotelBookerUtility.hotelSearchPage.selectClient();
    }

    @Then("selected client should display on header")
    public void selected_client_should_display_on_header() {
        assertTrue(hotelBookerUtility.hotelSearchPage.isClientDisplayedOnHeader());
    }

    @When("user selects country {string}")
    public void user_selects_country(String country) {
        hotelBookerUtility.hotelSearchPage.selectCountry(country);
    }

    @When("enters location {string} from suggestion")
    public void enters_location_from_suggestion(String location) {
        hotelBookerUtility.hotelSearchPage.enterLocation(location);
    }

    @When("enters hotel name {string}")
    public void enters_hotel_name(String hotelName) {
        hotelBookerUtility.hotelSearchPage.enterHotelName(hotelName);
    }

    @When("selects distance {string}")
    public void selects_distance(String distance) {
        hotelBookerUtility.hotelSearchPage.selectDistance(distance);
    }

    @When("selects arrival date {int} days from today")
    public void selects_arrival_date_days_from_today(int days) {
        hotelBookerUtility.hotelSearchPage.setArrivalDateDaysFromToday(days);
    }

    @When("enters number of nights as {string}")
    public void enters_number_of_nights_as(String nights) {
        hotelBookerUtility.hotelSearchPage.setNights(nights);
    }

    @When("selects number of rooms as {string}")
    public void selects_number_of_rooms_as(String rooms) {
        hotelBookerUtility.hotelSearchPage.selectRooms(rooms);
    }

    @When("selects number of guests as {string}")
    public void selects_number_of_guests_as(String guests) {
        hotelBookerUtility.hotelSearchPage.selectGuests(guests);
    }

    @When("clicks on search button")
    public void clicks_on_search_button() {
        hotelBookerUtility.hotelSearchPage.clickSearchButton();
        BaseTest.waitForPageLoadComplete();
        BaseTest.captureScreenshotWithInfo(BaseTest.page, "Search for Hotels", Hooks.test.get());

    }

    @Then("hotel search results should be displayed or a message if no hotels found")
    public void hotel_search_results_should_be_displayed_or_a_message_if_no_hotels_found() {
        assertTrue(hotelBookerUtility.hotelSearchPage.isSearchResultDisplayedOrNoHotelMessage());
    }
}