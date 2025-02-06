package co.bilira.kripto.stepDefs;

import co.bilira.kripto.pages.AuthPage;
import co.bilira.kripto.pages.DashboardPage;
import co.bilira.kripto.pages.LoginPage;
import co.bilira.kripto.pages.MainPage;
import co.bilira.kripto.utils.BrowserUtils;
import co.bilira.kripto.utils.ConfigReader;
import co.bilira.kripto.utils.Driver;
import co.bilira.kripto.utils.SmsReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class Swap_steps {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AuthPage authPage = new AuthPage();
    private final DashboardPage dashboardPage = new DashboardPage();
    private final String email = ConfigReader.getProperty("email");
    private final String password = ConfigReader.getProperty("password");
    private final String authUrl = ConfigReader.getProperty("auth.url");


    /**
     * Log in to the system using the given email and password.
     * It waits until the page is fully loaded and checks if the current URL is the same as the auth URL.
     * Then it gets the verification code from an SMS and submits it.
     */
    @Given("I am logged into the platform")
    public void user_is_logged_in() {
        // Go to the main page
        Driver.getDriver().get(ConfigReader.getProperty("URL"));

        // Wait until the page is fully loaded
        BrowserUtils.waitForPageToLoad();

        // Accept cookies
        mainPage.getAcceptCookiesButton().click();

        // Click on the login button
        mainPage.getLoginButton().click();

        // Log in with the given email and password
        loginPage.login(email, password);

        // Wait until the auth page's auth code field is visible
        BrowserUtils.waitForElementToBeVisible(authPage.getAuthCodeField());

        // Wait for 10 seconds to allow the SMS to be received
        BrowserUtils.hardWait(10);

        // Check if the current URL is the same as the auth URL
        Assert.assertEquals(authUrl, Driver.getDriver().getCurrentUrl());

        // Get the verification code from an SMS
        String verificationCode = SmsReader.getVerificationCode();

        // Fill in the verification code
        authPage.getAuthCodeField().sendKeys(verificationCode);

        // Submit the verification code
        Driver.getDriver().findElement(By.xpath("//input[@type='submit']")).click();

        // Wait until the page is fully loaded
        BrowserUtils.waitForPageToLoad();

        BrowserUtils.waitForElementToBeClickable(dashboardPage.getCryptoDepositButton());
        BrowserUtils.waitForElementToBeClickable(dashboardPage.getFiatDepositButton());

        Assert.assertTrue(dashboardPage.getCryptoDepositButton().isDisplayed());
        Assert.assertTrue(dashboardPage.getCryptoDepositButton().isEnabled());
    }

    @And("I navigate to the Swap section")
    public void iNavigateToTheSwapSection() {
    }

    @When("I select {string} as the source currency")
    public void iSelectAsTheSourceCurrency(String arg0) {
    }

    @And("I select {string} as the target currency")
    public void iSelectAsTheTargetCurrency(String arg0) {
    }

    @And("I enter {string} as the amount")
    public void iEnterAsTheAmount(String arg0) {
    }

    @And("I confirm the swap")
    public void iConfirmTheSwap() {
    }

    @Then("I should see the updated wallet balances")
    public void iShouldSeeTheUpdatedWalletBalances() {
    }

    @And("the transaction should appear in the swap history.")
    public void theTransactionShouldAppearInTheSwapHistory() {
    }
}
