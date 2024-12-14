package PageLocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPageLocators {

    @FindBy(xpath = "//span[@class='mdc-button__label']//span[1]")
    public static WebElement userProfile;
}
