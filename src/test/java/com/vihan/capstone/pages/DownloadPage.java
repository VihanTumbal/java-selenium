package com.vihan.capstone.pages;

import com.vihan.capstone.core.BasePage;
import com.vihan.capstone.core.Texts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DownloadPage extends BasePage {
    private final By h1 = By.cssSelector("main h1");

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    public String heading() {
        return Texts.normalize(text(h1));
    }
}