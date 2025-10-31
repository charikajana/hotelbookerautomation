
package com.sabre.hotelbooker.hotelbooker;

import com.microsoft.playwright.Page;
import com.sabre.hotelbooker.pageobjects.LoginPage;

public class HotelBookerUtility {

    public LoginPage loginPage;
    

    public HotelBookerUtility(Page page) {
        this.loginPage = new LoginPage(page);
    }

}
