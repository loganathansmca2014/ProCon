package Utillity;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    /****************************************************************************************
     * DESCRIPTION: This method captures a screenshot if the test scenario fails and attaches
     * the screenshot to the Cucumber report. It also ensures that the WebDriver is properly
     * quit after the test scenario execution, whether it passes or fails.
     * <p>
     * The method performs the following actions:
     * 1. **Check Scenario Status**:
     *    - The method checks if the scenario has failed using `scenario.isFailed()`.
     * <p>
     * 2. **Capture Screenshot**:
     *    - If the scenario has failed, the method captures a screenshot of the current page
     *      as a byte array using `getScreenshotAs` from the `TakesScreenshot` interface of the WebDriver.
     * <p>
     * 3. **Attach Screenshot to Scenario**:
     *    - The screenshot (as byte data) is then attached to the test scenario using `scenario.attach()`.
     *    - The screenshot is embedded into the Cucumber report with the mime type `"image/png"` and
     *      the scenario name as the label.
     * <p>
     * 4. **Quit WebDriver**:
     *    - After the scenario completes, the WebDriver instance is properly quit using `driver.quit()`
     *      to close all associated windows and free up resources.
     * <p>
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: takeScreenshotOnFailure(Scenario scenario)
     * Example:
     *    - This method is automatically invoked after each scenario. If the scenario fails,
     *      a screenshot will be taken and attached to the Cucumber report. Additionally, the
     *      WebDriver is quit after the scenario execution.
     *      Example: No need to manually call, as this is a Cucumber `@After` hook.
     ****************************************************************************************/

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
        File reportOutputDirectory = new File("reports");

// Ensure the directory exists, create if it doesn't
        if (reportOutputDirectory.exists()) {
            FileUtils.deleteDirectory(new File("reports"));
                logger.info("Report directory deleted successfully.");
            } else {
                logger.warn("Report directory not exists or could not be created.");
            }

    }
    /****************************************************************************************
     * DESCRIPTION: This method captures a screenshot if the test scenario fails and attaches
     * the screenshot to the Cucumber report. It also ensures that the WebDriver is properly
     * quit after the test scenario execution, whether it passes or fails.
     * <p>
     * The method performs the following actions:
     * 1. **Check Scenario Status**:
     *    - The method checks if the scenario has failed using `scenario.isFailed()`.
     * <p>
     * 2. **Capture Screenshot**:
     *    - If the scenario has failed, the method captures a screenshot of the current page
     *      as a byte array using `getScreenshotAs` from the `TakesScreenshot` interface of the WebDriver.
     * <p>
     * 3. **Attach Screenshot to Scenario**:
     *    - The screenshot (as byte data) is then attached to the test scenario using `scenario.attach()`.
     *    - The screenshot is embedded into the Cucumber report with the mime type `"image/png"` and
     *      the scenario name as the label.
     *
     * 4. **Quit WebDriver**:
     *    - After the scenario completes, the WebDriver instance is properly quit using `driver.quit()`
     *      to close all associated windows and free up resources.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: takeScreenshotOnFailure(Scenario scenario)
     * Example:
     *    - This method is automatically invoked after each scenario. If the scenario fails,
     *      a screenshot will be taken and attached to the Cucumber report. Additionally, the
     *      WebDriver is quit after the scenario execution.
     *      Example: No need to manually call, as this is a Cucumber `@After` hook.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method captures a screenshot of the current page and attaches it
     * to the test scenario in the report if the scenario has not failed. The screenshot is
     * saved in PNG format and is included in the test report under the section "Evidences".
     *
     * The method performs the following actions:
     * 1. **Scenario Check**:
     *    - The method checks if the test scenario has not failed (`!scenario.isFailed()`)
     *      and if the `scenario` object is not `null`.
     *
     * 2. **Capture Screenshot**:
     *    - If the scenario is successful, the method captures a screenshot by invoking
     *      `getScreenshotAs` from the `TakesScreenshot` interface of the WebDriver.
     *    - The screenshot is captured as a byte array (`byte[]`).
     *
     * 3. **Attach Screenshot to Scenario**:
     *    - The screenshot (as byte data) is attached to the test scenario using
     *      `scenario.attach()`, with the mime type `"image/png"`. This makes the screenshot
     *      appear in the test report under the section labeled "Evidences".
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: takeScreenshot()
     * Example:
     *    - Call this method to capture a screenshot and attach it to the test scenario when
     *      the scenario has not failed.
     *      Example: takeScreenshot();
     ****************************************************************************************/

    public static void takeScreenshot() {

        if (!scenario.isFailed() && scenario!=null) {

// byte[] screenshot = getScreenshotAs(OutputType.BYTES);
                byte[] screenshot = ((TakesScreenshot) WebDriverFactory.driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png","Evidences");
            }

    }

    /****************************************************************************************
     * DESCRIPTION: This method waits for a web page to completely load within the specified
     * timeout duration. It uses JavaScript to check if the document's `readyState` is "complete",
     * indicating that the page has fully loaded.
     *
     * The method performs the following steps:
     * 1. **WebDriverWait Initialization**:
     *    - A `WebDriverWait` instance is created using the provided `driver` and `timeout`.
     *
     * 2. **Wait for Page Load**:
     *    - The method waits for the page to be fully loaded by executing a JavaScript command
     *      that checks the `document.readyState`.
     *    - It waits until the value of `document.readyState` is equal to "complete",
     *      indicating that the page has finished loading.
     *
     * 3. **Error Handling**:
     *    - If the page does not load within the specified timeout, the method catches the exception
     *      and prints a message indicating that the page failed to load within the given time.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: waitForPageToLoad(driver, timeout)
     * Example:
     *    - Call this method to ensure a page has fully loaded within a given timeout.
     *      Example: waitForPageToLoad(driver, Duration.ofSeconds(30));
     ****************************************************************************************/

    public static void waitForPageToLoad(WebDriver driver, Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until((ExpectedCondition<Boolean>) wd ->
                    {
                        assert wd != null;
                        return ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete");
                    }
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

    /****************************************************************************************
     * DESCRIPTION: This method waits for the page to load completely by checking the
     * `document.readyState` property. It waits for the page to reach the 'complete' state.
     *
     * The method performs the following actions:
     * 1. **Wait for Page Load**:
     *    - It uses a `JavascriptExecutor` to execute JavaScript on the browser and checks the value of `document.readyState`.
     *    - The method will wait until the page load state becomes 'complete'.
     *
     * 2. **Polling Every Second**:
     *    - The method checks the page load status every 2 seconds, up to a maximum of 30 seconds.
     *    - If the page is not fully loaded within 30 seconds, it throws a `RuntimeException`.
     *
     * 3. **Handling Interruptions**:
     *    - If the `Thread.sleep()` method is interrupted during the wait, it logs the error message.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: waitForPageLoad(WebDriver driver)
     * Example:
     *    - This method can be used in Selenium-based tests to ensure that the page has finished loading
     *      before proceeding with further actions. Example usage: `waitForPageLoad(driver)`.
     *
     * @param driver - The WebDriver instance used to interact with the browser.
     * @throws RuntimeException if the page does not load within 30 seconds.
     ****************************************************************************************/

    public static void waitForPageLoad(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 30; i++) { // Maximum wait of 30 seconds
            try {
                Thread.sleep(2000); // Check every 1 second
                if (js.executeScript("return document.readyState").equals("complete")) {
                    return;
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
        throw new RuntimeException("Page did not load within 30 seconds.");
    }


    /****************************************************************************************
     * DESCRIPTION: This method highlights a given web element by changing its style temporarily.
     * It modifies the element's CSS to display a green border and a yellow background, making it
     * stand out visually for better visibility during automation tests or debugging sessions.
     *
     * The method performs the following actions:
     * 1. **Original Style Preservation**:
     *    - The original style of the element is saved so that it can be restored after highlighting.
     *
     * 2. **Style Modification**:
     *    - The method uses JavaScript (via `JavascriptExecutor`) to modify the element's CSS properties.
     *    - It sets a green border and a yellow background for the highlighted effect.
     *
     * 3. **Sleep for Highlighting**:
     *    - The element is highlighted for 500 milliseconds using `Thread.sleep()` to ensure the effect is visible.
     *
     * 4. **Restoring Original Style**:
     *    - The original style of the element is not restored in the current method, but it could be if desired.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: highlightElement(WebDriver driver, WebElement element)
     * Example:
     *    - This method can be used in Selenium-based tests when you want to visually highlight an
     *      element on the page for debugging purposes. Example usage: `highlightElement(driver, element);`.
     *
     * @param driver - The WebDriver instance used to interact with the browser.
     * @param element - The WebElement that you want to highlight.
     * @throws InterruptedException if the thread sleep is interrupted during the highlight process.
     ****************************************************************************************/

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
    /****************************************************************************************
     * DESCRIPTION: This method performs a hover action on a given web element using Selenium's
     * Actions class. The method simulates the mouse movement to the element, causing a hover
     * effect. This is particularly useful when interacting with elements that are revealed
     * or activated upon hover, such as dropdown menus or tooltips.
     *
     * The method does the following:
     * 1. **Actions Object**: The method creates an instance of the `Actions` class, which provides
     *    several advanced user interaction capabilities such as moving the mouse to an element.
     *
     * 2. **Move to Element**: The `moveToElement()` method of `Actions` is used to simulate
     *    the mouse hover over the specified element.
     *
     * 3. **Perform Action**: The `perform()` method is called to actually perform the hover action.
     *
     * 4. **Error Handling**: If an error occurs during the hover action (e.g., the element is
     *    not found or an interaction issue), an exception is caught, and an error message is logged.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: hoverOverElement(WebElement element)
     * Example:
     *    - This method can be used when you need to hover over an element to trigger a dropdown
     *      or reveal additional options. Example usage: `hoverOverElement(driver, element);`.
     *
     * @param element - The WebElement that you want to hover over.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method scrolls the page to bring a specific web element into view
     * using JavaScript execution. It uses the `scrollIntoView()` method, which is a JavaScript
     * function that scrolls the browser window to ensure the element is visible to the user.
     * This is helpful when an element is not in the current viewport, and you need to scroll
     * the page to interact with it.
     *
     * The method does the following:
     * 1. **JavascriptExecutor**: The method casts the WebDriver instance (`driver`) to a `JavascriptExecutor`,
     *    which allows executing JavaScript code within the context of the browser.
     *
     * 2. **Scroll Into View**: The method executes JavaScript to scroll the given element into view
     *    by calling `scrollIntoView(true)`. This ensures the element is visible in the viewport,
     *    bringing it to the top of the screen.
     *
     * 3. **Element Visibility**: After executing the script, the page is scrolled so that the element is
     *    within the visible portion of the browser window.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: scrollToElement(WebElement element)
     * Example:
     *    - This method can be used when the element is located outside the current viewport
     *      and you need to scroll to it. Example usage: `scrollToElement(driver, someElement);`.
     *
     * @param element - The WebElement that needs to be scrolled into view.
     ****************************************************************************************/

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Method to accept the alert
    public static void acceptAlert(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();  // Accept the alert
        } catch (Exception e) {
            System.out.println("No alert present to accept.");
        }
    }

    // Method to dismiss the alert
    public static void dismissAlert(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();  // Dismiss the alert
        } catch (Exception e) {
            System.out.println("No alert present to dismiss.");
        }
    }

    // Method to get the alert text (optional, for scenarios where you need the text)
    public static String getAlertText(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();  // Retrieve the alert text
        } catch (Exception e) {
            System.out.println("No alert present to retrieve text.");
            return null;
        }
    }

    // Method to send keys to an alert (useful for prompt dialogs)
    public static void sendKeysToAlert(WebDriver driver, String text) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);  // Send text to the alert (for prompts)
        } catch (Exception e) {
            System.out.println("No prompt alert present to send keys.");
        }
    }
    public static HelperFunction getInstance() {
        return INSTANCE;
    }

}
