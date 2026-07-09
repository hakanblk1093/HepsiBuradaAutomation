package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By firstProductLink = By.cssSelector("h2[data-test-id^='title-'] a");

    public void clickFirstProduct() {
        jsClick(firstProductLink);
    }

    public List<WebElement> getProductTitles() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        return driver.findElements(productTitles);
    }

    public String getNoResultsMessage() {
        return waitForShadowDomText("bulunamad", Duration.ofSeconds(10));
    }
}