package PageLocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators {

    @FindBy(xpath = "(//*[@name='username'])")
    public static WebElement userNameTextBox;


    @FindBy(xpath = "//input[contains(@id,'password')]")
    public static WebElement PassTextBox;

    @FindBy(xpath = "(//*[contains(@class,'col-sm-12 col-md-7')])")
    public static WebElement LoginPageHome;

    @FindBy(xpath = "//input[contains(@id,'kc-login')]")
    public static WebElement loginBtn;

    @FindBy(xpath = " //span[@id='input-error']")
    public static WebElement erroLabel;



}
