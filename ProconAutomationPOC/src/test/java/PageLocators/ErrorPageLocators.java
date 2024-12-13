package PageLocators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPageLocators {

    @FindBy(css = "mat-error.mat-mdc-form-field-error.mat-mdc-form-field-bottom-align")
    public static WebElement errorlableRFQmode;
}
