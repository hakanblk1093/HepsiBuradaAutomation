package com.hepsiburada.pages;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By firstProductLink = By.cssSelector("h2[data-test-id^='title-'] a");
    private final By acceptCookies = By.id("hb-accept-all");
    private final By firstProductPrice = By.cssSelector("[data-test-id='final-price-1']");
    private final By appliedFilters = By.cssSelector("[data-test-id='filterbox-item-display-name']");
    // Sayfada birden fazla dropdown var (konum seçimi vb.), sıralama olanı metninden ayırt ediliyor
    private final By sortDropdownToggle = By.xpath("//div[@data-test-id='dropdown-toggle']"
            + "[contains(.,'sıralama') or contains(.,'fiyat') or contains(.,'satan')"
            + " or contains(.,'puan') or contains(.,'İndirim') or contains(.,'eklenen')]");

    public void clickFirstProduct() {
        String urlBefore = driver.getCurrentUrl();
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        clickFirstNavigableProductLink();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> !Objects.equals(d.getCurrentUrl(), urlBefore));
        } catch (TimeoutException e) {
            // Geç açılan çerez banner'ı tıklamayı geçersiz kılmış olabilir, tekrar dene
            try {
                WebElement banner = driver.findElement(acceptCookies);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);
            } catch (Exception ignored) {
            }
            clickFirstNavigableProductLink();
        }
    }

    /**
     * Reklam/sponsorlu ürün kartlarının başlık linki gerçek ürün sayfasına gitmiyor (href yok),
     * bu yüzden ilk eşleşen değil, href'i dolu olan ilk gerçek ürün linki tıklanır.
     */
    private void clickFirstNavigableProductLink() {
        WebElement link = driver.findElements(firstProductLink).stream()
                .filter(el -> el.getAttribute("href") != null && !Objects.requireNonNull(el.getAttribute("href")).isEmpty())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Tıklanabilir (href'li) bir ürün linki bulunamadı"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public List<WebElement> getProductTitles() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        return driver.findElements(productTitles);
    }

    public String getNoResultsMessage() {
        return waitForShadowDomText("bulunamad", Duration.ofSeconds(10));
    }

    public void sortBy(String optionLabel) {
        waitForResultsPageReady();
        String firstPriceBefore = firstProductPriceText();
        click(sortDropdownToggle);
        By option = By.xpath("//*[@data-test-id='dropdown-content']//a[normalize-space(.)='" + optionLabel + "']");
        click(option);
        // Sıralama seçimi arama sonuçlarını "siralama" parametresiyle yeniden yükler
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("siralama="));
        // URL parametresi liste yeniden render edilmeden değişiyor, listenin tazelenmesini bekle
        if (!waitUntil(d -> !firstProductPriceText().isEmpty() && !firstProductPriceText().equals(firstPriceBefore))) {
            // Site bazen URL'i güncelleyip listeyi yenilemiyor; sıralama parametresi URL'de olduğu için reload yeterli
            reloadResults();
        }
    }

    public String getSelectedSortOption() {
        return getText(sortDropdownToggle).trim();
    }

    public double getFirstProductPrice() {
        // Liste yeniden render edilirken eleman stale olabildiği için metin okunana kadar denenir
        String raw = new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    String text = firstProductPriceText();
                    return text.isEmpty() ? null : text;
                });
        return parsePrice(raw);
    }

    public void filterByBrand(String brand) {
        String value = brand.toLowerCase(Locale.ROOT);
        waitForResultsPageReady();
        jsClick(brandCheckbox(value));
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("markalar=" + value));
        // Filtre rozetleri URL değişiminden sonra render ediliyor
        if (!waitUntil(d -> isBrandFilterApplied(brand))) {
            // Site bazen URL'i güncelleyip paneli yenilemiyor; marka parametresi URL'de olduğu için reload yeterli
            reloadResults();
            waitUntil(d -> isBrandFilterApplied(brand));
        }
    }

    public void removeBrandFilter(String brand) {
        String value = brand.toLowerCase(Locale.ROOT);
        // Filtre yeni uygulandığı için panel hâlâ güncelleniyor olabilir ve tıklama kaybolabiliyor;
        // sayfa yenilenerek gerçek duruma dönülüp tekrar deneniyor
        for (int attempt = 0; attempt < 3 && !filterCleared(); attempt++) {
            if (attempt > 0) {
                reloadResults();
            } else {
                waitForResultsPageReady();
            }
            jsClick(brandCheckbox(value));
            waitUntil(d -> filterCleared(), Duration.ofSeconds(8));
        }
    }

    private boolean isBrandFilterApplied(String brand) {
        return getAppliedFilterNames().stream().anyMatch(name -> name.equalsIgnoreCase(brand));
    }

    private boolean filterCleared() {
        return !Objects.requireNonNull(driver.getCurrentUrl()).contains("markalar=") && getAppliedFilterNames().isEmpty();
    }

    private void reloadResults() {
        driver.navigate().refresh();
        waitForResultsPageReady();
    }

    /**
     * Sonuç listesi ve filtre paneli tamamen hazır olmadan yapılan tıklamalar
     * URL'i değiştirse de filtre panelini güncellemiyor.
     */
    private void waitForResultsPageReady() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        waitUntil(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id='vertical-scroll-filter']")));
    }

    public List<String> getAppliedFilterNames() {
        return textsOf(appliedFilters);
    }

    public List<String> getProductTitleTexts() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitles));
        return textsOf(productTitles);
    }

    /** Liste yeniden render edilirken eleman stale olabildiği için bir kez yeniden okunur */
    private List<String> textsOf(By locator) {
        try {
            return readTexts(locator);
        } catch (StaleElementReferenceException e) {
            return readTexts(locator);
        }
    }

    private List<String> readTexts(By locator) {
        return driver.findElements(locator).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    private String firstProductPriceText() {
        List<WebElement> prices = driver.findElements(firstProductPrice);
        try {
            return prices.isEmpty() ? "" : prices.get(0).getText().trim();
        } catch (StaleElementReferenceException e) {
            return "";
        }
    }

    /**
     * Sonuç listesi client-side yeniden render edildiği için beklenen durum oluşmazsa
     * assert'in gerçek farkı raporlamasına izin verilir.
     *
     * @return koşul sağlandıysa true, süre dolduysa false
     */
    private boolean waitUntil(ExpectedCondition<Boolean> condition) {
        return waitUntil(condition, Duration.ofSeconds(30));
    }

    private boolean waitUntil(ExpectedCondition<Boolean> condition, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .ignoring(StaleElementReferenceException.class)
                    .until(condition);
            return true;
        } catch (TimeoutException e) {
            // Beklenen duruma ulaşılmadıysa doğrulama adımı gerçek sonucu raporlasın
            return false;
        }
    }

    private By brandCheckbox(String value) {
        return By.cssSelector("input[name='markalar'][value='" + value + "']");
    }

    /** "47.999 TL" -> 47999.0, "868,04 TL" -> 868.04 */
    private double parsePrice(String raw) {
        String normalized = raw.replaceAll("[^0-9.,]", "").replace(".", "").replace(',', '.');
        return Double.parseDouble(normalized);
    }
}