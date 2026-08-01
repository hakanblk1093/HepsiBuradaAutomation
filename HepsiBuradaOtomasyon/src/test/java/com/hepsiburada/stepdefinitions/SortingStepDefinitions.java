package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class SortingStepDefinitions {

    private final SearchPage searchPage = new SearchPage();

    private double savedPrice;

    @And("kullanıcı ilk ürünün fiyatını kaydeder")
    public void kullanici_ilk_urun_fiyatini_kaydeder() {
        savedPrice = searchPage.getFirstProductPrice();
    }

    @And("kullanıcı sonuçları {string} seçeneğine göre sıralar")
    public void kullanici_sonuclari_siralar(String sortOption) {
        searchPage.sortBy(sortOption);
    }

    @Then("seçili sıralama {string} olmalıdır")
    public void secili_siralama_dogrulanir(String expectedOption) {
        Assertions.assertEquals(expectedOption, searchPage.getSelectedSortOption(),
                "Seçili sıralama beklenenle eşleşmiyor");
    }

    @Then("ilk ürünün fiyatı kaydedilen fiyattan düşük olmalıdır")
    public void ilk_urun_fiyati_daha_dusuk_olmalidir() {
        double currentPrice = searchPage.getFirstProductPrice();
        Assertions.assertTrue(currentPrice < savedPrice,
                "Artan fiyat sıralamasından sonra ilk ürün daha ucuz değil: " + currentPrice + " >= " + savedPrice);
    }

    @Then("ilk ürünün fiyatı kaydedilen fiyattan yüksek olmalıdır")
    public void ilk_urun_fiyati_daha_yuksek_olmalidir() {
        double currentPrice = searchPage.getFirstProductPrice();
        Assertions.assertTrue(currentPrice > savedPrice,
                "Azalan fiyat sıralamasından sonra ilk ürün daha pahalı değil: " + currentPrice + " <= " + savedPrice);
    }
}
