Feature: Swap Feature
  As a registered user
  I want to swap one crypto currency for another instantly
  So that I can take advantage of real-time market rates and prices

  @positive @smoke @regression @swap
  Scenario: Swap 100 USDT to BTC successfully
    Given I am logged into the platform
    And I navigate to the Swap section
    When I select "USDT" as the source currency
    And I select "BTC" as the target currency
    And I enter "100" as the amount
    And I confirm the swap
    Then I should see the updated wallet balances
    And the transaction should appear in the swap history.