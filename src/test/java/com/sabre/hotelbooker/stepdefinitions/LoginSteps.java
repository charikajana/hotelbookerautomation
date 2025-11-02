package com.sabre.hotelbooker.stepdefinitions;

import com.sabre.hotelbooker.base.BaseTest;
import com.sabre.hotelbooker.hotelbooker.HotelBookerUtility;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class LoginSteps {
    
    private HotelBookerUtility hotelBookerUtility;

    @Before
    public void assignUtility() {
        hotelBookerUtility = new HotelBookerUtility(BaseTest.page);
    }
    
    @Given("Open Browser and Navigate to HotelBooker")
    public void user_is_on_login_page() {
        hotelBookerUtility.loginPage.navigateToLoginPage();
    }

    @When("user enters username and password")
    public void user_enters_username_and_password() {
       hotelBookerUtility.loginPage.enterUserName();
        hotelBookerUtility.loginPage.enterPassword();
    }

    @When("user clicks login button")
    public void user_clicks_login_button() {
        hotelBookerUtility.loginPage.clickLogin();
        BaseTest.captureScreenshotWithInfo(BaseTest.page, "Login Successful", Hooks.test.get());
    }
}