package com.crazy.game;

import com.crazy.game.rules.game;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    game g;
    List<String> temp = new ArrayList<>();
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

    @When("list is {string} {string} {string} {string} {string}")
    public void listIs(String arg0, String arg1, String arg2, String arg3, String arg4) {
        temp.add(arg0);
        temp.add(arg1);
        temp.add(arg2);temp.add(arg3);temp.add(arg4);


    }

    @Then("the score is {int}")
    public void theScoreIs(int arg0) {
        Assertions.assertEquals(arg0,g.listScore(temp,0));
    }
}
