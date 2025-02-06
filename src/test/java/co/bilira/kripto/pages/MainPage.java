package co.bilira.kripto.pages;

import co.bilira.kripto.utils.Driver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class MainPage {

    public MainPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//button[@data-testid='accept-all-cookies-btn']")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//a[@data-testid='login-btn']")
    private WebElement loginButton;
}
