package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {
    private static final String APP_URL = "appUrl";
    private static final String BROWSER = "browser";
    private static final String WAIT_DURATION = "waitDuration";
    private static final String TASK_PROPERTIES = "task.properties";

    private String appUrl;
    private BrowserType browser;
    private int waitDuration;
    private Properties properties = new Properties();

    public WebDriver getDriver() {
        return getDriver(browser);
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Integer getWaitDuration() {
        return waitDuration;
    }

    public Settings() {
        loadSettings();
    }

    private void loadSettings() {
        properties = loadPropertiesFile();
        appUrl = getPropertyOrThrowException(APP_URL);
        waitDuration = Integer.parseInt(getPropertyOrThrowException(WAIT_DURATION));
        browser = BrowserType.valueOf(getPropertyOrThrowException(BROWSER));
    }

    private WebDriver getDriver(BrowserType browserType) {
        String driversPath = getResourcesPath();

        switch (browserType) {
            case GC:
                System.setProperty("webdriver.chrome.driver", driversPath + "//chromedriver.exe");
                return new ChromeDriver();
            case FF:
                System.setProperty("webdriver.gecko.driver", driversPath + "//geckodriver.exe");
                return new FirefoxDriver();
            default:
                throw new UnsupportedBrowserException("This browser is not supported!");
        }
    }

    private String getResourcesPath(){
        Path resourceDirectory = Paths.get("src","main","resources");
        return resourceDirectory.toFile().getAbsolutePath();
    }

    private Properties loadPropertiesFile() {
        try {
            // get specified property file
            String filename = getPropertyOrNull(TASK_PROPERTIES);
            // it is not defined, use default
            if (filename == null) {
                filename = TASK_PROPERTIES;
            }
            // try to load from classpath
            InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
            // no file in classpath, look on disk
            if (stream == null) {
                stream = new FileInputStream(new File(filename));
            }
            Properties result = new Properties();
            result.load(stream);
            return result;
        } catch (IOException e) {
            throw new UnknownPropertyException("Property file is not found");
        }
    }

    private String getPropertyOrNull(String name) {
        return getProperty(name, false);
    }

    private String getPropertyOrThrowException(String name) {
        return getProperty(name, true);
    }

    private String getProperty(String name, boolean forceExceptionIfNotDefined) {
        String result;
        if ((result = System.getProperty(name, null)) != null && result.length() > 0) {
            return result;
        } else if ((result = getPropertyFromPropertiesFile(name)) != null && result.length() > 0) {
            return result;
        } else if (forceExceptionIfNotDefined) {
            throw new UnknownPropertyException("Unknown property: [" + name + "]");
        }
        return result;
    }

    private String getPropertyFromPropertiesFile(String name) {
        Object result = properties.get(name);
        if (result == null) {
            return null;
        } else {
            return result.toString();
        }
    }
}
