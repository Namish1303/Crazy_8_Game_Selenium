package com.crazy.game.selenium;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class Part1 {

    @Autowired
    private WebDriver webDriver;

    @FindBy(id="connect")
    private WebElement connect;

    @Test
    void openBrowser()
    {

        webDriver.navigate().to("http://localhost:8080");
        //connect.click();
        webDriver.findElement(By.className("connect"));
        //connect.click();


        //System.out.println("HERE");
    }

}
