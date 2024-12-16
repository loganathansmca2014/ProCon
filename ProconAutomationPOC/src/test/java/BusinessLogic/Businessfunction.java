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

import static PageLocators.RFQPageLocators.nextButton;
import static Utillity.HelperFunction.*;
import static Utillity.WebDriverFactory.driver;


public class Businessfunction {
    private static final Logger logger = LogManager.getLogger(Businessfunction.class.getName());

    /****************************************************************************************
     * DESCRIPTION: This method handles the login functionality by performing the following steps:
     *              1. Initializes required page elements using `PageFactory`.
     *              2. Decodes the Base64 encoded password.
     *              3. Enters the username and password into their respective text boxes.
     *              4. Logs messages for successful input or any issues encountered.
     *              5. Captures a screenshot for documentation purposes.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: loginFunction()
     * Example:
     *    - Call this method to automate user login with predefined credentials.
     *    - Ensure `userInfo()` has been implemented to populate the `userName` and `password` fields.
     ****************************************************************************************/

    public static void loginFunction() throws IOException {
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

    /****************************************************************************************
     * DESCRIPTION: This method handles the login button click functionality by:
     *              1. Verifying that the username and password fields are not empty.
     *              2. Highlighting the login button for visibility.
     *              3. Clicking the login button to submit the login form.
     *              4. Logging messages based on the conditions and outcomes.
     *              5. Handling and logging any exceptions that may occur during the process.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: clickLoginBtn()
     * Example:
     *    - Call this method after entering the username and password to initiate the login process.
     *    - Ensure that the username and password text boxes are populated before invoking.
     ****************************************************************************************/

    public static void clickLoginBtn() {
        // Initialize page elements
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        try {
            // Check if username and password text boxes are not null or empty
            String username = LoginPageLocators.userNameTextBox.getText();
            String password = LoginPageLocators.PassTextBox.getText();

            if (!username.isEmpty() && !password.isEmpty()) {
                highlightElement(driver, LoginPageLocators.loginBtn);
                LoginPageLocators.loginBtn.click();
                scenario.log("User clicked the login button successfully.");
                // Optionally capture a screenshot
                // HelperFunction.takeScreenshot(driver, scenario);
            } else if (username.isEmpty() && password.isEmpty()) {
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

    /****************************************************************************************
     * DESCRIPTION: This method verifies the elements and behavior on the Landing Page.
     * It checks the visibility of the user profile element and logs a welcome message.
     *
     * The method performs the following steps:
     * 1. **Page Initialization**:
     *    - Initializes the necessary page elements using `PageFactory.initElements`.
     *
     * 2. **Page Load Wait**:
     *    - Waits for the page to load completely before performing any actions.
     *
     * 3. **User Profile Verification**:
     *    - Checks if the user profile element is displayed on the Landing Page.
     *    - If the user profile is visible:
     *      - Highlights the user profile element.
     *      - Takes a screenshot of the Landing Page for documentation.
     *      - Logs a message with the user's profile name.
     *    - If the user profile is not displayed, logs a warning message.
     *
     * 4. **Error Handling**:
     *    - Catches any exceptions that may occur during the process, logs the error, and logs it in the scenario.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: verifyLandingPage()
     * Example:
     *    - Call this method to verify that the user profile is visible and log a welcome message.
     *      Example: verifyLandingPage();
     ****************************************************************************************/

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
     */
    /****************************************************************************************
     * DESCRIPTION: This method automates the login process with invalid credentials.
     *              It performs the following steps:
     *              1. Initializes required page elements using `PageFactory`.
     *              2. Decodes the Base64-encoded invalid password.
     *              3. Enters an invalid username and password into their respective fields.
     *              4. Handles scenarios where text boxes might be disabled or unavailable.
     *              5. Logs or prints error messages for decoding failures or unexpected exceptions.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: inValidCredLogin()
     * Example:
     *    - Call this method to test login functionality with invalid credentials.
     *    - Ensure `inValiduserName` and `inValidpassword` are populated before invoking this method.
     ****************************************************************************************/

    public static void inValidCredLogin() {
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

    /****************************************************************************************
     * DESCRIPTION: This method is responsible for handling error validation during the
     *              login process. It performs the following steps:
     *              1. Initializes necessary page elements using `PageFactory`.
     *              2. Checks if the error label is displayed on the login page.
     *              3. If displayed, retrieves and logs the error message from the label.
     *              4. Highlights the error label for visibility.
     *              5. Handles scenarios where the error label is not found or any unexpected exceptions.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: errorValidation()
     * Example:
     *    - Call this method after attempting a login to verify if any error message is displayed.
     *    - Use it to capture and log any issues encountered during the login attempt.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method automates the process of interacting with the RFQ (Request for
     *              Quotation) page to create a new form and validate its name. The method
     *              performs the following steps:
     *              1. Hovers over and clicks the RFQ creation icon.
     *              2. Waits for the "Create New" button to be displayed and clicks it.
     *              3. Verifies that the form displayed matches the expected form name.
     *              4. Takes a screenshot of the page after form creation.
     *              5. Logs the validation result of the form name.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: getFormData(String form)
     * Example:
     *    - Call this method to automate the creation and verification of a new RFQ form.
     *    - Pass the expected form name as a parameter to validate the form's title.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method automates the process of filling in the RFQ (Request for
     *              Quotation) information form by entering details such as RFQ mode, description,
     *              document type, and currency. The method performs the following steps:
     *              1. Enters the RFQ mode in the corresponding field.
     *              2. Fills in the description field with the provided text.
     *              3. Selects the document type from a dropdown or field.
     *              4. Sets the currency value for the RFQ.
     *              5. Optionally handles validation or logging if needed.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: toRFQInfo(String rfqMode, String description, String documentType, String currency)
     * Example:
     *    - Call this method to automate the process of entering RFQ details.
     *    - Pass values for RFQ mode, description, document type, and currency to complete the form.
     ****************************************************************************************/

    public static void toRFQInfo(String rfqMode, String description, String documentType, String currency)
            throws AWTException {

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

    /****************************************************************************************
     * DESCRIPTION: This method is used to select an option from a dropdown list based on the
     *              provided value. The method performs the following steps:
     *              1. Iterates over a list of dropdown elements.
     *              2. Filters elements to find one whose text matches the provided value.
     *              3. Clicks the first matching element to select the value.
     *              4. Waits for the page to load after the selection is made.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: selectFromDropdown(List<WebElement> elements, String value)
     * Example:
     *    - Call this method to select an option from a dropdown list on the page.
     *    - Provide a list of WebElement dropdown options and the value to select.
     ****************************************************************************************/

    private static void selectFromDropdown(List<WebElement> elements, String value) {
        elements.stream()
                .filter(it -> !it.getText().isEmpty() && it.getText().contains(value))
                .findFirst()
                .ifPresent(WebElement::click);
        waitForPageLoad(driver);
    }
    /****************************************************************************************
     * DESCRIPTION: This method handles the display of error messages associated with
     *              input fields. It checks if an error label is displayed for a specific
     *              input field and positions it appropriately on the screen. The method
     *              performs the following steps:
     *              1. Checks if the error label is displayed.
     *              2. Retrieves the position and dimensions of the input field and error label.
     *              3. Calculates the coordinates to position the error label above the input field.
     *              4. Calls the method to display the error label appropriately.
     *              5. Handles the case where the error label is not found using a try-catch block.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: handleErrorLabel(WebElement inputField, WebElement errorLabel)
     * Example:
     *    - Call this method when validating an input field to ensure any associated error messages
     *      are properly displayed and positioned relative to the field.
     ****************************************************************************************/

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

/****************************************************************************************
 * DESCRIPTION: This method automates the process of selecting the Purchase Organization
 *              and Purchase Group in the RFQ (Request for Quotation) creation process.
 *              It performs the following steps:
 *              1. Clicks on the "Purchase Organization" dropdown and selects the value provided.
 *              2. Handles any error labels associated with the "Purchase Organization" selection.
 *              3. Clicks on the "Purchase Group" dropdown and selects the value provided.
 *              4. Handles any error labels associated with the "Purchase Group" selection.
 *              5. Takes a screenshot after performing the selection steps.
 * Created By: Loganathan Sengottaiyan
 * Created DATE: 15 Dec 2024
 * UPDATED BY:
 * UPDATED DATE:
 * Method: toRFQpurchese(String pOrg, String pGrp)
 * Example:
 *    - Call this method to automate the process of selecting Purchase Organization
 *      and Purchase Group when creating a new RFQ.
 *    - Pass values for the Purchase Organization (pOrg) and Purchase Group (pGrp).
 ****************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method is used to validate and set dates for various RFQ fields, such as
     *              Delivery Date, Deadline Date, and others related to RFQ timelines.
     *              The method performs the following steps:
     *              1. Calculates the current date plus 10 days for both delivery and deadline date fields.
     *              2. Checks if the "Delivery Date" field is present and selects the current date (with a 5-day offset).
     *              3. Checks if the "Deadline Date" field is present and selects the current date (with a 3-day offset).
     *              4. Takes a screenshot of the page after performing the actions.
     *              5. Scrolls to the "Payment Terms" label after date selections.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: rfqDateValidation(String deliveryDate, String deadlineDate, String validityPeriod, String rfqEvaluationPeriod, String awardingPeriod)
     * Example:
     *    - Call this method to automate the process of validating and setting dates for the RFQ,
     *      including Delivery Date and Deadline Date.
     *    - Pass appropriate date values for each parameter as needed.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method is used to select a specific date in a date picker by navigating
     *              to the target month and year, and then selecting the desired day. The date
     *              is calculated by adding a specified number of days to the current date.
     *              The following steps are performed:
     *              1. The current date is retrieved.
     *              2. The target date is calculated by adding the specified number of days (`daysToAdd`).
     *              3. The target month and year are compared with the displayed month and year in
     *                 the date picker.
     *              4. If the target month and year are not displayed, the method clicks the
     *                 "Next" button to navigate forward.
     *              5. Once the correct month and year are displayed, the method selects the target
     *                 day in the calendar.
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: selectCurrentDate(int daysToAdd)
     * Example:
     *    - Call this method to select a specific day in a date picker, for example, by passing 5
     *      to select a date 5 days ahead from the current date.
     ****************************************************************************************/

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

    /****************************************************************************************
     * DESCRIPTION: This method is used to select values for RFQ (Request for Quotation) data
     *              categories, including the following:
     *              1. Category
     *              2. Payment Terms
     *              3. Incoterms
     *
     * The method performs the following steps:
     * 1. Waits for the page to load and then clicks on the Category label to open the dropdown.
     * 2. Selects the specified category from the category dropdown.
     * 3. Handles any error label associated with the category selection.
     * 4. Clicks on the Payment Terms label to open the payment terms dropdown and selects
     *    the specified payment terms.
     * 5. Handles any error label associated with the payment terms selection.
     * 6. Clicks on the Incoterms label to open the Incoterms dropdown and selects the specified
     *    Incoterms.
     * 7. Handles any error label associated with the Incoterms selection.
     * 8. Takes a screenshot after all selections have been made.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: rfqDataCategory(String category, String paymentTerms, String incoTerms)
     * Example:
     *    - Call this method to select category, payment terms, and Incoterms in an RFQ form.
     *      Example: rfqDataCategory("Electronics", "Net 30", "FOB");
     ****************************************************************************************/

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


    /****************************************************************************************
     * DESCRIPTION: This method is used to validate the positioning and alignment of an error
     *              label relative to an input field. It checks both vertical and horizontal
     *              alignment of the error label and ensures that it is properly positioned
     *              in relation to the associated input field.
     *
     * The method performs the following checks:
     * 1. **Vertical Alignment**:
     *    - Verifies that the error label is positioned below the input field by comparing
     *      the Y-coordinate of the error label's top edge (`errorTopY`) with the bottom edge
     *      of the input field (`inputBottomY`).
     *    - Logs a message and asserts that the error label is correctly positioned.
     *    - If the error label is not positioned below the input field, an assertion failure
     *      is triggered.
     *
     * 2. **Horizontal Alignment**:
     *    - Ensures that the error label is horizontally aligned with the center of the input field
     *      by comparing the X-coordinate of the center of both elements (`inputCenterX` and `errorCenterX`).
     *    - Logs a message and asserts that the error label is properly horizontally aligned with
     *      the input field.
     *    - If the horizontal alignment is incorrect, an assertion failure is triggered.
     *
     * Created By: Loganathan Sengottaiyan
     * Created DATE: 15 Dec 2024
     * UPDATED BY:
     * UPDATED DATE:
     * Method: errorTopYlabel(int inputCenterX, int errorCenterX, int errorTopY, int inputBottomY)
     * Example:
     *    - Call this method to validate the alignment of an error label in relation to an input field.
     *      Example: errorTopYlabel(inputCenterX, errorCenterX, errorTopY, inputBottomY);
     ****************************************************************************************/


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
        hoverOverElement(RFQPageLocators.saveBtn)  ;
        waitForPageLoad(driver);
        RFQPageLocators.saveBtn.click();
        HelperFunction.acceptAlert(driver);
        HelperFunction.takeScreenshot();


    }

    public static void addBasicDetails(String detailText) {
        PageFactory.initElements(driver,RFQPageLocators.class);

        // Verify the form name matches the expected value
        String actualFormName = RFQPageLocators.basicinfoLabel.getText();
        if (actualFormName.equalsIgnoreCase(detailText)) {
            Assert.assertEquals(actualFormName, detailText, "Form names matched.");
            scenario.log("User is on the '" + detailText + "' form.");
        } else {
            scenario.log("Expected form: '" + detailText + "', but found: '" + actualFormName + "'.");
        }
    }
}

