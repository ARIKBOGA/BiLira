package co.bilira.kripto.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "html:src/test/resources/reports/cucumber.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml"
        },
        features = "src/test/resources/features",
        glue = "co/bilira/kripto/stepDefs",
        dryRun = false,
        monochrome = true
)
public class TestRunner {
}
