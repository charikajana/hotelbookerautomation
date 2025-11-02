package com.sabre.hotelbooker.utils;

import com.aventstack.extentreports.ExtentTest;
import com.sabre.hotelbooker.stepdefinitions.Hooks;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

public class ExtentStepLogger implements EventListener {
    // Store step information temporarily until execution finishes
    private Map<String, String> stepTextMap = new ConcurrentHashMap<>();
    // Track which steps have been executed/logged
    private Map<String, Boolean> stepExecutedMap = new ConcurrentHashMap<>();
    // Store all steps for a test case to mark unexecuted ones as skipped
    private Map<String, List<String>> testCaseStepsMap = new ConcurrentHashMap<>();
    
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String keyword = step.getStep().getKeyword();
            String text = step.getStep().getText();
            String testCaseId = event.getTestCase().getId().toString();
            String stepId = step.getId().toString();
            
            // Store the formatted step text for later use when step finishes
            String trimmedKeyword = keyword.trim();
            String color = null;
            switch (trimmedKeyword) {
                case "Given":
                    color = "blue";
                    break;
                case "When":
                    color = "green";
                    break;
                case "Then":
                    color = "orange";
                    break;
                case "And":
                    color = "purple";
                    break;
            }
            
            String coloredKeyword = keyword;
            if (color != null && text != null && !text.isEmpty()) {
                coloredKeyword = "<span style='color:" + color + "'><b>" + trimmedKeyword + "</b></span> ";
            }
            
            // Store the formatted step text using step as key
            stepTextMap.put(stepId, coloredKeyword + text);
            stepExecutedMap.put(stepId, false); // Initially mark as not executed
            
            // Track all steps for this test case
            testCaseStepsMap.computeIfAbsent(testCaseId, k -> new ArrayList<>()).add(stepId);
        }
    }
    private void handleStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepId = step.getId().toString();
            String formattedStepText = stepTextMap.get(stepId);
            
            ExtentTest test = Hooks.test.get();
            if (test != null && formattedStepText != null) {
                // Mark this step as executed
                stepExecutedMap.put(stepId, true);
                
                // Log step with appropriate status based on execution result
                Status stepStatus = event.getResult().getStatus();
                switch (stepStatus) {
                    case PASSED:
                        test.pass(formattedStepText);
                        break;
                    case FAILED:
                        test.fail(formattedStepText);
                        break;
                    case SKIPPED:
                        test.skip(formattedStepText);
                        break;
                    case PENDING:
                        test.warning(formattedStepText);
                        break;
                    case UNDEFINED:
                        test.warning(formattedStepText);
                        break;
                    default:
                        test.info(formattedStepText + " - " + stepStatus.toString());
                        break;
                }
            }
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        String testCaseId = event.getTestCase().getId().toString();
        List<String> allStepsForTestCase = testCaseStepsMap.get(testCaseId);
        
        if (allStepsForTestCase != null) {
            ExtentTest test = Hooks.test.get();
            if (test != null) {
                // Check for any unexecuted steps and mark them as skipped
                for (String stepId : allStepsForTestCase) {
                    Boolean wasExecuted = stepExecutedMap.get(stepId);
                    if (wasExecuted == null || !wasExecuted) {
                        // This step was not executed, mark it as skipped
                        String formattedStepText = stepTextMap.get(stepId);
                        if (formattedStepText != null) {
                            test.skip(formattedStepText + " - Skipped (Not executed due to previous failure)");
                        }
                    }
                }
            }
            
            // Clean up tracking data for this test case
            for (String stepId : allStepsForTestCase) {
                stepTextMap.remove(stepId);
                stepExecutedMap.remove(stepId);
            }
            testCaseStepsMap.remove(testCaseId);
        }
    }
}
