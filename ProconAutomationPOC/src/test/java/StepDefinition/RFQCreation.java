package StepDefinition;

import BusinessLogic.Businessfunction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.io.IOException;

public class RFQCreation {


    @Given("the user is on the {string} form")
    public void theUserIsOnTheForm(String form) throws InterruptedException {
        Businessfunction.getFormData(form);
    }

    @When("the user selects {string} ,{string},{string}, {string}")
    public void theUserSelects(String RFQ_Mode, String Description, String Document_Type, String Currency) throws InterruptedException, AWTException {
        Businessfunction.toRFQInfo(RFQ_Mode, Description, Document_Type, Currency);
    }

    @And("the user selects {string}, {string}")
    public void theUserSelects(String P_Org, String P_Grp) {
        Businessfunction.toRFQpurchese(P_Org, P_Grp);

    }

    @And("the user enters {string},{string}, {string},{string},{string}")
    public void theUserEnters(String Delivery_Date, String Deadline_Date, String Validity_Period, String RFQEvaluationPeriod, String AwardingPeriod) {
        Businessfunction.rfqDateValidation(Delivery_Date, Deadline_Date, Validity_Period, RFQEvaluationPeriod, AwardingPeriod);
    }

    @And("the user selects {string}, {string}, {string}")
    public void theUserSelects(String Category, String PaymentTerms, String INCOTerms) {
        Businessfunction.rfqDataCategory(Category, PaymentTerms, INCOTerms);

    }

    @And("the user submits the form")
    public void theUserSubmitsTheForm() {
        Businessfunction.rfqSubmit();
    }

    @Then("the system should successfully submit the form")
    public void theSystemShouldSuccessfullySubmitTheForm() {
    }

    @And("I am on the {string} form page")
    public void iAmOnTheFormPage(String detailText) {
        Businessfunction.addBasicDetails(detailText);
    }


    @And("I enter the Additional Fields {string},{string},{string},{string}")
    public void iEnterTheAdditionalFields(String arg0, String arg1, String arg2, String arg3) {
    }

    @Then("the form should be successfully submitted")
    public void theFormShouldBeSuccessfullySubmitted() {
    }

    @And("I should see a confirmation message {string}")
    public void iShouldSeeAConfirmationMessage(String arg0) {
    }


    @And("I enter the following details {string},{string},{string},{string},{string},{string}")
    public void iEnterTheFollowingDetails(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    }
}
