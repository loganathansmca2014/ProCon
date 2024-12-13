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
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
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


    public static void loginfunction() throws IOException, InterruptedException {
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        userInfo();
        Base64.Decoder decoder = Base64.getDecoder();
        String dpassword = new String(decoder.decode(password));
        if (LoginPageLocators.userNameTextBox.isEnabled()) {
            LoginPageLocators.userNameTextBox.sendKeys(userName);
        }
        if (LoginPageLocators.PassTextBox.isEnabled()) {
            LoginPageLocators.PassTextBox.sendKeys(dpassword);
        }
        scenario.log("User has been entered username and password successfully");
        takeScreenshot();
    }


    public static void clickLoginBtn() {
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        if (LoginPageLocators.userNameTextBox.getText() != null && LoginPageLocators.PassTextBox.getText() != null) {
            highlightElement(driver, LoginPageLocators.loginBtn);
            LoginPageLocators.loginBtn.click();
            scenario.log("User clicked login button successfully");
            //  HelperFunction.takeScreenshot();

        } else if (LoginPageLocators.userNameTextBox.getText().isEmpty() && LoginPageLocators.PassTextBox.getText().isEmpty()) {
            if (LoginPageLocators.loginBtn.isEnabled()) {
                highlightElement(driver, LoginPageLocators.loginBtn);
                LoginPageLocators.loginBtn.click();
                scenario.log("Username and password filed can't be empty");


            }

        }
       /* if (LoginPageLocators.erroLabel.isDisplayed()) {
            scenario.log(LoginPageLocators.erroLabel.getText());
            highlightElement(driver,LoginPageLocators.erroLabel);
            takeScreenshot();
        }*/

    }

    public static void verifyLandingPage() {
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);
        waitForPageLoad(driver);
        highlightElement(driver, LandingPageLocators.userProfile);
        takeScreenshot();
        String userProfile = LandingPageLocators.userProfile.getText();
        logger.info("Hi " + userProfile + " Welcome into Procon LandingPage");
        scenario.log("Hi " + userProfile + " Welcome into Procon LandingPage");
    }

    public static void inValidCredLogin() throws IOException {
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);

        userInfo();
        Base64.Decoder decoder = Base64.getDecoder();
        String invapassword = new String(decoder.decode(inValidpassword));
        if (LoginPageLocators.userNameTextBox.isEnabled()) {
            LoginPageLocators.userNameTextBox.sendKeys(inValiduserName);
        }
        if (LoginPageLocators.PassTextBox.isEnabled()) {
            LoginPageLocators.PassTextBox.sendKeys(invapassword);
        }

    }

    public static void errorValidation() {
        PageFactory.initElements(driver, LoginPageLocators.class);
        PageFactory.initElements(driver, LandingPageLocators.class);


        if (LoginPageLocators.erroLabel.isDisplayed()) {
            scenario.log(LoginPageLocators.erroLabel.getText());
            highlightElement(driver, LoginPageLocators.erroLabel);
            takeScreenshot();
        }
    }

    public static void getFormData(String form) throws InterruptedException {

        PageFactory.initElements(driver, RFQPageLocators.class);
        hoverOverElement(RFQPageLocators.rfqcreationicon);
        RFQPageLocators.rfqcreationicon.click();
        if (RFQPageLocators.createNewBtn.isDisplayed()) {
            Thread.sleep(6000);
            RFQPageLocators.createNewBtn.click();
        }
        HelperFunction.takeScreenshot();
        if (RFQPageLocators.formName.getText().equalsIgnoreCase("General Data")) {
            Assert.assertEquals(RFQPageLocators.formName.getText(), "General Data", "Both re Matched");
            scenario.log("User under General form ");
        }

    }

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
        /*RFQPageLocators.Search.click();
        RFQPageLocators.Search.sendKeys(currency);
        RFQPageLocators.Search.sendKeys(Keys.ARROW_DOWN);
        RFQPageLocators.Search.sendKeys(Keys.ENTER);*/

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

    public static void toRFQpurchese(String pOrg, String pGrp) {
        PageFactory.initElements(driver, RFQPageLocators.class);

        RFQPageLocators.clickpurchase_Organization.click();
      /*  RFQPageLocators.Search.click();
        RFQPageLocators.Search.sendKeys(pOrg);
        RFQPageLocators.Search.sendKeys(Keys.ARROW_DOWN);
        RFQPageLocators.Search.sendKeys(Keys.ENTER);*/
        List<String> listOfp_org = RFQPageLocators.purchase_Organization.stream().map(WebElement::getText)
                .collect(Collectors.toList());
        listOfp_org.removeIf(item -> item.isEmpty() || item.equals("Select"));

        for (String actualp_org : listOfp_org) {
            if (actualp_org.contains(pOrg)) {
                RFQPageLocators.purchase_Organization.stream().filter(It -> It.getText().contains(pOrg)).findFirst().ifPresent(WebElement::click);
                waitForPageLoad(driver);
                break;
            }
        }
        RFQPageLocators.clickdpurchase_grp.click();
       /* RFQPageLocators.Search.click();
        RFQPageLocators.Search.sendKeys(pGrp);
        RFQPageLocators.Search.sendKeys(Keys.ARROW_DOWN);
        RFQPageLocators.Search.sendKeys(Keys.ENTER);*/
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

        PageFactory.initElements(driver, RFQPageLocators.class);
        RFQPageLocators.categorylabel.click();
        HelperFunction.takeScreenshot();

        List<String> listOfcategory = RFQPageLocators.category.stream().map(WebElement::getText)
                .collect(Collectors.toList());
        listOfcategory.removeIf(item -> item.isEmpty() || item.equals("Select"));
        for (String actualcategory : listOfcategory) {
            if (actualcategory.contains(category)) {
                RFQPageLocators.category.stream().filter(It -> It.getText().contains(category)).findFirst().ifPresent(WebElement::click);
                waitForPageLoad(driver);
                break;
            }
        }

        RFQPageLocators.paymentTermslabel.click();
        HelperFunction.takeScreenshot();

        List<String> listOfPayementTerm = RFQPageLocators.paymentTerms.stream().map(WebElement::getText)
                .collect(Collectors.toList());
        listOfPayementTerm.removeIf(item -> item.isEmpty() || item.equals("Select"));
        for (String actualPaymentTerm : listOfPayementTerm) {

            if (actualPaymentTerm.contains(paymentTerms)) {
                RFQPageLocators.paymentTerms.stream()
                        .filter(it -> it.getText().contains(paymentTerms))
                        .findFirst()
                        .ifPresent(WebElement::click);

                waitForPageLoad(driver);
                break;
            }
        }


        RFQPageLocators.incoTermsLabel.click();
        HelperFunction.takeScreenshot();

        List<String> listOfincoTerms = RFQPageLocators.incoTerms.stream().map(WebElement::getText)
                .collect(Collectors.toList());
        listOfincoTerms.removeIf(item -> item.isEmpty() || item.equals("Select"));
        for (String actualincoTerms : listOfincoTerms) {
            if (actualincoTerms.contains(incoTerms)) {
                RFQPageLocators.incoTerms.stream().filter(It -> It.getText().contains(incoTerms)).findFirst().ifPresent(WebElement::click);
                waitForPageLoad(driver);
                break;
            }
        }
        HelperFunction.takeScreenshot();

    }

    public static void errorLabelValidation(String rfqMode, String description, String documentType, String currency) {
        PageFactory.initElements(driver, RFQPageLocators.class);




    }

    private static void errorTopYlabel(int inputCenterX, int errorCenterX, int errorTopY, int inputBottomY) {
        if (errorTopY > inputBottomY) {
            System.out.println("Error label is positioned below the input field.");
        } else {
            System.out.println("Error label is not positioned below the input field.");
        }
        // Validate horizontal alignment
        if (inputCenterX == errorCenterX) {
            Assert.assertEquals(inputCenterX, errorCenterX, "Error label is properly center-aligned with the input field.");
            System.out.println("Error label is properly center-aligned with the input field.");
        } else {
            Assert.fail("Error label is properly center-aligned with the input field.");
        }

    }

}
