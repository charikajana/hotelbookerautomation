package com.sabre.hotelbooker.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.sabre.hotelbooker.hotelbooker.ApplicationConstants;
import com.sabre.hotelbooker.stepdefinitions.Hooks;
import com.sabre.hotelbooker.utils.ConfigReader;

public class LoginPage {
     private static final String USERNAME_INPUT = "#ctl00_cphMainContent_txtUserName";
    private static final String PASSWORD_INPUT = "#ctl00_cphMainContent_txtPassword";
    private static final String LOGIN_BUTTON = "#ctl00_cphMainContent_btnLogin";
    private static final String FORGOTTEN_PASSWORD_LINK = "#btnForgotten";
    private static final String FORGOTTEN_USERNAME_INPUT = "#ctl00_cphMainContent_ucForgottenPassword_txtForgottenUserName";
    private static final String FORGOTTEN_NEXT_BUTTON = "#ctl00_cphMainContent_ucForgottenPassword_btnFindUser";
    private static final String SECURITY_ANSWER_INPUT = "#ctl00_cphMainContent_ucForgottenPassword_txtAnswer";
    private static final String SECURITY_ANSWER_NEXT_BUTTON = "#ctl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion";
    private static final String CANCEL_FORGOTTEN_PASSWORD = "a[href='javascript: cancelForgottenPassword();']";
    private static final String TERMS_LINK = "a[href='Terms.aspx']";
    private static final String PRIVACY_LINK = "a[href='https://www.sabre.com/about/privacy/']";

    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToLoginPage() {
        page.navigate(ConfigReader.getProperty(ApplicationConstants.HOTEL_BOOKER_URL));
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.waitForTimeout(2000); 
        Hooks.Extent_INFO("Opened HotelBooker login page.");
    }

    public void enterUserName() {
        String UserName = ConfigReader.getProperty(ApplicationConstants.USER_NAME);
        page.fill(USERNAME_INPUT, UserName);
        Hooks.Extent_INFO("Filled username field.");
    }

    public void enterPassword() {
        String Password = ConfigReader.getProperty(ApplicationConstants.PASSWORD);
        page.fill(PASSWORD_INPUT, Password);
        Hooks.Extent_INFO("Filled password field.");
    }

    public void clickLogin() {
        page.click(LOGIN_BUTTON);
        Hooks.Extent_INFO("Clicked Login button.");
    }

    public void clickForgottenPassword() {
        page.click(FORGOTTEN_PASSWORD_LINK);
        Hooks.Extent_INFO("Opened Forgotten Password wizard.");
    }

    public void enterForgottenUserName(String userName) {
        page.fill(FORGOTTEN_USERNAME_INPUT, userName);
        Hooks.Extent_INFO("Entered Forgotten Password username.");
    }

    public void submitForgottenUserName() {
        page.click(FORGOTTEN_NEXT_BUTTON);
        Hooks.Extent_INFO("Submitted Forgotten Password username.");
    }

    public void enterSecurityAnswer(String answer) {
        page.fill(SECURITY_ANSWER_INPUT, answer);
        Hooks.Extent_INFO("Entered security answer for Forgotten Password.");
    }

    public void submitSecurityAnswer() {
        page.click(SECURITY_ANSWER_NEXT_BUTTON);
        Hooks.Extent_INFO("Submitted security answer for Forgotten Password.");
    }

    public void cancelForgottenPassword() {
        page.click(CANCEL_FORGOTTEN_PASSWORD);
        Hooks.Extent_INFO("Cancelled Forgotten Password wizard.");
    }

    public void openTerms() {
        page.click(TERMS_LINK);
        Hooks.Extent_INFO("Opened Terms & Conditions.");
    }

    public void openPrivacy() {
        page.click(PRIVACY_LINK);
        Hooks.Extent_INFO("Opened Privacy page.");
    }

    public boolean isLoginButtonVisible() {
        return page.isVisible(LOGIN_BUTTON);
    }

    public Locator getLoginButton() {
        return page.locator(LOGIN_BUTTON);
    }
}