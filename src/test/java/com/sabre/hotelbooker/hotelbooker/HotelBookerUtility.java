
package com.sabre.hotelbooker.hotelbooker;

import com.microsoft.playwright.Page;
import com.sabre.hotelbooker.pageobjects.LoginPage;
import com.sabre.hotelbooker.pageobjects.aibasedPageObjects.HotelAvailabilityPage;
import com.sabre.hotelbooker.pageobjects.HotelSearchPage;

public class HotelBookerUtility {

    public LoginPage loginPage;
    public HotelSearchPage hotelSearchPage;
    public HotelAvailabilityPage hotelAvailabilityPage;
    

    public HotelBookerUtility(Page page) {
        this.loginPage = new LoginPage(page);
        this.hotelSearchPage = new HotelSearchPage(page);
        this.hotelAvailabilityPage = new HotelAvailabilityPage(page);
    }

}
