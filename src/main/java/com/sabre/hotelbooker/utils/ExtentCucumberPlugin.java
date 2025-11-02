package com.sabre.hotelbooker.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import java.util.HashMap;
import java.util.Map;

public class ExtentCucumberPlugin implements ConcurrentEventListener {
    private ExtentReports extent = ExtentReportManager.getInstance();
    private Map<String, ExtentTest> scenarioTestMap = new HashMap<>();
    private Map<String, Integer> scenarioExampleCounter = new HashMap<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        String scenarioId = event.getTestCase().getId().toString();
        String scenarioName = event.getTestCase().getName();
        // For scenario outlines, append (Example #N) for each example
        String uniqueScenarioName = scenarioName;
        if (!scenarioName.contains("<")) {
            int exampleNum = scenarioExampleCounter.merge(scenarioName, 1, Integer::sum);
            // Only append if there is more than one example or always for outlines
            uniqueScenarioName = scenarioName + " (TestCase #" + exampleNum + ")";
        }
        if (!scenarioTestMap.containsKey(scenarioId)) {
            ExtentTest test = extent.createTest(uniqueScenarioName);
            scenarioTestMap.put(scenarioId, test);
        }
    }

    private void handleStepFinished(TestStepFinished event) {
        String testCaseId = event.getTestCase().getId().toString();
        ExtentTest test = scenarioTestMap.get(testCaseId);
        
        if (test != null && event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) event.getTestStep();
            String stepText = pickleStep.getStep().getKeyword() + pickleStep.getStep().getText();
            Status stepStatus = event.getResult().getStatus();
            
            // Log step based on its actual execution result
            switch (stepStatus) {
                case PASSED:
                    test.pass(stepText);
                    break;
                case FAILED:
                    Throwable error = event.getResult().getError();
                    String errorMessage = error != null ? error.getMessage() : "Unknown error";
                    test.fail(stepText + " - " + errorMessage);
                    
                    // Check if this is a critical failure and add additional info
                    if (error != null && error.getClass().getSimpleName().equals("CriticalTestFailureException")) {
                        test.fail("❌ CRITICAL FAILURE: Test execution stopped immediately");
                    }
                    break;
                case SKIPPED:
                    test.skip(stepText + " - Skipped");
                    break;
                case PENDING:
                    test.warning(stepText + " - Pending");
                    break;
                case UNDEFINED:
                    test.warning(stepText + " - Undefined");
                    break;
                default:
                    test.info(stepText + " - " + stepStatus.toString());
                    break;
            }
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        String testCaseId = event.getTestCase().getId().toString();
        ExtentTest test = scenarioTestMap.get(testCaseId);
        
        if (test != null) {
            Status status = event.getResult().getStatus();
            
            switch (status) {
                case PASSED:
                    test.pass("✅ Test Completed Successfully");
                    break;
                case FAILED:
                    Throwable error = event.getResult().getError();
                    if (error != null && error.getClass().getSimpleName().equals("CriticalTestFailureException")) {
                        test.fail("❌ Test Failed Due to Critical Failure - Execution Stopped Immediately");
                    } else {
                        test.fail("❌ Test Failed: " + (error != null ? error.getMessage() : "Unknown error"));
                    }
                    if (error != null) {
                        test.fail(error);
                    }
                    break;
                case SKIPPED:
                    test.skip("⏭️ Test Skipped");
                    break;
                default:
                    test.info("Test Status: " + status.toString());
                    break;
            }
        }
        
        // Clean up tracking maps for this test case
        scenarioTestMap.remove(testCaseId);
        
        extent.flush();
    }
}
