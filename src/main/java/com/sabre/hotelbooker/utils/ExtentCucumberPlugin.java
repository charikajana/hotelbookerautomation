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
    // Track example count per scenario outline name
    private Map<String, Integer> scenarioExampleCounter = new HashMap<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
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

    private void handleStepStarted(TestStepStarted event) {
        try {
            java.nio.file.Files.write(
                java.nio.file.Paths.get("step_plugin_invoked.txt"),
                ("Step event: " + System.currentTimeMillis() + System.lineSeparator()).getBytes(),
                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            // ignore
        }
        TestStep testStep = event.getTestStep();
        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            String stepText = pickleStep.getStep().getKeyword() + pickleStep.getStep().getText();
            String scenarioId = event.getTestCase().getId().toString();
            ExtentTest test = scenarioTestMap.get(scenarioId);
            if (test != null) {
                test.info(stepText);
            }
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        scenarioTestMap.remove(event.getTestCase().getId().toString());
    }
}
