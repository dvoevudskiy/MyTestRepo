package framework;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverUtils {
    private static WebDriver driver = BasePage.driver;
    private static final int waitDuration = BasePage.settings.getWaitDuration();

    public static void waitForElementPresent(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitForElementClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void waitForAlert(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitDuration));
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static String getElementValue(WebElement element){
        return element.getAttribute("value");
    }
}
