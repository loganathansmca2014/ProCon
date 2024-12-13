@login @All
Feature: User Login
  As a user of the application
  I want to log in with valid credentials
  So that I can access the secure area of the Procon application

  @TC01
  Scenario Outline: Successful Login with valid credentials
    Given I am on the login page"<Browser>"
    When I enter valid credentials
    And I click on the login button
    Then I should be redirected to the dashboard
    Examples:
    |Browser|
    |Edge   |
    #And I should see a welcome message
  @TC02
  Scenario Outline: Unsuccessful Login with invalid credentials
    Given I am on the login page"<Browser>"
    When I enter invalid credentials
    And I click on the login button
    Then I should see an error message
    And I should remain on the login page
Examples:
  |Browser|
  |Edge   |
    @TC03
  Scenario Outline: Attempt to login with empty fields
    Given I am on the login page"<Browser>"
    When I leave the username and password fields blank
      Then I should see a validation login button disabled
Examples:
  |Browser|
  |Edge   |