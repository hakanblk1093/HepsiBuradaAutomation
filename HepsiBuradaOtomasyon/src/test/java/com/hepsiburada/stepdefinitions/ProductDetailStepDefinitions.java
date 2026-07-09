package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.ProductDetailPage;
import com.hepsiburada.pages.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class ProductDetailStepDefinitions {

    private final SearchPage searchPage = new SearchPage();
    private final ProductDetailPage productDetailPage = new ProductDetailPage();

    @And("kullanıcı arama sonucundaki ilk ürüne tıklar")
    public void kullanici_ilk_urune_tiklar() {
        searchPage.clickFirstProduct();
    }

    @Then("ürün detay sayfası açılmalı ve ürün adı görüntülenmelidir")
    public void urun_detay_sayfasi_acilir() {
        Assertions.assertFalse(productDetailPage.getProductTitle().isEmpty(),
                "Ürün detay sayfasında ürün adı görüntülenmedi");
    }

    @Then("ürün fiyatı görüntülenmelidir")
    public void urun_fiyati_goruntulenmelidir() {
        Assertions.assertFalse(productDetailPage.getProductPrice().isEmpty(),
                "Ürün fiyatı görüntülenmedi");
    }
}
