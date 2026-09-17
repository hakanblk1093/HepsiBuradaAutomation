package com.hepsiburada.pages;

import org.openqa.selenium.By;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SearchPage extends BasePage {

    private final By productTitles = By.cssSelector("h2[data-test-id^='title-']");
    private final By firstProductLink = By.cssSelector("h2[data-test-id^='title-'] a");
    private final By acceptCookies = By.id("hb-accept-all");
    private final By firstProductPrice = By.cssSelector("li[class*='productListContent'] [data-test-id^='final-price']");
    private final By appliedFilters = By.cssSelector("[data-test-id='filterbox-item-display-name']");
    // Sayfada birden fazla dropdown var (konum seçimi vb.), sıralama olanı metninden ayırt ediliyor
    private final By sortDropdownToggle = By.xpath("//div[@data-test-id='dropdown-toggle']"
            + "[contains(.,'sıralama') or contains(.,'fiyat') or contains(.,'satan')"
            + " or contains(.,'puan') or contains(.,'İndirim') or contains(.,'eklenen')]");

    // Sıralama etiketleri -> Hepsiburada "siralama=" URL parametre değerleri
    private static final Map<String, String> SORT_PARAMS = Map.of(
            "En düşük fiyat", "artanFiyat",
            "En yüksek fiyat", "azalanFiyat",
            "Çok satanlar", "cokSatan",
            "Yüksek puanlılar", "yuksekPuan",
            "İndirim oranı", "indirimOrani");

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
        String param = SORT_PARAMS.get(optionLabel);
        if (param == null) {
            throw new IllegalArgumentException("Bilinmeyen sıralama seçeneği: " + optionLabel);
        }
        // Sıralama seçenekleri client-side (javascript:void) ve React onClick'i WebDriver ile tetiklenen
        // tıklamalara yanıt vermiyor. Sıralama, "siralama=" parametresiyle doğrudan URL üzerinden güvenilir
        // biçimde uygulanıyor (ör. artanFiyat -> ürünler artan fiyata göre yeniden sıralanıyor).
        String url = driver.getCurrentUrl();
        int i = url.indexOf("q=");
        String query = i >= 0 ? url.substring(i + 2).split("&")[0] : "";
        driver.navigate().to("https://www.hepsiburada.com/ara?q=" + query + "&siralama=" + param);
        waitForResultsPageReady();
        // Liste ilk render'dan sonra client-side yeniden sıralanabildiği için, fiyat sıralamalarında
        // ilk iki ürün beklenen yönde sıralanana kadar bekle (erken okumayı önler).
        if (param.equals("artanFiyat")) {
            waitUntil(d -> firstTwoPricesSorted(true), Duration.ofSeconds(15));
        } else if (param.equals("azalanFiyat")) {
            waitUntil(d -> firstTwoPricesSorted(false), Duration.ofSeconds(15));
        }
    }

    private boolean firstTwoPricesSorted(boolean ascending) {
        List<WebElement> els = driver.findElements(
                By.cssSelector("li[class*='productListContent'] [data-test-id^='final-price']"));
        if (els.size() < 2) {
            return false;
        }
        try {
            double first = parsePrice(els.get(0).getText());
            double second = parsePrice(els.get(1).getText());
            return ascending ? first <= second : first >= second;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public String getSelectedSortOption() {
        // Aktif sıralama, dropdown metnine yansımadığı için URL'deki "siralama=" parametresinden okunur
        // ve etikete geri eşlenir.
        String url = driver.getCurrentUrl();
        int i = url.indexOf("siralama=");
        if (i < 0) {
            return "Önerilen sıralama";
        }
        String param = url.substring(i + "siralama=".length()).split("&")[0];
        return SORT_PARAMS.entrySet().stream()
                .filter(e -> e.getValue().equals(param))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(param);
    }

    /**
     * Sonuç listesindeki ilk {@code limit} ürünün fiyatını (soldan sağa görünen sırayla) döndürür.
     * Fiyat okuması reklam/sponsorlu kartlara denk gelmemesi için {@code li[class*='productListContent']}
     * ile scope'lanmıştır. Liste aşamalı render edildiği ve yeniden render sırasında eleman stale
     * olabildiği için, en az iki fiyat okunana kadar yeniden denenir.
     */
    public List<Double> getFirstProductPrices(int limit) {
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    List<WebElement> els = d.findElements(firstProductPrice);
                    List<Double> prices = new ArrayList<>();
                    for (WebElement el : els) {
                        if (prices.size() >= limit) {
                            break;
                        }
                        String text = el.getText().trim();
                        if (text.isEmpty()) {
                            continue;
                        }
                        try {
                            prices.add(parsePrice(text));
                        } catch (RuntimeException ignored) {
                            // fiyatı ayrıştırılamayan kartı atla
                        }
                    }
                    return prices.size() >= 2 ? prices : null;
                });
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
        // Site marka filtresini artık URL'e 'markalar=' olarak yansıtmıyor; gerçek kullanıcı çıktısı
        // olan uygulanmış filtre rozetini bekle. Rozet gelmezse bir kez daha dene.
        if (!waitUntil(d -> isBrandFilterApplied(brand))) {
            reloadResults();
            jsClick(brandCheckbox(value));
            waitUntil(d -> isBrandFilterApplied(brand));
        }
    }

    public void removeBrandFilter(String brand) {
        String value = brand.toLowerCase(Locale.ROOT);
        // Marka filtresi, uygulamada olduğu gibi marka checkbox'ı tekrar tıklanarak (kapatılarak) kaldırılır.
        for (int attempt = 0; attempt < 3 && isBrandFilterApplied(brand); attempt++) {
            if (attempt > 0) {
                reloadResults();
            } else {
                waitForResultsPageReady();
            }
            jsClick(brandCheckbox(value));
            waitUntil(d -> !isBrandFilterApplied(brand), Duration.ofSeconds(8));
        }
    }

    private boolean isBrandFilterApplied(String brand) {
        return getAppliedFilterNames().stream().anyMatch(name -> name.equalsIgnoreCase(brand));
    }


    private void reloadResults() {
        driver.navigate().refresh();
        waitForResultsPageReady();
        // Sıralama dropdown'ı yenileme sonrası geç render edilebiliyor, sonraki okumalar için hazır olmasını bekle
        wait.until(ExpectedConditions.presenceOfElementLocated(sortDropdownToggle));
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
        // Ürün kartları aşamalı (lazy) render edildiğinden başlıklar bir an boş gelebiliyor ve liste
        // yeniden render edilirken eleman stale olabiliyor. Stale'i yok sayarak, boş olmayan başlıklardan
        // oluşan kararlı bir liste dolana kadar okumayı yeniden dene.
        return new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    List<String> texts = d.findElements(productTitles).stream()
                            .map(WebElement::getText)
                            .map(String::trim)
                            .filter(t -> !t.isBlank())
                            .toList();
                    return texts.isEmpty() ? null : texts;
                });
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
}