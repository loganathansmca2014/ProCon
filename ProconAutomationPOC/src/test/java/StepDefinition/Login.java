package StepDefinition;

import BusinessLogic.Businessfunction;
import PageLocators.LoginPageLocators;
import Utillity.BrowserControl;
import Utillity.HelperFunction;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.chrono.BuddhistChronology;
import org.testng.Assert;

import java.io.IOException;

import static Utillity.HelperFunction.scenario;

public class Login {

    @Given("I am on the login page{string}")
    public void iAmOnTheLoginPage(String browserType) throws InterruptedException, IOException {

       BrowserControl.driverBrowser(browserType);
        HelperFunction.takeScreenshot();

    }
    @When("I enter valid credentials")
    public void iEnterValidCredentials() throws IOException, InterruptedException {
        Businessfunction.loginfunction();



    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        Businessfunction.clickLoginBtn();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        Businessfunction.verifyLandingPage();
    }

    @When("I enter invalid credentials")
    public void iEnterInvalidCredentials() throws IOException {
        Businessfunction.inValidCredLogin();

    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        Businessfunction.errorValidation();
    }

    @And("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        Assert.assertTrue(LoginPageLocators.loginBtn.isDisplayed());
        HelperFunction.scenario.log("User is still under loginPage");
    }

    @When("I leave the username and password fields blank")
    public void iLeaveTheUsernameAndPasswordFieldsBlank() {
Businessfunction.clickLoginBtn();

    }



    @Then("I should see a validation login button disabled")
    public void iShouldSeeAValidationLoginButtonDisabled() {
        //HelperFunction.takeScreenshot();
        Assert.assertFalse(LoginPageLocators.loginBtn.isEnabled());
        scenario.log("Login Button disabled");    }
}
