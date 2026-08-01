package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Locale;

public class FilterStepDefinitions {

    private final SearchPage searchPage = new SearchPage();

    @And("kullanıcı {string} markasını filtreler")
    public void kullanici_markayi_filtreler(String brand) {
        searchPage.filterByBrand(brand);
    }

    @And("kullanıcı {string} marka filtresini kaldırır")
    public void kullanici_marka_filtresini_kaldirir(String brand) {
        searchPage.removeBrandFilter(brand);
    }

    @Then("uygulanan filtreler arasında {string} görüntülenmelidir")
    public void uygulanan_filtreler_dogrulanir(String expectedFilter) {
        List<String> appliedFilters = searchPage.getAppliedFilterNames();
        Assertions.assertTrue(appliedFilters.contains(expectedFilter),
                "Uygulanan filtreler arasında '" + expectedFilter + "' yok: " + appliedFilters);
    }

    @Then("listelenen tüm ürünler {string} markasına ait olmalıdır")
    public void tum_urunler_markaya_ait_olmalidir(String brand) {
        List<String> titles = searchPage.getProductTitleTexts();
        Assertions.assertFalse(titles.isEmpty(), "Filtre sonrası hiç ürün listelenmedi");

        List<String> digerMarkalar = titles.stream()
                .filter(title -> !title.toLowerCase(Locale.ROOT).contains(brand.toLowerCase(Locale.ROOT)))
                .toList();
        Assertions.assertTrue(digerMarkalar.isEmpty(),
                "'" + brand + "' markasına ait olmayan ürünler listelendi: " + digerMarkalar);
    }

    @Then("uygulanan filtre kalmamalıdır")
    public void uygulanan_filtre_kalmamalidir() {
        List<String> appliedFilters = searchPage.getAppliedFilterNames();
        Assertions.assertTrue(appliedFilters.isEmpty(),
                "Filtre kaldırıldığı halde uygulanan filtreler duruyor: " + appliedFilters);
    }
}
