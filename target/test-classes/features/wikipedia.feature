Feature: Wikipedia Basic Functionality
  As a user
  I want to perform basic operations on Wikipedia
  So that I can verify its core functionality

  Scenario: Search for a topic on Wikipedia
    Given I am on the Wikipedia homepage
    When I search for "Selenium (software)"
    Then I should see the search results dropdown
    And the first result should contain "Selenium"

  Scenario: Navigate to a specific article
    Given I am on the Wikipedia homepage
    When I search for "Selenium (software)"
    And I click on the first result
    Then I should be taken to the Contents page with title "Selenium (software)" 
