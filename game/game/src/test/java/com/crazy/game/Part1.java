package com.crazy.game;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(properties = {"server.port-8080"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class Part1 extends Base{

    @Autowired
    game g;


    private WebDriver player1;
    private WebDriver player2;
    private WebDriver player3;
    private WebDriver player4;

    @FindBy(id="connect")
    private WebElement connect;


    @BeforeEach
    void start()
    {
        WebDriverManager.chromedriver().setup();
        player1 = new ChromeDriver();
        player2 = new ChromeDriver();
        player3 = new ChromeDriver();
        player4 = new ChromeDriver();
    }

    @Test
    void openBrowser()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(20,"4C");
        temp.set(1,"3C");
        temp.set(7,"5D");
        g.setCards(temp);

        System.out.println(g.getCards().get(1));

        //player1.navigate().to("http://localhost:3000");
        player1.navigate().to("http://localhost:8080");
        player2.navigate().to("http://localhost:8080");
        player3.navigate().to("http://localhost:8080");
        player4.navigate().to("http://localhost:8080");



        player1.findElement(By.id("username")).sendKeys("player1");
        player2.findElement(By.id("username")).sendKeys("player2");
        player3.findElement(By.id("username")).sendKeys("player3");
        player4.findElement(By.id("username")).sendKeys("player4");



        player1.findElement(By.id("connect")).click();
        player2.findElement(By.id("connect")).click();
        player3.findElement(By.id("connect")).click();
        player4.findElement(By.id("connect")).click();

        
        player1.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        player2.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        player3.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        player4.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        //player1.findElement(By.id("3C")).click();
        g.reset();
       // player2.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //Boolean check = player2.findElement(By.id("5D")).isEnabled();



       // Assertions.assertTrue(check);




    }



    void end()
    {
        player1.close();
        player2.close();
        player3.close();
        player4.close();
    }
}
