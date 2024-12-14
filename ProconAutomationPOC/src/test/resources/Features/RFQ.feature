@All
Feature: Create New
  As a user of the application
  I want to Create RFQ

  @RFQ_TC01
  Scenario Outline: User successfully submits the form with valid inputs
    Given I am on the login page"<Browser>"
    When I enter valid credentials
    And I click on the login button
    Then I should be redirected to the dashboard
    Given the user is on the "General_Data" form
    When the user selects "<RFQ_Mode>" ,"<Description>","<Document_Type>", "<Currency>"
    And the user selects "<P_Org>", "<P_Grp>"
    And the user enters "<Delivery_Date>","<Deadline_Date>", "<Validity_Period>","<RFQEvaluationPeriod>","<AwardingPeriod>"
    And the user selects "<Category>", "<PaymentTerms>", "<INCOTerms>"
    And the user submits the form
    Then the system should successfully submit the form
    #And display a success message

    Examples:
      | Browser | RFQ_Mode   | Description | Document_Type | Currency | P_Org | P_Grp | Delivery_Date | Deadline_Date | Validity_Period | RFQEvaluationPeriod | AwardingPeriod | Category              | PaymentTerms | INCOTerms |
      | Edge    | Direct RFQ | Direct RFQ  | Service       | NGN      | NG01  | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CFR       |
      | Edge    | Select     | Direct RFQ  |               | NGN      |       | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CIR       |
      | Edge    | Direct RFQ | Direct RFQ  | Select        | NGN      |       | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CIR       |
      | Edge    | Direct RFQ | Direct RFQ  | Service       | Select   | NG01  | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CFR       |
