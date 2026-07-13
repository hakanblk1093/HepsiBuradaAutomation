package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class HomePage extends BasePage {

    private final By acceptCookies = By.id("hb-accept-all");
    private final By accountMenu = By.id("myAccount");
    private final By loginLink = By.id("login");
    private final By registerLink= By.id("register");
    private final By searchInput = By.cssSelector("[data-test-id='search-bar-input']");
    private final By cartItemCount = By.id("cartItemCount");

    public void acceptCookiesIfPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookies)).click();
        } catch (Exception e) {
            // Çerez popup'ı çıkmadıysa devam et
        }
    }

    public void clickLoginLink() {
    hover(accountMenu);
    jsClick(loginLink);
    }

    public void clickRegisterLink() {
        hover(accountMenu);
        jsClick(registerLink);
    }

    public void searchProduct(String query) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.sendKeys(query + Keys.ENTER);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/ara"));
        } catch (TimeoutException e) {
            // Öneri bulunamayan sorgularda Enter arama sayfasına yönlendirmiyor
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            driver.navigate().to("https://www.hepsiburada.com/ara?q=" + encoded);
        }
    }

    public String getCartItemCount() {
        return getText(cartItemCount);
    }
}