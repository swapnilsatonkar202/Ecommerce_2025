package com.qa.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String currentReportPath; // ⭐ stores timestamp report

    // ===============================
    // 🚀 TEST EXECUTION START
    // ===============================
    @Override
    public void onStart(ITestContext context) {

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        currentReportPath = System.getProperty("user.dir")
                + "/reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark =
                new ExtentSparkReporter(currentReportPath);

        spark.config().setReportName("Automation Test Execution Report");
        spark.config().setDocumentTitle("Extent Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Project", "Ecommerce Automation");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Tester", "Swapnil");

        System.out.println("📘 Test Execution Started: " + context.getName());
    }

    // ===============================
    // 🧪 TEST START
    // ===============================
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    // ===============================
    // ✅ TEST PASS
    // ===============================
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS,
                "✅ Test Passed: " + result.getMethod().getMethodName());
    }

    // ===============================
    // ❌ TEST FAIL
    // ===============================
    @Override
    public void onTestFailure(ITestResult result) {

        test.get().log(Status.FAIL,
                "❌ Test Failed: " + result.getMethod().getMethodName());

        test.get().fail(result.getThrowable());

        String screenshotPath =
                takeScreenshot(result.getMethod().getMethodName());

        if (screenshotPath != null) {
            try {
                test.get().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===============================
    // ⚠️ TEST SKIP
    // ===============================
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP,
                "⚠️ Test Skipped: " + result.getMethod().getMethodName());
    }

    // ===============================
    // 🧹 TEST EXECUTION FINISH
    // ===============================
    @Override
    public void onFinish(ITestContext context) {

        if (extent != null) {
            extent.flush();
        }

        // ⭐ PERMANENT LATEST REPORT POINTER
        try {
            File src = new File(currentReportPath);
            File dest = new File(System.getProperty("user.dir")
                    + "/reports/LatestExtentReport.html");

            FileHandler.copy(src, dest); // overwrite every run
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("📗 Test Execution Completed: " + context.getName());
    }

    // ===============================
    // 📸 SCREENSHOT METHOD
    // ===============================
    private String takeScreenshot(String testName) {

        try {
            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            String screenshotDir =
                    System.getProperty("user.dir") + "/screenshots/";

            String screenshotPath =
                    screenshotDir + testName + "_" + timestamp + ".png";

            FileHandler.createDir(new File(screenshotDir));

            TakesScreenshot ts =
                    (TakesScreenshot) BaseTest.driver;

            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(screenshotPath);

            FileHandler.copy(src, dest);

            return screenshotPath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
