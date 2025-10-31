package com.sabre.hotelbooker.utils;

import org.assertj.core.api.SoftAssertions;

import com.aventstack.extentreports.ExtentTest;

public class SoftAssertUtil {
    private static final ThreadLocal<SoftAssertions> softAssertions = ThreadLocal.withInitial(SoftAssertions::new);

    public static void assertTrue(boolean condition, String message, ExtentTest test) {
        softAssertions.get().assertThat(condition).as(message).isTrue();
    }

    public static void assertFalse(boolean condition, String message, ExtentTest test) {
        softAssertions.get().assertThat(condition).as(message).isFalse();
    }

    public static void assertEquals(Object actual, Object expected, String message, ExtentTest test) {
        softAssertions.get().assertThat(actual).as(message).isEqualTo(expected);
    }

    public static void assertNotNull(Object actual, String message, ExtentTest test) {
        softAssertions.get().assertThat(actual).as(message).isNotNull();
    }

    public static void fail(String message, ExtentTest test) {
        test.fail("FAIL: " + message);
        softAssertions.get().fail(message);
    }

    public static void assertAll(ExtentTest test) {
        try {
            softAssertions.get().assertAll();
            test.pass("All assertions passed");
        } catch (AssertionError e) {
            test.fail("Assertion(s) failed: " + e.getMessage());
            throw e;
        } finally {
            softAssertions.remove();
        }
    }
}
