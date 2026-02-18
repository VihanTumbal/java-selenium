package com.vihan.capstone.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();
    protected static Properties CONFIG;

    static {
        CONFIG = new Properties();
        try {
            CONFIG.load(new FileInputStream("src/test/resources/config/config.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(CONFIG.getProperty("headless", "false"))) {
            options.addArguments("--headless-new");
        }
        options.addArguments("--window-size=1920,1200");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        TL.set(driver);
    }

    public void stop() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }

    public static WebDriver driver() {
        WebDriver d = TL.get();
        if (d == null) {
            throw new IllegalStateException("Driver not initialized");
        }
        return d;
    }
}