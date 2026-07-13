package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.SearchPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchStepDefinitions {

    private final HomePage homePage = new HomePage();
    private final SearchPage searchPage = new SearchPage();

    @When("kullanıcı arama kutusuna {string} yazıp arama yapar")
    public void kullanici_arama_yapar(String query) {
        homePage.searchProduct(query);
    }

    @Then("arama sonuçları listelenmelidir")
    public void arama_sonuclari_listelenmelidir() {
        List<WebElement> titles = searchPage.getProductTitles();

        Assertions.assertFalse(titles.isEmpty(), "Arama sonucu ürün listelenmedi");
    }

    @Then("sonuç bulunamadı mesajı görüntülenmelidir")
    public void sonuc_bulunamadi_mesaji_goruntulenir() {
        String message = searchPage.getNoResultsMessage();
        Assertions.assertFalse(message.isEmpty(), "Sonuç bulunamadı mesajı görüntülenmedi");
    }
}