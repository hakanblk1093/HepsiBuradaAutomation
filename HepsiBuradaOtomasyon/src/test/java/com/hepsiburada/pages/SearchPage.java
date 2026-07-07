package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By noResultsMessage = By.xpath("//*[contains(., 'bulunamad')]");
    public List<WebElement> getProductTitles() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        return driver.findElements(productTitles);
    }

    public String getNoResultsMessage() {
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        String pageSource = driver.getPageSource();
        if (pageSource.contains("bulunamad")) {
            return "ürün bulunamadı";
        }
        return "";
    }
}