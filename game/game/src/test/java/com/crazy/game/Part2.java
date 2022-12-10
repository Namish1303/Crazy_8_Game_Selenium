package com.crazy.game;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(properties = {"server.port-8080"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class Part2 extends Base{

    @Autowired
    game g;


    private WebDriver player1;
    private WebDriver player2;
    private WebDriver player3;
    private WebDriver player4;


    @Test
    public void Row78()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(20,"4C");
        temp.set(1,"3C");
        temp.set(7,"5D");
        g.setCards(temp);


        //player1.navigate().to("http://localhost:3000");
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("3C")));

        List<String> one = new ArrayList<>();
        List<String> two = new ArrayList<>();
        List<String> three = new ArrayList<>();
        List<String> four = new ArrayList<>();
        Map<String,List<String>> temp2 = new HashMap<>();
        one.add("1S");one.add("3C");
        two.add("4C");
        three.add("8H");three.add("JH");three.add("6H");three.add("KH");three.add("KS");
        four.add("8C");four.add("8D");four.add("2D");
        temp2.put("player1",one);
        temp2.put("player2",two);
        temp2.put("player3",three);
        temp2.put("player4",four);
        g.setPlayerC(temp2);
        player1.findElement(By.id("3C")).click();
        player2.findElement(By.id("4C")).click();

        Assertions.assertEquals(player1.findElement(By.id("gameOver")).getText(),"GAME OVER!!!");
        Assertions.assertEquals(player1.findElement(By.id("player1")).getText(),"player1 : 1");
        Assertions.assertEquals(player1.findElement(By.id("player2")).getText(),"player2 : 0");
        Assertions.assertEquals(player1.findElement(By.id("player3")).getText(),"player3 : 86");
        Assertions.assertEquals(player1.findElement(By.id("player4")).getText(),"player4 : 102");
        g.reset();

        player1.close();
        player2.close();
        player3.close();
        player4.close();
    }
}
