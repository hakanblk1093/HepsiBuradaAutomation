package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.LoginPage;
import com.hepsiburada.pages.ProductDetailPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class FavoriteStepDefinitions {

    private final ProductDetailPage productDetailPage = new ProductDetailPage();
    private final LoginPage loginPage = new LoginPage();

    @And("kullanıcı ürün detay sayfasında favorilere ekle butonuna tıklar")
    public void kullanici_favorilere_ekle_tiklar() {
        productDetailPage.clickFavoriteButton();
    }

    @Then("kullanıcı giriş sayfasına yönlendirilmelidir")
    public void kullanici_giris_sayfasina_yonlendirilir() {
        Assertions.assertTrue(loginPage.isLoginPageOpened(), "Kullanıcı giriş sayfasına yönlendirilmedi");
    }
}
