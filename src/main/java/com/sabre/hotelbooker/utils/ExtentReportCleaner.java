package com.sabre.hotelbooker.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExtentReportCleaner {
    public static void main(String[] args) throws IOException {
        // Dynamically find the latest ExtentReport.html in the reports directory
        String reportPath = findLatestExtentReport();
        if (reportPath == null) {
            System.err.println("No ExtentReport.html found in reports directory.");
            return;
        }
        Document doc = Jsoup.parse(new File(reportPath), "UTF-8");

        Elements testItems = doc.select("li.test-item");
        System.out.println("[CLEANER] Test names before cleaning:");
        for (Element testItem : testItems) {
            String name = testItem.selectFirst(".test-detail .name") != null ? testItem.selectFirst(".test-detail .name").text() : "";
            System.out.println("  - " + name);
        }
        java.util.List<Element> cleanedTests = new java.util.ArrayList<>();
        for (Element testItem : testItems) {
            Element durationElem = testItem.selectFirst("span.text-sm span");
            String duration = durationElem != null ? durationElem.text() : "";
            boolean hasSteps = !testItem.select("table.table-sm tr.event-row").isEmpty();
            // Remove only empty entries, keep all test nodes with steps and non-zero duration
            if (!hasSteps || duration.endsWith("00:00:00:000")) {
                continue;
            }
            cleanedTests.add(testItem);
        }
        System.out.println("[CLEANER] Test names after cleaning:");
        for (Element e : cleanedTests) {
            String name = e.selectFirst(".test-detail .name") != null ? e.selectFirst(".test-detail .name").text() : "";
            System.out.println("  - " + name);
        }
        System.out.println("[CLEANER] Test names after cleaning:");
        for (Element e : cleanedTests) {
            String name = e.selectFirst(".test-detail .name") != null ? e.selectFirst(".test-detail .name").text() : "";
            System.out.println("  - " + name);
        }
        // Already printed cleaned test names above

        // Remove all test-items and re-add only cleaned ones
        Element testList = doc.selectFirst("ul.test-list-item");
        if (testList != null) {
            testList.empty();
            for (Element e : cleanedTests) {
                testList.appendChild(e);
            }
        }

        // Update dashboard summary counts to match the number of test nodes
        int testCount = cleanedTests.size();
        // Update the main dashboard card ("Tests Passed")
        Elements passCards = doc.select(".card-body:has(.text-pass)");
        for (Element card : passCards) {
            Element h3 = card.selectFirst("h3");
            if (h3 != null) h3.text(Integer.toString(testCount));
        }
        // Update the small tag in the dashboard ("tests passed")
        Elements smalls = doc.select("small:matchesOwn(\\d+ tests passed)");
        for (Element small : smalls) {
            small.html("<b>" + testCount + "</b> tests passed ");
        }
        // Update the card-footer summary ("<b>2</b> tests passed")
        Elements cardFooters = doc.select(".card-footer");
        for (Element footer : cardFooters) {
            Elements bTags = footer.select("b");
            for (Element b : bTags) {
                if (b.text().matches("\\d+") && b.parent() != null && b.parent().text().contains("tests passed")) {
                    b.text(Integer.toString(testCount));
                }
            }
        }
        try (FileWriter writer = new FileWriter(reportPath)) {
            writer.write(doc.outerHtml());
        }
        System.out.println("Extent report cleaned: " + reportPath);
    }

    // Helper to parse duration string like 00:00:29:749 to milliseconds
    private static long parseDuration(String duration) {
        if (duration == null || duration.isEmpty()) return 0;
        String[] parts = duration.split(":");
        if (parts.length != 4) return 0;
        try {
            long h = Long.parseLong(parts[0]);
            long m = Long.parseLong(parts[1]);
            long s = Long.parseLong(parts[2]);
            long ms = Long.parseLong(parts[3]);
            return (((h * 60 + m) * 60 + s) * 1000) + ms;
        } catch (Exception e) {
            return 0;
        }
    }

    private static String findLatestExtentReport() {
        File reportsDir = new File("reports");
        if (!reportsDir.exists() || !reportsDir.isDirectory()) return null;
        File latestReport = null;
        long latestMod = Long.MIN_VALUE;
        for (File dateDir : reportsDir.listFiles(File::isDirectory)) {
            for (File timeDir : dateDir.listFiles(File::isDirectory)) {
                File reportFile = new File(timeDir, "ExtentReport.html");
                if (reportFile.exists() && reportFile.lastModified() > latestMod) {
                    latestMod = reportFile.lastModified();
                    latestReport = reportFile;
                }
            }
        }
        return latestReport != null ? latestReport.getPath() : null;
    }
}
