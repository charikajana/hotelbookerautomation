package com.sabre.hotelbooker.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.sabre.hotelbooker.stepdefinitions",
    plugin = {
        "pretty",
        "html:reports/cucumber-html-report.html",
        "com.sabre.hotelbooker.utils.ExtentStepLogger"
    },
        tags = "@HotelAvailability and @AI"
)
public class TestRunner {
}
