package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage extends BasePage {

    private final By productTitle = By.cssSelector("[data-test-id='title']");
    private final By productPrice = By.cssSelector("[data-test-id='price']");
    private final By favoriteButton = By.cssSelector("[class^='customerAccount-Like-']");
    private final By addToCartButton = By.cssSelector("[data-test-id='addToCart']");
    private final By cartConfirmationMessage = By.xpath("//span[contains(text(),'sepetinizde')]");

    public String getProductTitle() {
        return getText(productTitle);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }

    public void clickFavoriteButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(favoriteButton));
        jsClick(favoriteButton);
    }

    public void clickAddToCartButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.presenceOfElementLocated(addToCartButton));
        jsClick(addToCartButton);
    }

    public String getCartConfirmationMessage() {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(cartConfirmationMessage))
                .getText();
    }
}
