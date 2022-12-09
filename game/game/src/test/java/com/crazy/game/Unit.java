package com.crazy.game;

import com.crazy.game.rules.game;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class Unit {
    game g;

    @When("assignCards is called")
    public void assigncardsIsCalled() {
        g.assignCards();
    }

    @Given("game is initialised")
    public void gameIsInitialised() {
        g = new game();
    }

    @Then("{int} cards are given")
    public void cardsAreGiven(int arg0) {
        Assertions.assertEquals(arg0,g.cards.size());
    }
}
