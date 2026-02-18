package com.vihan.capstone.pages;

import com.vihan.capstone.core.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import java.util.List;

public class HeaderBar extends BasePage {
    private final By communityTop = By.xpath("//a[normalize-space()='Community']");
    private final By photoDirectoryLink = By.xpath("//a[normalize-space()='Photo Directory' or normalize-space()='Photos']");
    private final By anyDownloadLink = By.cssSelector("[href='/download']");

    public HeaderBar(WebDriver driver) {
        super(driver);
    }

    public void goToGetWordPress() {
        List<WebElement> links = driver.findElements(anyDownloadLink);
        for (WebElement l : links) {
            if (l.isDisplayed() && l.getSize().getHeight() > 0 && l.getSize().getWidth() > 0) {
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'})", l);
                try {
                    new Actions(driver).moveToElement(l).pause(java.time.Duration.ofMillis(100)).perform();
                    wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(l)).click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click()", l);
                }
                return;
            }
        }
        driver.get("https://wordpress.org/download/");
    }

    public void openPhotoDirectoryFromCommunity() {
        if (present(communityTop)) {
            hover(communityTop);
            if (present(photoDirectoryLink)) {
                click(photoDirectoryLink);
            } else {
                click(By.xpath("//a[contains(@href, '/photos/')]"));
            }
        }
    }
}