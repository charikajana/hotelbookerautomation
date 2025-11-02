package com.sabre.hotelbooker.pageobjects;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import java.time.LocalDate;

public class HotelSearchPage {
  
    private static final String CLIENT_HEADING = "h2:has-text('Test QA Client(Sabre)')";
    private static final String COUNTRY_DROPDOWN = "#ctl00_lstCountry";
    private static final String LOCATION_FIELD = "input[placeholder*='Place']";
    private static final String HOTEL_NAME_FIELD = "#ctl00_txtHotelName";
    private static final String DISTANCE_DROPDOWN = "#ctl00_lstDistance";
    private static final String ARRIVAL_DATE_FIELD = "#ctl00_txtArrivalDate";
    private static final String NIGHTS_FIELD = "#ctl00_txtNights";
    private static final String ROOMS_DROPDOWN = "#ctl00_lstRooms";
    private static final String GUESTS_DROPDOWN = "#ctl00_lstOccupancy";
    private static final String SEARCH_BUTTON = "#ctl00_btnSearch";
    private static final String HEADER_CLIENT = "#lnkClientSelect";
    private static final String NO_HOTEL_MESSAGE = "text=Sorry, we weren't able to find any hotels for the criteria you specified.";
    private static final String SEARCH_RESULTS = ".hotel-result";

    private final Page page;

    public HotelSearchPage(Page page) {
        this.page = page;
    }

    public void selectClient() {
    page.click(CLIENT_HEADING);
    }

    public boolean isClientDisplayedOnHeader() {
        boolean visible = page.isVisible(HEADER_CLIENT);
        return visible;
    }

    public void selectCountry(String country) {
    page.waitForSelector(COUNTRY_DROPDOWN);
    page.selectOption(COUNTRY_DROPDOWN, new SelectOption().setLabel(country));
    }

    public void enterLocation(String location) {
    page.fill(LOCATION_FIELD, location);
    }

    public void enterHotelName(String hotelName) {
    page.fill(HOTEL_NAME_FIELD, hotelName);
    }

    public void selectDistance(String distance) {
        page.selectOption(DISTANCE_DROPDOWN, new SelectOption().setLabel(distance));
    }

    public void setArrivalDateDaysFromToday(int days) {
    page.locator(ARRIVAL_DATE_FIELD).clear();
    selectArrivalDateInCalendar(days);
    }

    public void setNights(String nights) {
    page.fill(NIGHTS_FIELD, nights);
    }

    public void selectRooms(String rooms) {
        page.selectOption(ROOMS_DROPDOWN, new SelectOption().setLabel(rooms));
    }

    public void selectGuests(String guests) {
        page.selectOption(GUESTS_DROPDOWN, new SelectOption().setLabel(guests));
    }

    public void clickSearchButton() {
    page.click(SEARCH_BUTTON);
    }

    public boolean isSearchResultDisplayedOrNoHotelMessage() {
        boolean result = page.isVisible(SEARCH_RESULTS) || page.isVisible(NO_HOTEL_MESSAGE);
        return result;
    }
    public void selectArrivalDateInCalendar(int daysFromToday) {
        LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
        int targetDay = targetDate.getDayOfMonth();
        int targetMonth = targetDate.getMonthValue();
        int targetYear = targetDate.getYear();
        page.click(ARRIVAL_DATE_FIELD);
        String monthLabelSelector = ".datepicker-days .datepicker-switch";
        String nextMonthButtonSelector = ".datepicker-days th.next";
        page.waitForSelector(monthLabelSelector);
        while (true) {
            String monthYearText = page.textContent(monthLabelSelector).trim();
            java.time.format.DateTimeFormatter calFmt = java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy");
            java.time.YearMonth displayedMonthYear = java.time.YearMonth.parse(monthYearText, calFmt);
            if (displayedMonthYear.getMonthValue() == targetMonth && displayedMonthYear.getYear() == targetYear) {
                break;
            }
            page.click(nextMonthButtonSelector);
            page.waitForTimeout(200);
        }
        String dayCellSelector = String.format(".datepicker-days td.day:not(.old):not(.new):text-is('%d')", targetDay);
        page.click(dayCellSelector);
    }
}
