package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    private final By removeButton = By.cssSelector("a[aria-label='Sepetten Çıkar']");
    private final By emptyCartMessage = By.xpath("//*[contains(text(),'Sepetin şu an boş')]");
    private final By increaseQuantityButton = By.cssSelector("a[aria-label='Ürünü Arttır']");
    private final By quantityInput = By.cssSelector("input[name='quantity']");

    public void clickRemoveButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(removeButton));
        jsClick(removeButton);
    }

    public String getEmptyCartMessage() {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage))
                .getText();
    }

    public void clickIncreaseQuantityButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(increaseQuantityButton));
        jsClick(increaseQuantityButton);
    }

    public String getProductQuantity() {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(quantityInput))
                .getAttribute("value");
    }
}