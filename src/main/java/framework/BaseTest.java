package framework;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    private static Settings settings = new Settings();
    private static final String FAILED_SCREENSHOTS_PATH = "%s/%s_%s.jpg";
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public static void beforeSuite() {
        logger.info("Starting test execution");
        BasePage.driver = settings.getDriver();
        BasePage.settings = settings;
        BasePage.driver.get(settings.getAppUrl());
        BasePage.driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result);
        }
    }

    private void takeScreenshot(ITestResult result) {
        Date dateNow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String screenshotDirectory = Paths.get("src","test","screenshots").toFile().getAbsolutePath();
        String filePath = String.format(FAILED_SCREENSHOTS_PATH, screenshotDirectory, result.getName(), format.format(dateNow));
        logger.info("Screenshot will be saved to the following path: " + filePath);
        try {
            File scrFile = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite(alwaysRun = true)
    public static void afterClass() {
        BasePage.driver.quit();
    }
}
