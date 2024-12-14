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
        Businessfunction.getFormData( form);
    }

    @When("the user selects {string} ,{string},{string}, {string}")
    public void theUserSelects(String RFQ_Mode, String Description, String Document_Type, String Currency) throws InterruptedException, AWTException {
        Businessfunction.toRFQInfo(RFQ_Mode,Description,Document_Type,Currency);
    }

    @And("the user selects {string}, {string}")
    public void theUserSelects(String P_Org, String P_Grp) {
        Businessfunction.toRFQpurchese(P_Org,P_Grp);

    }

    @And("the user enters {string},{string}, {string},{string},{string}")
    public void theUserEnters(String Delivery_Date, String Deadline_Date, String Validity_Period, String RFQEvaluationPeriod, String AwardingPeriod) {
Businessfunction.rfqDateValidation(Delivery_Date,Deadline_Date,Validity_Period,RFQEvaluationPeriod,AwardingPeriod);
    }

    @And("the user selects {string}, {string}, {string}")
    public void theUserSelects(String Category, String PaymentTerms, String INCOTerms) {
        Businessfunction.rfqDataCategory(Category,PaymentTerms,INCOTerms);

    }

    @And("the user submits the form")
    public void theUserSubmitsTheForm() {
        Businessfunction.rfqSubmit();
    }

    @Then("the system should successfully submit the form")
    public void theSystemShouldSuccessfullySubmitTheForm() {
    }
}
