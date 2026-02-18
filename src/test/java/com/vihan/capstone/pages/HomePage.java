package com.vihan.capstone.pages;

import com.vihan.capstone.core.BasePage;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public String title() {
        return driver.getTitle();
    }

    public HeaderBar header() {
        return new HeaderBar(driver);
    }
}