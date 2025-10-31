 package com.sabre.hotelbooker.pageobjects;

import com.microsoft.playwright.Page;
import com.sabre.hotelbooker.stepdefinitions.Hooks;

public class LoginPage {
    private Page page;

    public LoginPage(Page page){
        this.page = page;
    }

    // type: hidden
    private final String __VIEWSTATE = "#__VIEWSTATE";
    public void set__VIEWSTATE(String value) {
        page.fill(__VIEWSTATE, value);
    }

    // type: hidden
    private final String __VIEWSTATEGENERATOR = "#__VIEWSTATEGENERATOR";
    public void set__VIEWSTATEGENERATOR(String value) {
        page.fill(__VIEWSTATEGENERATOR, value);
    }

    // type: hidden
    private final String __EVENTVALIDATION = "#__EVENTVALIDATION";
    public void set__EVENTVALIDATION(String value) {
        page.fill(__EVENTVALIDATION, value);
    }

    // type: text
    private final String ctl00_cphMainContent_txtUserName = "#ctl00_cphMainContent_txtUserName";
    public void UserName(String value) {
        page.fill(ctl00_cphMainContent_txtUserName, value);
    }

    // type: password
    private final String ctl00_cphMainContent_txtPassword = "#ctl00_cphMainContent_txtPassword";
    public void Password(String value) {
        page.fill(ctl00_cphMainContent_txtPassword, value);
        Hooks.logToExtent("Given Paswword.");
    }

    // type: submit
    private final String ctl00_cphMainContent_btnLogin = "#ctl00_cphMainContent_btnLogin";
    public void clickLogin() {
        page.click(ctl00_cphMainContent_btnLogin);
        Hooks.logToExtent("Then Clicked on Login button.");
    }

    // type: text
    private final String ctl00_cphMainContent_ucForgottenPassword_txtForgottenUserName = "#ctl00_cphMainContent_ucForgottenPassword_txtForgottenUserName";
    public void ForgottenUserName(String value) {
        page.fill(ctl00_cphMainContent_ucForgottenPassword_txtForgottenUserName, value);
    }

    // type: submit
    private final String ctl00_cphMainContent_ucForgottenPassword_btnFindUser = "#ctl00_cphMainContent_ucForgottenPassword_btnFindUser";
    public void setCtl00_cphMainContent_ucForgottenPassword_btnFindUser(String value) {
        page.fill(ctl00_cphMainContent_ucForgottenPassword_btnFindUser, value);
    }

    // type: text
    private final String ctl00_cphMainContent_ucForgottenPassword_txtAnswer = "#ctl00_cphMainContent_ucForgottenPassword_txtAnswer";
    public void setCtl00_cphMainContent_ucForgottenPassword_txtAnswer(String value) {
        page.fill(ctl00_cphMainContent_ucForgottenPassword_txtAnswer, value);
    }

    // type: submit
    private final String ctl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion = "#ctl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion";
    public void setCtl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion(String value) {
        page.fill(ctl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion, value);
    }

    // type: hidden
    private final String ctl00_hdnPageToken = "#ctl00_hdnPageToken";
    public void setCtl00_hdnPageToken(String value) {
        page.fill(ctl00_hdnPageToken, value);
    }

    // Returns true if the logout button is visible (update selector as needed)
     private final String logoutSelector = "#ctl00_cphMainContent_ucForgottenPassword_btnAnswerQuestion";
    public boolean isLogoutVisible() {    
        return page.isVisible(logoutSelector);
    }
}