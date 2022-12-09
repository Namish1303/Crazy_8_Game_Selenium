Feature: Unit Tests

  Scenario: When cards are assigned
    Given game is initialised
    When assignCards is called
    Then 52 cards are given


  Scenario:  When score is calculate
    Given game is initialised
    When list is "1S" "2S" "8D" "QC" "KD"
    Then the score is 73


    Scenario: Check valid move
      Given game is initialised
      When common card is "1S"
      And and player plays "2S"
      Then valid is true
