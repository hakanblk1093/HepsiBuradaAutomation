package com.hepsiburada.pages;

import org.openqa.selenium.By;

public class ProductDetailPage extends BasePage {

    private final By productTitle = By.cssSelector("[data-test-id='title']");
    private final By productPrice = By.cssSelector("[data-test-id='price']");

    public String getProductTitle() {
        return getText(productTitle);
    }

    public String getProductPrice() {
        return getText(productPrice);
    }
}
