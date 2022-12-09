Feature: Unit Tests

  Scenario: When cards are assigned
    Given game is initialised
    When assignCards is called
    Then 52 cards are given