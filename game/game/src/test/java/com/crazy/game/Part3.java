package com.crazy.game;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(properties = {"server.port-8080"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class Part3 {

    @Autowired
    game g;


    private WebDriver player1;
    private WebDriver player2;
    private WebDriver player3;
    private WebDriver player4;

    @Test
    public void Row80()
    {
        String cards1 = "4H,7S,5D,6D,9D,4S,6S,KC,8H,TD,9S,6C,9C,JD,3H,7D,JH,QH,KH,5C,";
        cards1 = cards1 + "4D,";
        cards1 = cards1 + "2C,3C,4C,TH,JC,7C";
        List<String> cardsOne = Arrays.asList(cards1.split(","));
        System.out.println(cardsOne.get(20));

        List<String> fromGame = new ArrayList<>();
        fromGame = g.getCards();

        for(int i=0;i< cardsOne.size();i++)
        {
            fromGame.set(i,cardsOne.get(i));
        }

        g.setCards(fromGame);

        WebDriverManager.chromedriver().setup();
        player1 = new ChromeDriver();
        player1.navigate().to("http://localhost:8080");
        player1.findElement(By.id("username")).sendKeys("player1");
        player1.findElement(By.id("connect")).click();


        player2 = new ChromeDriver();
        player2.navigate().to("http://localhost:8080");
        player2.findElement(By.id("username")).sendKeys("player2");
        player2.findElement(By.id("connect")).click();

        player3 = new ChromeDriver();
        player3.navigate().to("http://localhost:8080");
        player3.findElement(By.id("username")).sendKeys("player3");
        player3.findElement(By.id("connect")).click();

        player4 = new ChromeDriver();
        player4.navigate().to("http://localhost:8080");
        player4.findElement(By.id("username")).sendKeys("player4");
        player4.findElement(By.id("connect")).click();

        WebDriverWait wait = new WebDriverWait(player1, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("4H")));
        player1.findElement(By.id("4H")).click();
        player2.findElement(By.id("4S")).click();
        player3.findElement(By.id("9S")).click();
        player4.findElement(By.id("draw")).click();
        Assertions.assertTrue(player4.findElement(By.id("2C")).isEnabled());
        player4.findElement(By.id("draw")).click();
        player4.findElement(By.id("draw")).click();
        player4.findElement(By.id("draw")).click();
        Assertions.assertTrue(player1.findElement(By.id("7S")).isEnabled());
        player1.findElement(By.id("7S")).click();
        player2.findElement(By.id("6S")).click();
        player3.findElement(By.id("6C")).click();
        player4.findElement(By.id("2C")).click();
        player1.findElement(By.id("draw")).click();
        player1.findElement(By.id("draw")).click();
        //player1 draws JC (top card is 2C) (hence draw button should be disabled now)
        Assertions.assertFalse(player1.findElement(By.id("draw")).isEnabled());
        player1.findElement(By.id("JC")).click();
        player2.findElement(By.id("KC")).click();
        player3.findElement(By.id("9C")).click();
        player4.findElement(By.id("3C")).click();
        player1.findElement(By.id("draw")).click();
        player1.findElement(By.id("7C")).click();
        player2.findElement(By.id("8H")).click();
        Select suite = new Select(player2.findElement(By.id("suiteSelect")));
        suite.selectByVisibleText("None");
        suite.selectByIndex(4);
        player3.findElement(By.id("JD")).click();
        player4.findElement(By.id("7D")).click();
        //player 2 changes suite to D, player 3 plays JD, player 4 plays 7D, hence player 1 should have turn now
        Assertions.assertTrue(player1.findElement(By.id("9D")).isEnabled());
        player1.findElement(By.id("9D")).click();
        player2.findElement(By.id("TD")).click();

        Assertions.assertEquals(player1.findElement(By.id("player1")).getText(),"player1 : 21");
        Assertions.assertEquals(player1.findElement(By.id("player2")).getText(),"player2 : 0");
        Assertions.assertEquals(player1.findElement(By.id("player3")).getText(),"player3 : 3");
        Assertions.assertEquals(player1.findElement(By.id("player4")).getText(),"player4 : 39");

        //round 1 complete.............


        player1.findElement(By.className("round")).click();
        player2.findElement(By.className("round")).click();
        player3.findElement(By.className("round")).click();
        cards1 = "TD,7D,4S,7C,4H,5D,9D,3S,9C,3H,JC,3D,9S,3C,9H,5H,4D,7S,4C,5S,8D,";
        cards1 = cards1 + "KS,QS,KH,6D,QD,JD,6S,JS,TS";

        List<String> cardsTwo = Arrays.asList(cards1.split(","));
        List<String> fromGame2 = new ArrayList<>();
        fromGame2 = g.getCards();
        System.out.println(fromGame2.size());
        for(int i=0;i< cardsTwo.size();i++)
        {
            fromGame2.set(i,cardsTwo.get(i));
        }

        g.setCards(fromGame2);
        player4.findElement(By.className("round")).click();

        //WebDriverWait wait2 = new WebDriverWait(player2, Duration.ofSeconds(20));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("9D")));
        Assertions.assertTrue(player2.findElement(By.id("9D")).isEnabled());

        player2.findElement(By.id("9D")).click();
        player3.findElement(By.id("3D")).click();
        player4.findElement(By.id("4D")).click();
        player1.findElement(By.id("4S")).click();

        player2.findElement(By.id("3S")).click();
        player3.findElement(By.id("9S")).click();
        player4.findElement(By.id("7S")).click();
        player1.findElement(By.id("7C")).click();

        player2.findElement(By.id("9C")).click();
        player3.findElement(By.id("3C")).click();
        player4.findElement(By.id("4C")).click();
        player1.findElement(By.id("4H")).click();

        player2.findElement(By.id("3H")).click();
        player3.findElement(By.id("9H")).click();
        //player 3 has played 9H card (row 131)

        player4.findElement(By.id("draw")).click();
        player4.findElement(By.id("draw")).click();
        player4.findElement(By.id("draw")).click();

        Assertions.assertTrue(player4.findElement(By.id("KH")).isEnabled());
        player4.findElement(By.id("KH")).click();
        player1.findElement(By.id("draw")).click();
        player1.findElement(By.id("draw")).click();
        player1.findElement(By.id("draw")).click();
        player1.findElement(By.id("draw")).click();

        player2.findElement(By.id("draw")).click();
        player2.findElement(By.id("draw")).click();
        player2.findElement(By.id("draw")).click();
        player2.findElement(By.id("draw")).click();

       player3.findElement(By.id("5H")).click();

        Assertions.assertEquals(player1.findElement(By.id("gameOver")).getText(),"GAME OVER!!!");
        Assertions.assertEquals(player1.findElement(By.id("player1")).getText(),"player1 : 59");
        Assertions.assertEquals(player1.findElement(By.id("player2")).getText(),"player2 : 36");
        Assertions.assertEquals(player1.findElement(By.id("player3")).getText(),"player3 : 3");
        Assertions.assertEquals(player1.findElement(By.id("player4")).getText(),"player4 : 114");
       // Winner:  {winner}
        Assertions.assertEquals(player1.findElement(By.id("winner")).getText(),"Winner: player3");



        player1.close();
        player2.close();
        player3.close();
        player4.close();


    }
}
