package com.crazy.game;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
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

    }

    @Test
    void Row41()
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
        player1.findElement(By.id("3C")).click();
        Assertions.assertTrue(player2.findElement(By.id("5D")).isEnabled());

        g.reset();


    }

    @Test
    void Row42()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"AH");
        temp.set(16,"7H");
        temp.set(20,"3H");
        temp.set(12,"8H");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AH")));
        player1.findElement(By.id("AH")).click();
        Assertions.assertTrue(player4.findElement(By.id("7H")).isEnabled());

        player4.findElement(By.id("7H")).click();
        Assertions.assertTrue(player3.findElement(By.id("8H")).isEnabled());


        g.reset();
    }

    @Test
    public void Row44()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"QC");
        temp.set(6,"9H");
        temp.set(16,"7H");
        temp.set(20,"AC");
        temp.set(12,"8H");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("QC")));


        player1.findElement(By.id("QC")).click();
        Assertions.assertFalse(player2.findElement(By.id("9H")).isEnabled());
        Assertions.assertTrue(player3.findElement(By.id("8H")).isEnabled());
        g.reset();
    }

    @Test
    public void Row45()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"QC");
        temp.set(2,"9H");
        temp.set(16,"3C");
        temp.set(20,"AC");
        temp.set(12,"7C");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("QC")));

        player1.findElement(By.id("QC")).click();
        player3.findElement(By.id("7C")).click();
        Assertions.assertTrue(player4.findElement(By.id("3C")).isEnabled());
        player4.findElement(By.id("3C")).click();

        Assertions.assertTrue(player1.findElement(By.id("9H")).isEnabled());
    }

    @Test
    public void Row47()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"TD");
        temp.set(2,"9H");
        temp.set(16,"AH");
        temp.set(20,"4D");
        temp.set(12,"KH");
        temp.set(6,"KD");
        temp.set(7,"9C");
        temp.set(13,"7H");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("TD")));

        player1.findElement(By.id("TD")).click();
        player2.findElement(By.id("KD")).click();
        player3.findElement(By.id("KH")).click();
        player4.findElement(By.id("AH")).click();

        System.out.println("\n\n\n\n\n"+player3.findElement(By.id("turn")).getText());
        Assertions.assertTrue(player3.findElement(By.id("7H")).isEnabled());
        player3.findElement(By.id("7H")).click();

        Assertions.assertTrue(player2.findElement(By.id("9C")).isEnabled());

    }

    @Test
    public void Row48()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"TD");
        temp.set(2,"9H");
        temp.set(16,"QC");
        temp.set(20,"4D");
        temp.set(12,"KC");
        temp.set(6,"KD");
        temp.set(7,"9C");
        temp.set(13,"7H");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("TD")));

        player1.findElement(By.id("TD")).click();
        player2.findElement(By.id("KD")).click();
        player3.findElement(By.id("KC")).click();
        player4.findElement(By.id("QC")).click();

        Assertions.assertTrue(player2.findElement(By.id("9C")).isEnabled());
    }


    @Test
    public void Row51()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"KH");
        temp.set(2,"9H");
        temp.set(16,"QC");
        temp.set(20,"KC");
        temp.set(12,"KC");
        temp.set(6,"KD");
        temp.set(7,"9C");
        temp.set(13,"7H");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("KH")));

        player1.findElement(By.id("KH")).click();
        Assertions.assertEquals(player2.findElement(By.id("common")).getText(),"KH");
        Assertions.assertTrue(player2.findElement(By.id("KD")).isEnabled());
    }


    @Test
    public void Row52()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"7C");
        temp.set(20,"KC");
        temp.set(6,"9C");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("7C")));

        player1.findElement(By.id("7C")).click();
        Assertions.assertEquals(player2.findElement(By.id("common")).getText(),"7C");
        Assertions.assertTrue(player2.findElement(By.id("9C")).isEnabled());
    }

    @Test
    public void Row53()
    {
        List<String> temp = new ArrayList<>();
        temp = g.getCards();
        temp.set(0,"8H");
        temp.set(20,"KC");
        temp.set(6,"9C");
        g.setCards(temp);


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("8H")));

        player1.findElement(By.id("8H")).click();
        //Assertions.assertEquals(player2.findElement(By.id("common")).getText(),"7C");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("suiteSelect")));
        Assertions.assertTrue(player1.findElement(By.id("suiteSelect")).isEnabled());
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
