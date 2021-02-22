Feature: Searching customers


  Scenario Outline: Search for customer using filters
    Given no filters are applied
    When user searches for "<customerData>" using "<filter>" filter
    Then result with "<customerData>" in "<filter>" column should be shown
    But result with "<otherCustomerData>" in "<filter>" column should not be shown

    Examples:
      | customerData | filter | otherCustomerData    |
      | 1            | Id     | 2                    |
      | Postimex     | Name   | Bondir               |
      | info@bond.ir | Email  | conatact@postimex.pl |
      | Melbourne    | City   | Belfast              |


  Scenario Outline: Match case functionality
    Given user searched for case swapped "<customerData>" using "<filter>" filter
    When user enables 'Match case' function
    Then result with "<customerData>" in "<filter>" column is not shown

    Examples:
      | customerData | filter |
      | Postimex     | Name   |
      | info@bond.ir | Email  |
      | Melbourne    | City   |


  Scenario: Clear filters
    Given user searched for "Postimex" using "Name" filter
    When user clicks "click to clear all filters" button
    Then all filters are removed
