package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SortingStepDefinitions {

    private final SearchPage searchPage = new SearchPage();

    @And("kullanıcı sonuçları {string} seçeneğine göre sıralar")
    public void kullanici_sonuclari_siralar(String sortOption) {
        searchPage.sortBy(sortOption);
    }

    @Then("seçili sıralama {string} olmalıdır")
    public void secili_siralama_dogrulanir(String expectedOption) {
        Assertions.assertEquals(expectedOption, searchPage.getSelectedSortOption(),
                "Seçili sıralama beklenenle eşleşmiyor");
    }

    @Then("ürünler artan fiyata göre sıralanmış olmalıdır")
    public void urunler_artan_fiyata_gore_siralanmalidir() {
        assertSorted(true);
    }

    @Then("ürünler azalan fiyata göre sıralanmış olmalıdır")
    public void urunler_azalan_fiyata_gore_siralanmalidir() {
        assertSorted(false);
    }

    /**
     * Varsayılan sıradaki ilk ürünle karşılaştırmak yerine (varsayılan sıra ucuz bir aksesuarla
     * başlayabildiği için güvenilmez), listedeki ilk ürünlerin fiyatlarının gerçekten artan/azalan
     * yönde sıralı olduğunu doğrular.
     */
    private void assertSorted(boolean ascending) {
        List<Double> prices = searchPage.getFirstProductPrices(5);
        for (int i = 1; i < prices.size(); i++) {
            double prev = prices.get(i - 1);
            double curr = prices.get(i);
            boolean inOrder = ascending ? prev <= curr : prev >= curr;
            Assertions.assertTrue(inOrder,
                    (ascending ? "Artan" : "Azalan") + " fiyat sıralaması bozuk: " + prices);
        }
    }
}
