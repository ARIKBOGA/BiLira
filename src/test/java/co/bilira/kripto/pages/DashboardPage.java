package co.bilira.kripto.pages;

import co.bilira.kripto.utils.Driver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class DashboardPage {

    public DashboardPage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "//button[@data-testid='fiat-deposit-btn']")
    private WebElement fiatDepositButton;

    @FindBy(xpath = "//button[@data-testid='crypto-deposit-btn']")
    private WebElement cryptoDepositButton;

}
