package com.sabre.hotelbooker.stepdefinitions;

import com.sabre.hotelbooker.base.BaseTest;
import com.sabre.hotelbooker.hotelbooker.HotelBookerUtility;
import com.sabre.hotelbooker.pageobjects.LoginPage;
import com.sabre.hotelbooker.utils.ConfigReader;
import com.sabre.hotelbooker.utils.HardAssertUtil;
import com.sabre.hotelbooker.utils.SoftAssertUtil;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


public class LoginSteps {
    private ConfigReader config = new ConfigReader();
    private HotelBookerUtility hotelBookerUtility;

    @Before
    public void assignUtility() {
        BaseTest.initialize();
        hotelBookerUtility = new HotelBookerUtility(BaseTest.page);
    }


    @Given("Open Browser and Navigate to HotelBooker")
    public void user_is_on_login_page() {
        BaseTest.page.navigate(config.getProperty("baseUrl"));
        hotelBookerUtility.loginPage = new LoginPage(BaseTest.page);
    }


    @When("user enters username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
       hotelBookerUtility.loginPage.UserName(username);
        hotelBookerUtility.loginPage.Password(password);
        BaseTest.captureScreenshotWithInfo(BaseTest.page, "Login Successful", Hooks.test.get());
    }

    @When("user clicks login button")
    public void user_clicks_login_button() {
        hotelBookerUtility.loginPage.clickLogin();
    }


    @Then("user should be logged in")
    public void user_should_be_logged_in() {
        Hooks.test.get().info("adding this custome Information at Test Level");
        SoftAssertUtil.assertTrue(
            hotelBookerUtility.loginPage.isLogoutVisible(),
            "Logout button should be visible after login",
            Hooks.test.get()
        );
        HardAssertUtil.assertTrue(hotelBookerUtility.loginPage.isLogoutVisible(),
            "Logout button should be visible after login",
            Hooks.test.get());
        SoftAssertUtil.assertAll(Hooks.test.get());
        
    }

    @Then("user should see a login error message")
    public void user_should_see_a_login_error_message() {
        // Add assertion for error message
        // Example:
        // Assert.assertTrue(loginPage.isErrorVisible());
    }
}