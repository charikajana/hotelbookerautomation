package com.sabre.hotelbooker.utils;

import com.sabre.hotelbooker.base.BaseTest;

import com.aventstack.extentreports.ExtentTest;
import org.junit.Assert;

public class HardAssertUtil {
    public static void assertTrue(boolean condition, String message, ExtentTest test) {
        try {
            Assert.assertTrue(message, condition);
            if (test != null) {
                test.pass("PASS: " + message);
            } else {
                System.err.println("[WARN] ExtentTest is null in HardAssertUtil.assertTrue. Message: " + message);
            }
        } catch (AssertionError e) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            if (test != null) {
                test.fail("FAIL: " + message + screenshotHtml + "<br>" + e.getMessage());
            } else {
                System.err.println("[WARN] ExtentTest is null in HardAssertUtil.assertTrue (FAIL). Message: " + message + ". Error: " + e.getMessage());
            }
            throw e;
        }
    }

    public static void assertFalse(boolean condition, String message, ExtentTest test) {
        try {
            Assert.assertFalse(message, condition);
            test.pass("PASS: " + message);
        } catch (AssertionError e) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            test.fail("FAIL: " + message + screenshotHtml + "<br>" + e.getMessage());
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message, ExtentTest test) {
        try {
            Assert.assertEquals(message, expected, actual);
            test.pass("PASS: " + message);
        } catch (AssertionError e) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            test.fail("FAIL: " + message + screenshotHtml + "<br>" + e.getMessage());
            throw e;
        }
    }

    public static void assertNotNull(Object actual, String message, ExtentTest test) {
        try {
            Assert.assertNotNull(message, actual);
            test.pass("PASS: " + message);
        } catch (AssertionError e) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            test.fail("FAIL: " + message + screenshotHtml + "<br>" + e.getMessage());
            throw e;
        }
    }

    public static void fail(String message, ExtentTest test) {
        String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
        String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
        String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
        test.fail("FAIL: " + message + screenshotHtml);
        Assert.fail(message);
    }
}
