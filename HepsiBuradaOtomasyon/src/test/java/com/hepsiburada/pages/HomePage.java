package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By acceptCookies = By.id("hb-accept-all");
    private final By accountMenu = By.id("myAccount");
    private final By loginLink = By.id("login");
    private final By registerLink= By.id("register");

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
}