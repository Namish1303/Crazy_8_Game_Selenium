package com.crazy.game.selenium;

import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


public class Part1 extends Base{

    @Autowired
    private WebDriver player1;

    @Autowired
    private WebDriver player2;

    @Autowired
    private WebDriver player3;

    @Autowired
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

        player1.navigate().to("http://localhost:3000");
        player1.findElement(By.id("username")).sendKeys("player1");
        player1.findElement(By.id("connect")).click();

        player2.navigate().to("http://localhost:3000");
        player2.findElement(By.id("username")).sendKeys("player2");
        player2.findElement(By.id("connect")).click();

        player3.navigate().to("http://localhost:3000");
        player3.findElement(By.id("username")).sendKeys("player3");
        player3.findElement(By.id("connect")).click();

        player4.navigate().to("http://localhost:3000");
        player4.findElement(By.id("username")).sendKeys("player4");
        player4.findElement(By.id("connect")).click();
        


    }


    @AfterEach
    void end()
    {
        player1.close();
        player2.close();
        player3.close();
        player4.close();
    }
}
