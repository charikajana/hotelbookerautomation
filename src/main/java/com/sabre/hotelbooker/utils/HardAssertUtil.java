package com.sabre.hotelbooker.utils;

import com.sabre.hotelbooker.base.BaseTest;

import com.aventstack.extentreports.ExtentTest;

public class HardAssertUtil {
    public static void assertTrue(boolean condition, String message, ExtentTest test) {
        if (!condition) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            if (test != null) {
                test.fail("FAIL: " + message + screenshotHtml);
            } else {
                System.err.println("[WARN] ExtentTest is null in HardAssertUtil.assertTrue (FAIL). Message: " + message);
            }
            // Use custom CriticalTestFailureException to stop test execution immediately
            throw new CriticalTestFailureException("CRITICAL TEST FAILURE: " + message);
        }
        if (test != null) {
            test.pass("PASS: " + message);
        } else {
            System.err.println("[WARN] ExtentTest is null in HardAssertUtil.assertTrue. Message: " + message);
        }
    }

    public static void assertFalse(boolean condition, String message, ExtentTest test) {
        if (condition) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            if (test != null) {
                test.fail("FAIL: " + message + screenshotHtml);
            }
            // Use custom CriticalTestFailureException to stop test execution immediately
            throw new CriticalTestFailureException("CRITICAL TEST FAILURE: " + message);
        }
        if (test != null) {
            test.pass("PASS: " + message);
        }
    }

    public static void assertEquals(Object actual, Object expected, String message, ExtentTest test) {
        if (!java.util.Objects.equals(expected, actual)) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            String fullMessage = message + " - Expected: [" + expected + "], Actual: [" + actual + "]";
            if (test != null) {
                test.fail("FAIL: " + fullMessage + screenshotHtml);
            }
            // Use custom CriticalTestFailureException to stop test execution immediately
            throw new CriticalTestFailureException("CRITICAL TEST FAILURE: " + fullMessage);
        }
        if (test != null) {
            test.pass("PASS: " + message);
        }
    }

    public static void assertNotNull(Object actual, String message, ExtentTest test) {
        if (actual == null) {
            String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
            String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
            String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
            if (test != null) {
                test.fail("FAIL: " + message + screenshotHtml);
            }
            // Use custom CriticalTestFailureException to stop test execution immediately
            throw new CriticalTestFailureException("CRITICAL TEST FAILURE: " + message);
        }
        if (test != null) {
            test.pass("PASS: " + message);
        }
    }

    public static void fail(String message, ExtentTest test) {
        String screenshotPath = BaseTest.captureScreenshotToFile(BaseTest.page, "hard_assert_fail");
        String relativePath = screenshotPath != null ? screenshotPath.replace(com.sabre.hotelbooker.utils.ExtentReportManager.reportDir + java.io.File.separator, "") : null;
        String screenshotHtml = (relativePath != null) ? "<br><a href='" + relativePath + "' target='_blank'>Screenshot</a>" : "";
        if (test != null) {
            test.fail("FAIL: " + message + screenshotHtml);
        }
        // Mark critical failure to skip subsequent steps
        TestExecutionState.markCriticalFailure();
        // Use custom CriticalTestFailureException to stop test execution immediately
        throw new CriticalTestFailureException("CRITICAL TEST FAILURE: " + message);
    }
}
