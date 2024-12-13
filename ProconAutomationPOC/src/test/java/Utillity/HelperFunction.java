package Utillity;

import BusinessLogic.Businessfunction;
import PageLocators.LoginPageLocators;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static Utillity.BrowserControl.prop;
import static Utillity.WebDriverFactory.driver;

public class HelperFunction {

    public static HelperFunction INSTANCE = new HelperFunction();

    private static final Logger logger = LogManager.getLogger(HelperFunction.class.getName());
    public static Scenario scenario;
    static String testCaseID = "";
    public static String userName;
    public static String password;
    public static String inValiduserName;
    public static String inValidpassword;
    @Before
    public void startTest(Scenario scenario) throws IOException {
        HelperFunction.scenario =scenario;
        logger.info("Scenario called ");

        logger.info("###############################################################");
        System.out.println(scenario.getId());
        System.out.println(scenario.getName());
        System.out.println(scenario.getStatus());
        logger.info("Test case Name:"+scenario.getName());

        logger.info("###############################################################");
        System.out.println("\n");
        for (String tag : scenario.getSourceTagNames()) {
            if (tag.contains("TC")) {
                testCaseID = tag.replace("@", "");
                logger.debug("Test case Tag:{}", testCaseID);

            }
        }
        FileUtils.deleteDirectory(new File("reports"));
    }

    @After
    public void takeScreenshotOnFailure(Scenario scenario)
    {
            if (scenario.isFailed())
            {
                // Take screenshot as bytes
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // Embed the screenshot in the Cucumber report
                scenario.attach(screenshot, "image/png", scenario.getName());
            }

            // Quit the WebDriver after scenario
            if (driver != null) {
                driver.quit();
            }

    }


    public static void takeScreenshot() {

        if (!scenario.isFailed() && scenario!=null) {

// byte[] screenshot = getScreenshotAs(OutputType.BYTES);
                byte[] screenshot = ((TakesScreenshot) WebDriverFactory.driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png","Evidences");
            }

    }

    /**
     * Waits until the page is fully loaded.
     *
     * @param driver  The WebDriver instance.
     * @param timeout The maximum time to wait in seconds.
     */
    public static void waitForPageToLoad(WebDriver driver, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until((ExpectedCondition<Boolean>) wd ->
                    ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
            );
        } catch (Exception e) {
            System.out.println("Page did not load within " + timeout + " seconds: " + e.getMessage());
        }
    }

    /**
     * Waits for the visibility of an element located by the given locator.
     *
     * @param driver  The WebDriver instance.
     * @param element The WeBElemen locator of the element.
     * @param timeout The timeout in seconds.
     * @return The visible WebElement.
     */
    public static WebElement waitForElementVisibility(WebDriver driver, WebElement element, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static void userInfo() throws IOException {
        String filename = "src/test/resources/UserCred.properties";
        HelperFunction.class.getClassLoader().getResourceAsStream(filename);
        System.out.println(filename);
        prop.load(new FileInputStream(filename));
        userName = prop.getProperty("UserName");
        password = prop.getProperty("Password");
        inValiduserName = prop.getProperty("inValiduserName");
        inValidpassword = prop.getProperty("inValidpassword");
    }

    /**
     * Waits for the page to load completely by checking the document.readyState property.
     *
     * @param driver The WebDriver instance.
     */
    public static void waitForPageLoad(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 30; i++) { // Maximum wait of 30 seconds
            try {
                Thread.sleep(4000); // Check every 1 second
                if (js.executeScript("return document.readyState").equals("complete")) {
                    return;
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
        throw new RuntimeException("Page did not load within 30 seconds.");
    }


    /**
     * Highlights the given element by changing its style temporarily.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to highlight.
     */
    public static void highlightElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Save the original style of the element
        String originalStyle = element.getAttribute("style");
        try {
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                    element, "border: 2px solid green; background: yellow;");
            js.executeScript("arguments[0].style.border='3px solid green'", element);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Highlight interrupted: " + e.getMessage());
        }
    }

    /**
     * Performs mouse hover over a given web element.
     *
     * @param element The WebElement to hover over.
     */
    public static void hoverOverElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            System.out.println("Hovered over element: " + element.toString());
        } catch (Exception e) {
            System.out.println("Failed to hover over element: " + e.getMessage());
        }
    }
    /**
     * Calculates a future date based on the current date and adds a given number of days.
     *
     * @param daysToAdd The number of days to add to the current date.
     * @param format    The desired date format (e.g., "dd-MM-yyyy").
     * @return A formatted future date as a String.
     */
    public static String calculateFutureDate(int daysToAdd, String format) {
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Add the specified number of days
            LocalDate futureDate = currentDate.plusDays(daysToAdd);

            // Format the future date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return futureDate.format(formatter);
        } catch (Exception e) {
            System.out.println("Error calculating future date: " + e.getMessage());
            return null;
        }
    }

    /**
     * Selects a date in the date picker that is `daysInFuture` days from the current date.
     *
     * @param driver       The WebDriver instance.
     * @param datePickerLocator The locator for the date picker field.
     * @param dateToSelectLocator The locator for selecting dates in the date picker.
     * @param daysInFuture The number of days in the future to select.
     */
    public static void selectFutureDate(WebDriver driver, By datePickerLocator, By dateToSelectLocator, int daysInFuture) {
        try {
            // Calculate the future date
            LocalDate futureDate = LocalDate.now().plusDays(daysInFuture);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

            // Open the date picker
            WebElement datePicker = driver.findElement(datePickerLocator);
            datePicker.click();

            // Navigate and select the date (assumes dates are clickable elements)
            String futureDay = futureDate.format(formatter);
            WebElement futureDateElement = driver.findElement(By.xpath("//td[text()='" + futureDay + "']"));
            futureDateElement.click();

            System.out.println("Selected date: " + futureDate.toString());
        } catch (Exception e) {
            System.out.println("Failed to select future date: " + e.getMessage());
        }
    }

    /**
     * Scroll to a specific WebElement.
     * @param element The WebElement to scroll to.
     */
    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static HelperFunction getInstance() {
        return INSTANCE;
    }

}
