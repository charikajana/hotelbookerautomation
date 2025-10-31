package com.sabre.hotelbooker.utils;

import com.aventstack.extentreports.ExtentTest;
import com.sabre.hotelbooker.stepdefinitions.Hooks;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.PickleStepTestStep;

public class ExtentStepLogger implements EventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
    }

    private void handleStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String keyword = step.getStep().getKeyword();
            String text = step.getStep().getText();
            ExtentTest test = Hooks.test.get();
            if (test != null) {
                // Only color if keyword is at the start of the step (feature file step)
                String coloredKeyword = keyword;
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
                if (color != null && text != null && !text.isEmpty()) {
                    // Only color if the keyword is at the start (Cucumber always provides it this way for feature steps)
                    coloredKeyword = "<span style='color:" + color + "'><b>" + trimmedKeyword + "</b></span> ";
                }
                test.info(coloredKeyword + text);
            }
        }
    }

    private void handleStepFinished(TestStepFinished event) {
        // Optionally, log step result (pass/fail) here
    }
}
