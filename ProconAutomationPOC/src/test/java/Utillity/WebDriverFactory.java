package Utillity;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public  static WebDriver driver;

    public static WebDriverFactory INSTANCE = new WebDriverFactory();

    public static WebDriverFactory getInstance() {
        return INSTANCE;
    }





}
