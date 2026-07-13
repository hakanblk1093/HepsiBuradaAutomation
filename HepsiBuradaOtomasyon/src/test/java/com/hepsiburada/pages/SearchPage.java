package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By firstProductLink = By.cssSelector("h2[data-test-id^='title-'] a");

    public void clickFirstProduct() {
        Set<String> handlesBeforeClick = driver.getWindowHandles();
        jsClick(firstProductLink);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.getWindowHandles().size() > handlesBeforeClick.size());
            Set<String> newHandles = new HashSet<>(driver.getWindowHandles());
            newHandles.removeAll(handlesBeforeClick);
            driver.switchTo().window(newHandles.iterator().next());
        } catch (TimeoutException e) {
            // Ürün aynı sekmede açıldıysa yeni sekmeye geçmeye gerek yok
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