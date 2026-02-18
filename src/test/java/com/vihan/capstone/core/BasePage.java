package com.vihan.capstone.core;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions act;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.act = new Actions(driver);
    }

    protected WebElement el(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void click(By by) {
        WebElement e = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'})", e);
        wait.until(ExpectedConditions.visibilityOf(e));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(e)).click();
        } catch (Exception ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", e);
        }
    }

    protected void type(By by, String txt) {
        WebElement e = el(by);
        e.clear();
        e.sendKeys(txt);
    }

    protected void hover(By by) {
        act.moveToElement(el(by)).pause(Duration.ofMillis(200)).perform();
    }

    protected String text(By by) {
        return el(by).getText();
    }

    protected boolean present(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}