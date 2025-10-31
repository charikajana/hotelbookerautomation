    
package com.sabre.hotelbooker.base;

import com.microsoft.playwright.*;
import com.sabre.hotelbooker.utils.ConfigReader;


public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    public static Page page;


    public static void initialize() {
        ConfigReader config = new ConfigReader();
        String browserName = config.getProperty("browser");
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        context = browser.newContext();
        page = context.newPage();
    }

    public static void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
    public static void captureScreenshotWithInfo(com.microsoft.playwright.Page page, String info, com.aventstack.extentreports.ExtentTest test) {
        String currentTimestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new java.util.Date());
        String screenshotPath = captureScreenshotToFile(page, currentTimestamp);
        String relativePath = screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "");
        if (test != null) {
            test.info(info + " <a href='" + relativePath + "' target='_blank'>Screenshot</a>");
        } else {
            System.err.println("[WARN] ExtentTest is null in captureScreenshotWithInfo. Info: " + info);
        }
    }

    public static String captureScreenshotToFile(com.microsoft.playwright.Page page, String scenarioName) {
        try {
            String screenshotDir = com.sabre.hotelbooker.utils.ExtentReportManager.reportDir;
            if (screenshotDir == null) {
                // fallback if reportDir is not initialized
                String date = new java.text.SimpleDateFormat("ddMMMyy").format(new java.util.Date()).toUpperCase();
                String time = new java.text.SimpleDateFormat("HHmmss").format(new java.util.Date());
                screenshotDir = "reports" + java.io.File.separator + date + java.io.File.separator + time;
                new java.io.File(screenshotDir).mkdirs();
            }
            String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + System.currentTimeMillis() + ".png";
            String filePath = screenshotDir + java.io.File.separator + fileName;
            page.screenshot(new com.microsoft.playwright.Page.ScreenshotOptions().setPath(new java.io.File(filePath).toPath()).setFullPage(true));
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}