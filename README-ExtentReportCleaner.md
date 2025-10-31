# ExtentReportCleaner Usage

After running your tests (e.g., with `mvn clean test`), the ExtentReportCleaner utility will automatically run in the Maven `verify` phase and clean up the `ExtentReport.html` file in the `target` directory.

**No manual steps are required.**

If you want to run the cleaner manually, you can use:

```
mvn exec:java -Dexec.mainClass="com.sabre.hotelbooker.utils.ExtentReportCleaner" -Dexec.args="target/ExtentReport.html"
```

This ensures your Extent report is always clean and free of duplicate/empty scenario entries after every test run.
