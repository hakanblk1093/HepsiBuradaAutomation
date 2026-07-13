package com.hepsiburada.pages;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By firstProductLink = By.cssSelector("h2[data-test-id^='title-'] a");
    private final By acceptCookies = By.id("hb-accept-all");

    public void clickFirstProduct() {
        String urlBefore = driver.getCurrentUrl();
        jsClick(firstProductLink);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> !Objects.equals(d.getCurrentUrl(), urlBefore));
        } catch (TimeoutException e) {
            // Geç açılan çerez banner'ı tıklamayı geçersiz kılmış olabilir, tekrar dene
            try {
                WebElement banner = driver.findElement(acceptCookies);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
            } catch (Exception ignored) {
            }
            jsClick(firstProductLink);
        }
    }

    public List<WebElement> getProductTitles() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        return driver.findElements(productTitles);
    }

    public String getNoResultsMessage() {
        return waitForShadowDomText("bulunamad", Duration.ofSeconds(10));
    }
}