@All
Feature: RFQ Creation and Filling in Basic Details for an Item
  As a user of the application
  I want to Create RFQ
  I want to fill out the basic details form with the required fields
  So that the item can be created successfully in the system


  @TC011
  Scenario Outline: User successfully submits the form with valid inputs
    Given I am on the login page"<Browser>"
    When I enter valid credentials
    And I click on the login button
    Then I should be redirected to the dashboard
    Given the user is on the "General Data" form
    When the user selects "<RFQ_Mode>" ,"<Description>","<Document_Type>", "<Currency>"
    And the user selects "<P_Org>", "<P_Grp>"
    And the user enters "<Delivery_Date>","<Deadline_Date>", "<Validity_Period>","<RFQEvaluationPeriod>","<AwardingPeriod>"
    And the user selects "<Category>", "<PaymentTerms>", "<INCOTerms>"
    And the user submits the form
    #And I am on the "Basic Details" form page
    #And I enter the following details "<Plant>","<Short_Text>","<Material_Category>","<Material_Group>","<Order_Quantity>","<Order_QuantityType>"
    #And I enter the Additional Fields "<Cost_Centre>","<GL_Account>","<Internal_Order>","<Asset_Number>"
    #Then the form should be successfully submitted
    #And I should see a confirmation message "Item created successfully"
    #Then the system should successfully submit the form
    #And display a success message

    Examples:
      | Browser | RFQ_Mode   | Description | Document_Type | Currency | P_Org | P_Grp | Delivery_Date | Deadline_Date | Validity_Period | RFQEvaluationPeriod | AwardingPeriod | Category              | PaymentTerms | INCOTerms | Plant | Short_Text | Material_Category | Material_Group | Order_Quantity | Order_QuantityType | Cost_Centre | GL_Account | Internal_Order | Asset_Number  |
      | Edge    | Direct RFQ | Direct RFQ  | Service       | NGN      | NG01  | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CFR       | 6101  | Auomation  | 001               | 7011           | 10             | BAG                | NG12010101  | 600002     | 8000062401     | Automation001 |
      | Edge    | Select     | Direct RFQ  |               | NGN      |       | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CIR       |       |            |                   |                |                |                    |             |            |                |               |
      | Edge    | Direct RFQ | Direct RFQ  | Select        | NGN      |       | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CIR       |       |            |                   |                |                |                    |             |            |                |               |
      | Edge    | Direct RFQ | Direct RFQ  | Service       | Select   | NG01  | 098   |               |               |                 |                     |                | Request For Quotation | Z007         | CFR       |       |            |                   |                |                |                    |             |            |                |               |

