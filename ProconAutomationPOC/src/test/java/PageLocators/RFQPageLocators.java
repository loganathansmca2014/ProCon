package PageLocators;

import io.cucumber.java.zh_cn.假如;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.nio.file.WatchEvent;
import java.util.List;

public class RFQPageLocators {

    @FindBy(xpath = "(//img[@alt='menu'])[1]")
    public static WebElement rfqcreationicon;

    @FindBy(xpath = "//span[text()=' CREATE NEW ']")
    public static WebElement createNewBtn;

    @FindBy(xpath = "//div[@class='d-flex align-items-center']//h1[1]")
    public static WebElement formName;
    @FindBy(xpath = "(//div[@ng-reflect-ng-switch='true'])[1]")
    public static WebElement clickrfqmode;
    @FindBy(xpath = "//span[text()=' Manual Creation ']")
    public static WebElement manualCreation;

    @FindBy(xpath = "(//mat-option[@role='option'])")
    public static List<WebElement> rfqMode;
    @FindBy(xpath = "(//div[@ng-reflect-ng-switch='true'])[1]")
    public static WebElement clickdocumentTypeList;

    @FindBy(xpath = "//span[text()='Currency']")
    public static WebElement clickCurrencyList;

    @FindBy(xpath = "(//mat-option[contains(@class,'mat-mdc-option mdc-list-item')])")
    public static List<WebElement> documentTypeList;

    @FindBy(xpath = "(//div[@role='listbox']//mat-option)")
    public static List<WebElement> currencyList;
    @FindBy(xpath = "//div[@class='mat-select-search-inner-row']//input[1]")
    public static WebElement Search;

    @FindBy(xpath = "((//*[text()='Purchase Organization'])//following::div)[2]")
    public static WebElement clickpurchase_Organization;

    @FindBy(xpath = "((//*[text()='Purchase Group'])//following::div)[2]")
    public static WebElement clickdpurchase_grp;
    @FindBy(xpath = "(//mat-option[@role='option']/following-sibling::mat-option)")
    public static List<WebElement> purchase_Organization;

    @FindBy(xpath = "(//div[@class='row align-items-center']//div)")
    public static List<WebElement> purchase_grp ;

    @FindBy(xpath = "(//*[starts-with(@class,'mat-mdc-select-value ng-tns-')])[7]")
    public static WebElement categorylabel;
    @FindBy(xpath = "(//*[starts-with(@class,'mat-mdc-select-value ng-tns-')])[8]")
    public static WebElement paymentTermslabel;
    @FindBy(xpath = "(//*[starts-with(@class,'mat-mdc-select-value ng-tns-')])[9]")
    public static WebElement incoTermsLabel;

    @FindBy(xpath = "(//mat-option[@role='option']/following-sibling::mat-option)")
    public static List<WebElement> category ;
    @FindBy(xpath = "(//mat-option[contains(@class,'mat-mdc-option mdc-list-item')])")
    public static List<WebElement> paymentTerms ;

    @FindBy(xpath = "(//div[@role='listbox']//mat-option)")
    public static List<WebElement> incoTerms ;

    @FindBy(xpath = "//input[@role='switch']")
    public static WebElement descriptionInputBox;

    @FindBy(xpath = "//mat-label[text()='Delivery Date']")
    public static WebElement deliverydateLabel;
    @FindBy(xpath = "//input[@ng-reflect-placeholder='Delivery Date']")
    public static WebElement deliveryDate;
    @FindBy(xpath = "(//span[@class='mdc-button__label']//span)[2]") // Adjust for month and year display
    public static WebElement displayedMonthYear;
    @FindBy(xpath = "(//button[@aria-label='Next month']//span)[2]") // Adjust for the next button if needed
    public static WebElement nextButton;
    @FindBy(xpath = "//mat-label[text()='Deadline Date']")
    public static WebElement deadLineDateLable;

    @FindBy(xpath = "//input[@ng-reflect-placeholder='Deadline Date']")
    public static WebElement deadLineDate;

    @FindBy(xpath = "//input[@ng-reflect-name='ValidityPeriodEnd']")
    public static WebElement validityPeriodEnd;

    @FindBy(xpath = "//input[@ng-reflect-name='EvaluationPeriodEnd']")
    public static WebElement evaluationPeriodEnd;

    @FindBy(xpath = "//input[@ng-reflect-name='AwardingDateTo']")
    public static WebElement awardingDateTo;


    @FindBy(xpath = "//button[@class='save mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-unthemed mat-mdc-button-base']")
    public static WebElement saveBtn;

    @FindBy(xpath = "(//mat-label[@class='details-label ng-star-inserted'])[1]")
    public static List<WebElement> listofLabel;



    @FindBy(xpath = "//span[text()='ADD ITEMS']")
    public static WebElement addItemRFQ;

    @FindBy(xpath = "//span[@class='subHeading mt-2']")
    public static WebElement basicinfoLabel;

    @FindBy(xpath = "(//div[@ng-reflect-ng-switch='true'])[1]")
    public static WebElement clickPlant;

    @FindBy(xpath = "(//span[@class='mdc-list-item__primary-text'])")
    public static List<WebElement> listOfPlant;







}
