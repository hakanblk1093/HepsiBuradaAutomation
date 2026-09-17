package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    private final By removeButton = By.cssSelector("a[aria-label='Sepetten Çıkar']");
    private final By emptyCartMessage = By.xpath("//*[contains(text(),'Sepetin şu an boş')]");
    private final By increaseQuantityButton = By.cssSelector("a[aria-label='Ürünü Arttır']");
    private final By decreaseQuantityButton = By.cssSelector("a[aria-label='Ürünü Azalt']");
    private final By quantityInput = By.cssSelector("input[name='quantity']");
    // Sepet satırı fiyatının stabil bir data-test-id'si yok; CSS-module insan-adı prefix'i ('product_price_')
    // üzerinden, iç konteyner ('container'/'box') sınıflarını eleyerek satır toplamı elemanı seçilir.
    private final By lineTotalPrice =
            By.cssSelector("div[class*='product_price_']:not([class*='container']):not([class*='box'])");

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

    public void clickDecreaseQuantityButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(decreaseQuantityButton));
        jsClick(decreaseQuantityButton);
    }

    public String getQuantityValue() {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(quantityInput))
                .getAttribute("value");
    }

    public void waitForQuantityValue(String expectedValue) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> expectedValue.equals(getQuantityValue()));
    }

    /** Adet 1 iken azaltma butonu DOM'dan tamamen kaldırılıyor (disabled değil, yok) */
    public boolean isDecreaseButtonPresent() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        return !driver.findElements(decreaseQuantityButton).isEmpty();
    }

    /**
     * Sepet satırının o anki toplam tutarını (birim fiyat × adet) döndürür. Liste yeniden render
     * edilirken eleman stale/boş olabildiği için metin okunana kadar denenir.
     */
    public double getLineTotalPrice() {
        String raw = new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    List<WebElement> els = d.findElements(lineTotalPrice);
                    if (els.isEmpty()) {
                        return null;
                    }
                    String text = els.get(0).getText().trim();
                    return text.isEmpty() ? null : text;
                });
        return parsePrice(raw);
    }

    /**
     * Adet değişince fiyat, adet input'undan bir tık sonra güncellendiği için, satır toplamı
     * beklenen değere (tolerans dahilinde) oturana kadar bekler. Süre dolarsa son okunan değeri
     * döndürür ki doğrulama adımı gerçek farkı raporlayabilsin.
     */
    public double waitForLineTotalNear(double expected, double tolerance) {
        double[] last = {Double.NaN};
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> {
                        last[0] = getLineTotalPrice();
                        return Math.abs(last[0] - expected) <= tolerance;
                    });
        } catch (TimeoutException ignored) {
            // beklenen değere ulaşılmadı; son okunan değerle assert gerçek farkı göstersin
        }
        return last[0];
    }
}