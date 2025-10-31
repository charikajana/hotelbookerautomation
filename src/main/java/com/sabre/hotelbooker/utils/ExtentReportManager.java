package com.sabre.hotelbooker.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

public class ExtentReportManager {
    private static ExtentReports extent;
    public static String reportDir;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String date = new SimpleDateFormat("ddMMMyy").format(new Date()).toUpperCase();
            String time = new SimpleDateFormat("HHmmss").format(new Date());
            reportDir = "reports" + File.separator + date + File.separator + time;
            new File(reportDir).mkdirs();
            String reportPath = reportDir + File.separator + "ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Results");
            // Set dashboard as default view
            sparkReporter.viewConfigurer().viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY, ViewName.AUTHOR, ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG })
                .apply();
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }
}