package com.crazy.game;

import com.crazy.game.rules.game;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    game g;
    String play;
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

    @When("common card is {string}")
    public void commonCardIs(String arg0) {
        g.setCommon(arg0);
    }

    @And("and player plays {string}")
    public void andPlayerPlays(String arg0) {
        play = arg0;
    }

    @Then("valid is true")
    public void validIsTrue() {
        Assertions.assertTrue(g.isMoveValid(play));
    }


    @When("direction is left")
    public void directionIsLeft() {
        g.setDirection(1);
    }

    @Then("player {int} plays now")
    public void playerPlaysNow(int arg0) {
        g.increaseCurrent();
        Assertions.assertEquals(arg0,g.getCurrent());
    }

    @Given("player {int} plays")
    public void playerPlays(int arg0) {
        g.setCurrent(arg0);
    }

    @And("direction is right")
    public void directionIsRight() {
        g.setDirection(-1);
    }
}
