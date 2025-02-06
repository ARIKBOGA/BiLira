package co.bilira.kripto.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserUtils {

    private static final WebDriver DRIVER = Driver.getDriver();

    private static final WebDriverWait WAIT = new WebDriverWait(DRIVER, Duration.ofSeconds(10));

    private static final Actions ACTION = new Actions(DRIVER);

    /**
     * Pause the execution of the program for a given number of seconds.
     * This is a hard wait, meaning it will not check for any conditions
     * during the wait period.
     *
     * @param seconds the number of seconds to wait
     */
    public static void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wait until the given iframe is ready to be switched to. This is done
     * by waiting until the iframe is available and can be switched to.
     *
     * @param frameElement the iframe element to wait for
     */
    public static void waitForIframeReadyToBeSwitched(WebElement frameElement) {
        WAIT.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    /**
     * Waits until the iframe with the specified name is ready to be switched to.
     *
     * @param frameName the name of the iframe to wait for
     */
    public static void waitForIframeReadyToBeSwitched(String frameName) {
        // Wait until the iframe with the given name is available and switch to it
        WAIT.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
    }

    /**
     * Wait until the iframe with the specified index is ready to be switched to.
     *
     * @param frameIndex the index of the iframe to wait for
     */
    public static void waitForIframeReadyToBeSwitched(byte frameIndex) {
        // Wait until the iframe with the given index is available and switch to it
        WAIT.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    /**
     * Waits until the iframe located by the specified locator is ready to be switched to.
     * This is done by waiting until the iframe is available and can be switched to.
     *
     * @param frameLocator the locator of the iframe to wait for
     */
    public static void waitForIframeReadyToBeSwitched(By frameLocator) {
        // Wait until the iframe with the given locator is available and switch to it
        WAIT.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }


    /**
     * Wait until the given element is clickable. This is done by waiting
     * until the element is visible and enabled.
     *
     * @param element the element to wait for
     */
    public static void waitForElementToBeClickable(WebElement element) {
        WAIT.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait until the given element is visible. This is done by waiting
     * until the element is present in the DOM and visible.
     *
     * @param element the element to wait for
     */
    public static void waitForElementToBeVisible(WebElement element) {
        // wait until the element is visible
        WAIT.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait until the element is visible.
     * This is done by waiting until the element is present in the DOM and visible.
     *
     * @param locator the locator of the element to wait for
     */
    public static void waitForVisibilityOfElementLocated(By locator) {
        // wait until the element is visible
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait until the page is fully loaded.
     * This is done by waiting until the document readystate is "complete".
     * This is a more reliable way of waiting for the page to load than
     * waiting for a specific element on the page, as the page may be
     * fully loaded but an element not yet visible.
     */
    public static void waitForPageToLoad() {
        // wait until the document readystate is "complete"
        WAIT.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete';"));
    }

    public static void waitForTitleIs(String expectedTitle){
        WAIT.until(ExpectedConditions.titleIs(expectedTitle));
    }

    public static void waitForTitleContains(String title) {
        WAIT.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Scroll the view to the specified element.
     * This method uses Actions to move the cursor to the element,
     * ensuring it is brought into view.
     *
     * @param element the WebElement to scroll to
     */
    public static void goToElement(WebElement element) {
        // Use Actions to move to the specified element
        ACTION.moveToElement(element).build().perform();
        // wait for a short time so that the element can be visible
        hardWait(1);
    }


    /**
     * Drag and drop the specified element to the target using the Actions class.
     * This method can be used to perform a drag and drop action from one element to another.
     *
     * @param element the WebElement to drag
     * @param target  the WebElement to drop the element onto
     */
    public static void dragElementAndDropToTarget(WebElement element, WebElement target) {
        // use Actions to drag and drop the element onto the target
        ACTION.dragAndDrop(element, target) // drag the element to the target
                .pause(1000) // pause for 1 second to ensure the action is registered
                .build() // build the action
                .perform(); // perform the action
    }


    /**
     * Hover over the specified element.
     * This method uses Actions to move the cursor to the element
     * and pauses for a short duration to ensure the hover action is registered.
     *
     * @param element the WebElement to hover over
     */
    public static void hoverOverElement(WebElement element) {
        // Use Actions to move to the specified element and pause
        ACTION.moveToElement(element)
                .pause(1000) // Pause for 1 second to ensure the hover action is registered
                .build()
                .perform();
    }

    /**
     * Executes the given JavaScript code in the context of the current page.
     * This can be used to perform actions on the page that are not possible
     * with the standard Selenium API.
     *
     * @param s the JavaScript code to execute
     */
    public static void executeJS(String s) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript(s);
    }

    /**
     * Scrolls the view to the specified element using JavaScript.
     * This method executes the JavaScript code to scroll the element into view.
     *
     * @param element the WebElement to scroll to
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
