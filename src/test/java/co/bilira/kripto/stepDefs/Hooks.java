package co.bilira.kripto.stepDefs;

import co.bilira.kripto.utils.Driver;
import com.google.common.io.Files;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Hooks {

    /**
     * Close the driver after the scenario is finished.
     * If the scenario failed, take a screenshot and save it to the "screenshots" folder.
     *
     * @param scenario the scenario that just finished
     * @throws IOException if something goes wrong with taking the screenshot
     */
    @After
    public void teardownScenario(Scenario scenario) {
        try {
            // If the scenario failed, take a screenshot and save it to the "screenshots" folder
            if (scenario.isFailed()) {
                final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
                File screenshotFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
                Files.move(screenshotFile, new File("src/test/resources/screenshots/" + scenario.getName() + ".png"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to take screenshot at the end of the scenario: " + scenario.getName(), e);
        }

        // Close the driver
        Driver.closeDriver();
    }
}
