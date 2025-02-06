package co.bilira.kripto.pages;

import co.bilira.kripto.utils.Driver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class AuthPage {

    public AuthPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(className = "input")
    private WebElement authCodeField;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement submitButton;
}
