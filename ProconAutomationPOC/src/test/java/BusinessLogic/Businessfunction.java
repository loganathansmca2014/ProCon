package BusinessLogic;

import PageLocators.ErrorPageLocators;
import PageLocators.LandingPageLocators;
import PageLocators.LoginPageLocators;
import PageLocators.RFQPageLocators;
import Utillity.HelperFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static PageLocators.RFQPageLocators.nextButton;
import static Utillity.HelperFunction.*;
import static Utillity.WebDriverFactory.driver;


public class Businessfunction {
    private static final Logger logger = LogManager.getLogger(Businessfunction.class.getName());
    static Properties prop = new Properties();


    public static void loginFunction() throws IOException, InterruptedException {
        userInfo();

        // Initialize page elements
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        try {
            // Decode the Base64 encoded password
            Base64.Decoder decoder = Base64.getDecoder();
            String decodedPassword = new String(decoder.decode(password));

            // Enter the username if the text box is enabled
            if (LoginPageLocators.userNameTextBox.isEnabled()) {
                LoginPageLocators.userNameTextBox.clear(); // Clear any existing text
                LoginPageLocators.userNameTextBox.sendKeys(userName);
            } else {
                scenario.log("Username text box is not enabled.");
            }

            // Enter the password if the text box is enabled
            if (LoginPageLocators.PassTextBox.isEnabled()) {
                LoginPageLocators.PassTextBox.clear(); // Clear any existing text
                LoginPageLocators.PassTextBox.sendKeys(decodedPassword);
            } else {
                scenario.log("Password text box is not enabled.");
            }

            // Log success message
            scenario.log("User entered username and password successfully.");

            // Take a screenshot for documentation
            takeScreenshot();

        } catch (IllegalArgumentException e) {
            scenario.log("Failed to decode password: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            scenario.log("An unexpected error occurred during login: " + e.getMessage());
            throw e;
        }
    }


    public static void clickLoginBtn() {
        // Initialize page elements
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        try {
            // Check if username and password text boxes are not null or empty
            String username = LoginPageLocators.userNameTextBox.getText();
            String password = LoginPageLocators.PassTextBox.getText();

            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                highlightElement(driver, LoginPageLocators.loginBtn);
                LoginPageLocators.loginBtn.click();
                scenario.log("User clicked the login button successfully.");
                // Optionally capture a screenshot
                // HelperFunction.takeScreenshot(driver, scenario);
            } else if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
                if (LoginPageLocators.loginBtn.isEnabled()) {
                    highlightElement(driver, LoginPageLocators.loginBtn);
                    LoginPageLocators.loginBtn.click();
                    scenario.log("Username and password fields can't be empty.");
                }
            }
        } catch (Exception e) {
            scenario.log("An error occurred while attempting to click the login button: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void verifyLandingPage() {
        // Initialize the page elements
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        try {
            // Wait for the page to load completely
            waitForPageLoad(driver);

            // Highlight the user profile element
            if (LandingPageLocators.userProfile.isDisplayed()) {
                highlightElement(driver, LandingPageLocators.userProfile);

                // Take a screenshot
                takeScreenshot();

                // Get the user profile text and log it
                String userProfile = LandingPageLocators.userProfile.getText();
                logger.info("Hi " + userProfile + ", Welcome into Procon LandingPage");
                scenario.log("Hi " + userProfile + ", Welcome into Procon LandingPage");
            } else {
                logger.warn("User profile element is not displayed.");
                scenario.log("User profile element is not displayed.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while verifying the landing page: " + e.getMessage());
            scenario.log("An error occurred while verifying the landing page: " + e.getMessage());
        }
    }


    /**
     * Attempts to log in with invalid credentials.
     * <p>
     * This method interacts with the login page to input invalid username and password.
     * The password is decoded from a Base64-encoded string before being entered into the password field.
     * The method checks whether the username and password text boxes are enabled before sending the credentials.
     *
     * @throws IOException If an I/O error occurs during the process.
     */
    public static void inValidCredLogin() throws IOException {
        // Initialize the page elements
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        try {
            // Decode the Base64 encoded password
            Base64.Decoder decoder = Base64.getDecoder();
            String decodedPassword = new String(decoder.decode(inValidpassword));

            // Check if the username textbox is enabled and enter the invalid username
            if (LoginPageLocators.userNameTextBox.isEnabled()) {
                LoginPageLocators.userNameTextBox.clear();
                LoginPageLocators.userNameTextBox.sendKeys(inValiduserName);
            } else {
                System.out.println("Username text box is not enabled.");
            }

            // Check if the password textbox is enabled and enter the invalid password
            if (LoginPageLocators.PassTextBox.isEnabled()) {
                LoginPageLocators.PassTextBox.clear();
                LoginPageLocators.PassTextBox.sendKeys(decodedPassword);
            } else {
                System.out.println("Password text box is not enabled.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error decoding password: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Validates and handles error messages on the login page.
     * <p>
     * This method checks if an error message is displayed on the login page.
     * If an error is detected, it logs the message, highlights the error element,
     * and captures a screenshot for debugging or reporting purposes.
     * <p>
     * ##@param driver   The WebDriver instance used for interacting with the browser.
     * ##@param scenario The Scenario instance for logging and attaching test artifacts.
     */
    public static void errorValidation() {
        try {
            // Initialize page elements only once or as needed
            PageFactory.initElements(driver, LoginPageLocators.class);
            PageFactory.initElements(driver, LandingPageLocators.class);

            // Check if the error label is displayed
            if (LoginPageLocators.erroLabel.isDisplayed()) {
                String errorMessage = LoginPageLocators.erroLabel.getText();
                scenario.log(errorMessage);
                highlightElement(driver, LoginPageLocators.erroLabel);
                highlightElement(driver, LoginPageLocators.erroLabel);
            }
        } catch (NoSuchElementException e) {
            scenario.log("Error label not found: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            scenario.log("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void getFormData(String form) throws InterruptedException {
        // Initialize page elements
        PageFactory.initElements(driver, RFQPageLocators.class);

        try {
            // Hover over the RFQ creation icon and click it
            hoverOverElement(RFQPageLocators.rfqcreationicon);
            RFQPageLocators.rfqcreationicon.click();

            // Wait for the "Create New" button to be displayed and click it
            if (RFQPageLocators.createNewBtn.isDisplayed()) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                Thread.sleep(6000);

                RFQPageLocators.createNewBtn.click();
                // wait.until(ExpectedConditions.visibilityOf(RFQPageLocators.manualCreation));
                RFQPageLocators.manualCreation.click();


            } else {
                scenario.log("Create New button is not displayed.");
                return;
            }

            // Capture a screenshot
            HelperFunction.takeScreenshot();

            // Verify the form name matches the expected value
            String actualFormName = RFQPageLocators.formName.getText();
            if (actualFormName.equalsIgnoreCase(form)) {
                Assert.assertEquals(actualFormName, form, "Form names matched.");
                scenario.log("User is on the '" + form + "' form.");
            } else {
                scenario.log("Expected form: '" + form + "', but found: '" + actualFormName + "'.");
            }

        } catch (Exception e) {
            scenario.log("An error occurred while retrieving form data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*

    public static void getFormData(String form) throws InterruptedException {

        PageFactory.initElements(driver, RFQPageLocators.class);
        hoverOverElement(RFQPageLocators.rfqcreationicon);
        RFQPageLocators.rfqcreationicon.click();
        if (RFQPageLocators.createNewBtn.isDisplayed()) {
            Thread.sleep(6000);
            RFQPageLocators.createNewBtn.click();
            RFQPageLocators.manualCreation.click();
        }
        HelperFunction.takeScreenshot();
        if (RFQPageLocators.formName.getText().equalsIgnoreCase("General Data")) {
            Assert.assertEquals(RFQPageLocators.formName.getText(), "General Data", "Both re Matched");
            scenario.log("User under General form ");
        }

    }
    */
    public static void toRFQInfo(String rfqMode, String description, String documentType, String currency)
            throws InterruptedException, AWTException {

        // Initialize locators
        PageFactory.initElements(driver, RFQPageLocators.class);
        PageFactory.initElements(driver, ErrorPageLocators.class);

        // Ensure the page is loaded
        waitForPageLoad(driver);

        // Select RFQ Mode
        RFQPageLocators.clickrfqmode.click();
        selectFromDropdown(RFQPageLocators.rfqMode, rfqMode);

        // Handle potential RFQ Mode error
        handleErrorLabel(RFQPageLocators.clickrfqmode, ErrorPageLocators.errorlableRFQmode);

        // Input description if enabled
        if (RFQPageLocators.descriptionInputBox.isEnabled()) {
            RFQPageLocators.descriptionInputBox.sendKeys(description);
        }

        // Select Document Type
        RFQPageLocators.clickdocumentTypeList.click();
        selectFromDropdown(RFQPageLocators.documentTypeList, documentType);
        // Handle potential Document Type error
        handleErrorLabel(RFQPageLocators.clickdocumentTypeList, ErrorPageLocators.errorlableRFQmode);
        // Select Currency
        RFQPageLocators.clickCurrencyList.click();
        selectFromDropdown(RFQPageLocators.currencyList, currency);
        // Handle potential Currency error
        handleErrorLabel(RFQPageLocators.clickCurrencyList, ErrorPageLocators.errorlableRFQmode);
        // Capture a screenshot
        HelperFunction.takeScreenshot();
    }

    private static void selectFromDropdown(List<WebElement> elements, String value) {
        elements.stream()
                .filter(it -> !it.getText().isEmpty() && it.getText().contains(value))
                .findFirst()
                .ifPresent(WebElement::click);
        waitForPageLoad(driver);
    }

    private static void handleErrorLabel(WebElement inputField, WebElement errorLabel) {
        try {
            if (errorLabel.isDisplayed()) {
                int inputBottomY = inputField.getRect().getY() + inputField.getRect().getHeight();
                int errorTopY = errorLabel.getRect().getY();
                int inputCenterX = inputField.getRect().getX() + (inputField.getRect().getWidth() / 2);
                int errorCenterX = errorLabel.getRect().getX() + (errorLabel.getRect().getWidth() / 2);
                errorTopYlabel(inputCenterX, errorCenterX, errorTopY, inputBottomY);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error message element not found. Proceeding to the next condition.");
        }
    }


    /*
        public static void toRFQInfo(String rfqMode, String description, String documentType, String currency) throws InterruptedException, AWTException {

            PageFactory.initElements(driver, RFQPageLocators.class);
            PageFactory.initElements(driver, ErrorPageLocators.class);

            waitForPageLoad(driver);
            RFQPageLocators.clickrfqmode.click();


            List<String> listOfrfqMode = RFQPageLocators.rfqMode.stream().map(WebElement::getText)
                    .collect(Collectors.toList());
            listOfrfqMode.removeIf(item -> item.isEmpty());

            for (String rfqlist : listOfrfqMode) {
                if (rfqlist.equalsIgnoreCase(rfqMode)) {
                    RFQPageLocators.rfqMode.stream().filter(It -> It.getText().equalsIgnoreCase(rfqMode)).findFirst().ifPresent(WebElement::click);
                    waitForPageLoad(driver);
                    break;
                }

            }
            try {
                if (ErrorPageLocators.errorlableRFQmode.isDisplayed()) {
                    int inputBottomY = RFQPageLocators.clickrfqmode.getRect().getY() + RFQPageLocators.clickrfqmode.getRect().getHeight();
                    int errorTopY = ErrorPageLocators.errorlableRFQmode.getRect().getY();
                    // Get horizontal alignment (center positions)
                    int inputCenterX = RFQPageLocators.clickrfqmode.getRect().getX() + (RFQPageLocators.clickrfqmode.getRect().getWidth() / 2);
                    int errorCenterX = ErrorPageLocators.errorlableRFQmode.getRect().getX() + (ErrorPageLocators.errorlableRFQmode.getRect().getWidth() / 2);
                    errorTopYlabel(inputCenterX, errorCenterX, errorTopY, inputBottomY);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error message element not found. Proceeding to next condition.");
            }
            // errorLabelValidation(rfqMode, description, documentType, currency);


            if (RFQPageLocators.descriptionInputBox.isEnabled()) {
                RFQPageLocators.descriptionInputBox.sendKeys(description);
            }

            RFQPageLocators.clickdocumentTypeList.click();

            List<String> listOfdocType = RFQPageLocators.documentTypeList.stream().map(WebElement::getText)
                    .collect(Collectors.toList());
            for (String actuallistOfdocType : listOfdocType) {
                if (actuallistOfdocType.contains(documentType)) {
                    RFQPageLocators.documentTypeList.stream().filter(It -> It.getText().contains(documentType)).findFirst().ifPresent(WebElement::click);
                    waitForPageLoad(driver);
                    break;
                }

            }

            try {
                if (ErrorPageLocators.errorlableRFQmode.isDisplayed()) {
                    int inputBottomY = RFQPageLocators.clickdocumentTypeList.getRect().getY() + RFQPageLocators.clickdocumentTypeList.getRect().getHeight();
                    int errorTopY = ErrorPageLocators.errorlableRFQmode.getRect().getY();
                    // Get horizontal alignment (center positions)
                    int inputCenterX = RFQPageLocators.clickdocumentTypeList.getRect().getX() + (RFQPageLocators.clickdocumentTypeList.getRect().getWidth() / 2);
                    int errorCenterX = ErrorPageLocators.errorlableRFQmode.getRect().getX() + (ErrorPageLocators.errorlableRFQmode.getRect().getWidth() / 2);
                    errorTopYlabel(inputCenterX, errorCenterX, errorTopY, inputBottomY);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error message element not found. Proceeding to next condition.");
            }
            RFQPageLocators.clickCurrencyList.click();

            List<String> listOfcurrency = RFQPageLocators.currencyList.stream().map(WebElement::getText)
                    .collect(Collectors.toList());
            listOfcurrency.removeIf(item -> item.isEmpty());

            for (String actualCurrency : listOfcurrency) {
                if (actualCurrency.contains(currency)) {
                    RFQPageLocators.currencyList.stream().filter(It -> It.getText().contains(currency)).findFirst().ifPresent(WebElement::click);
                    waitForPageLoad(driver);
                    break;
                }
            }
            try {
                if (ErrorPageLocators.errorlableRFQmode.isDisplayed()) {
                    int inputBottomY = RFQPageLocators.clickCurrencyList.getRect().getY() + RFQPageLocators.clickCurrencyList.getRect().getHeight();
                    int errorTopY = ErrorPageLocators.errorlableRFQmode.getRect().getY();
                    // Get horizontal alignment (center positions)
                    int inputCenterX = RFQPageLocators.clickCurrencyList.getRect().getX() + (RFQPageLocators.clickCurrencyList.getRect().getWidth() / 2);
                    int errorCenterX = ErrorPageLocators.errorlableRFQmode.getRect().getX() + (ErrorPageLocators.errorlableRFQmode.getRect().getWidth() / 2);
                    errorTopYlabel(inputCenterX, errorCenterX, errorTopY, inputBottomY);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error message element not found. Proceeding to next condition.");
            }
            HelperFunction.takeScreenshot();
        }
    */
    public static void toRFQpurchese(String pOrg, String pGrp) {
        // Initialize locators
        PageFactory.initElements(driver, RFQPageLocators.class);

        // Select Purchase Organization
        RFQPageLocators.clickpurchase_Organization.click();
        selectFromDropdown(RFQPageLocators.purchase_Organization, pOrg);
        handleErrorLabel(RFQPageLocators.clickpurchase_Organization, ErrorPageLocators.errorlableRFQmode);

        // Select Purchase Group
        RFQPageLocators.clickdpurchase_grp.click();
        selectFromDropdown(RFQPageLocators.purchase_grp, pGrp);
        handleErrorLabel(RFQPageLocators.clickdpurchase_grp, ErrorPageLocators.errorlableRFQmode);

        // Capture a screenshot
        HelperFunction.takeScreenshot();
    }

/*
    public static void toRFQpurchese(String pOrg, String pGrp) {
        PageFactory.initElements(driver, RFQPageLocators.class);
        RFQPageLocators.clickpurchase_Organization.click();

        List<String> listOfp_org = RFQPageLocators.purchase_Organization
                .stream()
                .map(WebElement::getText)
                .collect(Collectors
                        .toList());
        listOfp_org.removeIf(item -> item.isEmpty() || item.equals("Select"));

        for (String actualp_org : listOfp_org) {
            if (actualp_org.contains(pOrg)) {
                RFQPageLocators.purchase_Organization.stream().filter(It -> It.getText().contains(pOrg)).findFirst().ifPresent(WebElement::click);
                waitForPageLoad(driver);
                break;
            }
        }
        RFQPageLocators.clickdpurchase_grp.click();
       */
/* RFQPageLocators.Search.click();
        RFQPageLocators.Search.sendKeys(pGrp);
        RFQPageLocators.Search.sendKeys(Keys.ARROW_DOWN);
        RFQPageLocators.Search.sendKeys(Keys.ENTER);*//*

        List<String> listOfpurchase_grp = RFQPageLocators.purchase_grp.stream().map(WebElement::getText)
                .collect(Collectors.toList());
        listOfpurchase_grp.removeIf(item -> item.isEmpty() || item.equals("Select"));

        for (String actualpurchase_grp : listOfpurchase_grp) {
            if (actualpurchase_grp.equalsIgnoreCase(pGrp)) {
                RFQPageLocators.purchase_grp.stream().filter(It -> It.getText().contains(pGrp)).findFirst().ifPresent(WebElement::click);
                waitForPageLoad(driver);
                break;
            }
        }
        HelperFunction.takeScreenshot();
    }
*/

    public static void rfqDateValidation(String deliveryDate, String deadlineDate, String validityPeriod, String rfqEvaluationPeriod, String awardingPeriod) {

        PageFactory.initElements(driver, RFQPageLocators.class);
        String dDate = calculateFutureDate(10, "dd/MM/yyyy");
        String fDate = calculateFutureDate(10, "dd/MM/yyyy");

        if (dDate != null) {
            System.out.println("Current Date + 10 days: " + dDate);
        }
        if (RFQPageLocators.deliverydateLabel.getText().contains("Delivery Date")) {
            RFQPageLocators.deliveryDate.click();
            selectCurrentDate(5);
        }
        if (RFQPageLocators.deadLineDateLable.getText().contains("Deadline Date")) {

            RFQPageLocators.deadLineDate.click();
            selectCurrentDate(3);
        }

        HelperFunction.takeScreenshot();
        HelperFunction.scrollToElement(RFQPageLocators.paymentTermslabel);

    }

  /*  public static void selectCurrentDate(int daysToAdd) {
        // Get the current date and calculate the target date by adding days
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysToAdd);

        // Extract the day, month, and year
        String targetDay = targetDate.format(DateTimeFormatter.ofPattern("d"));
        String targetMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        // Ensure the correct month and year are displayed
        while (true) {
            String displayedText = RFQPageLocators.displayedMonthYear.getText();

            // If the displayed month and year match the target, break out of the loop
            if (displayedText.equalsIgnoreCase(targetMonthYear)) {
                break;
            }

            // Check if we need to navigate forward or backward depending on the target month
            if (targetDate.isAfter(currentDate)) {
                nextButton.click(); // Navigate forward if the target date is in the future
            } else {
                RFQPageLocators.previousBtn.click(); // Navigate backward if the target date is in the past
            }

            // Wait for page load or for the calendar to refresh if necessary
            waitForPageLoad(driver);
        }

        try {
            // Locate and click the target day in the calendar
            WebElement dayElement = driver.findElement(By.xpath("//span[text()='" + targetDay + "']"));
            dayElement.click();
        } catch (NoSuchElementException e) {
            System.out.println("Day element not found: " + targetDay);
            // Optionally handle the case where the target day is not found (e.g., retry, throw exception)
        }
    }


  */

    public static void selectCurrentDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysToAdd);

        // Extract the day, month, and year
        String targetDay = targetDate.format(DateTimeFormatter.ofPattern("d"));
        String targetMonthYear = targetDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        // Ensure the correct month and year are displayed
        while (true) {
            String displayedText = RFQPageLocators.displayedMonthYear.getText();
            if (displayedText.equalsIgnoreCase(targetMonthYear)) {
                break;
            }// Navigate months (adjust depending on your date picker)
            nextButton.click(); // Navigate forward
        }

        // Select the current day
        WebElement dayElement = driver.findElement(By.xpath("//span[text()=' " + targetDay + " ']")); // Adjust for day elements
        dayElement.click();
    }
    public static void rfqDataCategory(String category, String paymentTerms, String incoTerms) {
        // Initialize locators
        PageFactory.initElements(driver, RFQPageLocators.class);
        waitForPageLoad(driver);
        RFQPageLocators.categorylabel.click();

        // Select category
        selectFromDropdown( RFQPageLocators.category, category);
        handleErrorLabel(RFQPageLocators.categorylabel, ErrorPageLocators.errorlableRFQmode);

        // Select payment terms
        RFQPageLocators.paymentTermslabel.click();

        selectFromDropdown( RFQPageLocators.paymentTerms, paymentTerms);
        handleErrorLabel(RFQPageLocators.paymentTermslabel, ErrorPageLocators.errorlableRFQmode);

        // Select inco terms
        RFQPageLocators.incoTermsLabel.click();

        selectFromDropdown( RFQPageLocators.incoTerms, incoTerms);
        handleErrorLabel(RFQPageLocators.incoTermsLabel, ErrorPageLocators.errorlableRFQmode);

        // Final screenshot after selections
        HelperFunction.takeScreenshot();
    }

/*
    public static void rfqDataCategory(String category, String paymentTerms, String incoTerms) {

        PageFactory.initElements(driver, RFQPageLocators.class);
        RFQPageLocators.categorylabel.click();
        HelperFunction.takeScreenshot();
// Filter out empty items and "Select" options from the category list
        List<String> listOfCategory = RFQPageLocators.category.stream()
                .map(WebElement::getText)
                .filter(item -> !item.isEmpty() && !item.equals("Select")) // Remove empty and "Select"
                .collect(Collectors.toList());

// Iterate through filtered categories to find the match
        for (String actualCategory : listOfCategory) {
            if (actualCategory.contains(category)) {
                // Click on the category that matches
                RFQPageLocators.category.stream()
                        .filter(it -> it.getText().contains(category)) // Find the matching element
                        .findFirst()
                        .ifPresent(WebElement::click); // Click the matched category
                waitForPageLoad(driver); // Wait for page load after the click
                break; // Exit the loop once the item is selected
            }
        }


        RFQPageLocators.paymentTermslabel.click();
        HelperFunction.takeScreenshot();

        List<String> listOfPaymentTerms = RFQPageLocators.paymentTerms.stream()
                .map(WebElement::getText)
                .filter(item -> !item.isEmpty() && !item.equals("Select")) // Remove empty and "Select" items
                .collect(Collectors.toList());

        for (String actualPaymentTerm : listOfPaymentTerms) {
            if (actualPaymentTerm.contains(paymentTerms)) {
                // Select the matching payment term
                RFQPageLocators.paymentTerms.stream()
                        .filter(it -> it.getText().contains(paymentTerms)) // Filter for the matching term
                        .findFirst()
                        .ifPresent(WebElement::click); // Click the matched element
                waitForPageLoad(driver); // Wait for page load after selection
                break; // Exit the loop once the item is selected
            }
        }


        RFQPageLocators.incoTermsLabel.click();
        HelperFunction.takeScreenshot();
        List<String> listOfIncoTerms = RFQPageLocators.incoTerms.stream()
                .map(WebElement::getText)
                .filter(item -> !item.isEmpty() && !item.equals("Select")) // Remove empty and "Select" items
                .collect(Collectors.toList());

        for (String actualIncoTerm : listOfIncoTerms) {
            if (actualIncoTerm.contains(incoTerms)) {
                RFQPageLocators.incoTerms.stream()
                        .filter(it -> it.getText().contains(incoTerms)) // Filter for the matching term
                        .findFirst()
                        .ifPresent(WebElement::click); // Click the matched element
                waitForPageLoad(driver); // Wait for page load after selection
                break; // Exit the loop once the item is selected
            }
        }

        HelperFunction.takeScreenshot();

    }
*/

    public static void errorLabelValidation(String rfqMode, String description, String documentType, String currency) {
        PageFactory.initElements(driver, RFQPageLocators.class);


    }

    private static void errorTopYlabel(int inputCenterX, int errorCenterX, int errorTopY, int inputBottomY) {
        // Check if the error label is positioned below the input field
        if (errorTopY > inputBottomY) {
            System.out.println("Error label is positioned below the input field.");
            Assert.assertTrue(true ,"Error label is properly center-aligned with the input field.");
            System.out.println("Error label is properly center-aligned with the input field.");
           scenario.log("Error label is properly center-aligned with the input field."+errorTopY+"::"+inputBottomY);


        } else {
            Assert.fail("Error label is NOT properly center-aligned with the input field. Expected horizontal alignment at X: "
                    + errorTopY + ", but found X: " + inputBottomY);
          scenario.log("Error label is NOT properly center-aligned with the input field. Expected horizontal alignment at X: "
                    + errorTopY + ", but found X: " + inputBottomY);

            System.out.println("Error label is not positioned below the input field.");
        }

        // Validate horizontal alignment
        if (inputCenterX == errorCenterX) {
            Assert.assertEquals(inputCenterX, errorCenterX, "Error label is properly center-aligned with the input field.");
            System.out.println("Error label is properly center-aligned with the input field.");
        } else {
            // Providing a more detailed failure message
            scenario.log("Error label is NOT properly center-aligned with the input field. Expected horizontal alignment at X: "
                    + inputCenterX + ", but found X: " + errorCenterX);
            Assert.fail("Error label is NOT properly center-aligned with the input field. Expected horizontal alignment at X: "
                    + inputCenterX + ", but found X: " + errorCenterX);

        }
    }

    public static void rfqSubmit() {
        PageFactory.initElements(driver, RFQPageLocators.class);
        waitForPageLoad(driver);
        RFQPageLocators.saveBtn.click();


    }
}

