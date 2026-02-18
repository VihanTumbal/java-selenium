package com.vihan.capstone.steps;

import com.vihan.capstone.core.BaseTest;
import com.vihan.capstone.core.Texts;
import com.vihan.capstone.pages.DownloadPage;
import com.vihan.capstone.pages.HomePage;
import com.vihan.capstone.pages.PhotoDirectoryPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class WordPressSteps extends BaseTest {
    private HomePage home;
    private DownloadPage download;
    private PhotoDirectoryPage photos;

    @Given("I open the WordPress site")
    public void i_open_site() {
        home = new HomePage(driver());
        home.open(CONFIG.getProperty("baseUrl"));
        Assert.assertTrue(home.title().toLowerCase().contains("wordpress.org"));
    }

    @When("I go to the Get WordPress page via the header")
    public void i_go_to_get_wp() {
        home.header().goToGetWordPress();
        download = new DownloadPage(driver());
    }

    @Then("I should see the primary heading as Get WordPress")
    public void i_verify_h1() {
        String h1 = download.heading();
        Assert.assertEquals(Texts.normalize(h1), "Get WordPress");
    }

    @When("I open the Photo Directory from Community")
    public void i_open_photos() {
        home.header().openPhotoDirectoryFromCommunity();
        photos = new PhotoDirectoryPage(driver());
    }

    @When("I search photos for {string}")
    public void i_search_photos_for(String term) {
        if ("<searchFromConfig>".equals(term)) {
            term = CONFIG.getProperty("searchTerm", "sunset");
        }
        photos.search(term);
    }

    @Then("photo results should be visible")
    public void photo_results_should_be_visible() {
        Assert.assertTrue(photos.hasResults());
    }
}