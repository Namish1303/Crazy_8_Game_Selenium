package com.crazy.game;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class Base {

    @Before
    public WebDriver setup()
    {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }







}
