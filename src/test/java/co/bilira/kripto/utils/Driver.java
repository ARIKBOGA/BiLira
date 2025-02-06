package co.bilira.kripto.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {

    private static final String BROWSER = ConfigReader.getProperty("browser");

    private static final InheritableThreadLocal<WebDriver> DRIVER_POOL = new InheritableThreadLocal<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (DRIVER_POOL.get() == null) {
            DRIVER_POOL.set(createDriver());
            setDriverDefaults(DRIVER_POOL.get());
        }
        return DRIVER_POOL.get();
    }

    private static WebDriver createDriver() {
        LOGGER.info("Thread ID: {}, Starting Driver initialization for browser: {}", Thread.currentThread().getId(), BROWSER);

        return switch (BROWSER.toLowerCase()) {
            case "remote" -> setupRemoteDriver();
            case "chrome" -> setupChromeDriver();
            case "firefox" -> setupFirefoxDriver();
            case "safari" -> setupSafariDriver();
            default -> {
                LOGGER.error("Unknown browser type : {}", BROWSER);
                throw new IllegalArgumentException("Unknown browser type : " + BROWSER);
            }
        };
    }

    private static WebDriver setupRemoteDriver() {

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome"); // Chrome will be used on remote
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL is invalid!", e);
        }
    }



    private static WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        String chromeArguments = ConfigReader.getProperty("chrome.arguments");
        if (chromeArguments != null && !chromeArguments.isEmpty()) {
            options.addArguments(chromeArguments.split(","));
        }
        String chromeOptions = ConfigReader.getProperty("chrome.options");
        if (chromeOptions != null && !chromeOptions.isEmpty()) {
            options.addArguments(chromeOptions.split(","));
        }
        // These options are used to avoid the browser being detected as a bot
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        return new ChromeDriver(options);
    }

    private static WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        String firefoxOptions = ConfigReader.getProperty("firefox.options");
        if (firefoxOptions != null && !firefoxOptions.isEmpty()) {
            options.addArguments(firefoxOptions.split(","));
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver setupSafariDriver() {
        return new SafariDriver();
    }

    private static void setDriverDefaults(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void closeDriver() {
        if (DRIVER_POOL.get() != null) {
            try {
                DRIVER_POOL.get().quit();
            } catch (Exception e) {
                LOGGER.error("Error occurred while closing the WebDriver instance", e);
            } finally {
                DRIVER_POOL.remove();
            }
        }
    }
}
