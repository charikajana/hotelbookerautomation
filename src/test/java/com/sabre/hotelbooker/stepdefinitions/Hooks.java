package com.sabre.hotelbooker.stepdefinitions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.sabre.hotelbooker.base.BaseTest;
import com.sabre.hotelbooker.utils.ExtentReportManager;
import com.sabre.hotelbooker.utils.TestExecutionState;
import io.cucumber.java.AfterAll;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    public static ExtentReports extent;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    // private static final ConcurrentHashMap<String, AtomicInteger> scenarioExampleCounters = new ConcurrentHashMap<>();



    @Before(order = 0)
    public void setUp(Scenario scenario) {
        if (extent == null) {
            extent = ExtentReportManager.getInstance();
        }
        String baseScenarioName = scenario.getName();
        String scenarioId = scenario.getId();
        String exampleSuffix = "";
        if (scenarioId != null && scenarioId.contains(";")) {
            String[] parts = scenarioId.split(";");
            if (parts.length > 1) {
                exampleSuffix = " [Example: " + parts[1] + "]";
            }
        }
        String scenarioName = baseScenarioName + exampleSuffix;
        System.out.println("[DEBUG] Creating ExtentTest for scenario: '" + scenarioName + "'");
        ExtentTest extentTest = extent.createTest(scenarioName);
        test.set(extentTest);
        // Reset critical failure flag for new scenario
        TestExecutionState.resetCriticalFailureState();
        System.out.println("[DEBUG] ExtentTest set in ThreadLocal: " + (test.get() != null));
        BaseTest.initialize();
    }

    @After
    public void tearDownScenario() {
        com.sabre.hotelbooker.base.BaseTest.tearDown();
        test.remove();
        TestExecutionState.cleanup();
    }
    // private static boolean isScenarioOutline(Scenario scenario) {
    //     String raw = scenario.getId();
    //     return raw != null && raw.contains(";" /* scenario outline examples are separated by ; in id */);
    // }

    @AfterAll
    public static void tearDownAll() {
        if (extent != null) {
            extent.flush();
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (extent != null) {
                extent.flush();
                System.out.println("[HOOK] ExtentReports flushed in shutdown hook.");
            }
        }));
    }

    

    /**
     * Log custom info to the current ExtentTest node. Safe to call from any class (including PageObjects).
     */
    public static void Extent_INFO(String message) {
        ExtentTest t = test.get();
        if (t != null) {
            t.info(message);

        }
    }
    public static void Extent_WARNING(String message) {
        ExtentTest t = test.get();
        if (t != null) {
            t.warning(message);

        }
    }
    public static void Extent_FAIL(String message) {
        ExtentTest t = test.get();
        if (t != null) {
            t.fail(message);

        }
    }
}
