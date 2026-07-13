package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.ProductDetailPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class CartStepDefinitions {

    private final ProductDetailPage productDetailPage = new ProductDetailPage();
    private final HomePage homePage = new HomePage();

    @And("kullanıcı ürün detay sayfasında sepete ekle butonuna tıklar")
    public void kullanici_sepete_ekle_tiklar() {
        productDetailPage.clickAddToCartButton();
    }

    @Then("sepete ekleme onay mesajı görüntülenmelidir")
    public void sepete_ekleme_onay_mesaji_goruntulenir() {
        String message = productDetailPage.getCartConfirmationMessage();
        Assertions.assertTrue(message.contains("sepetinizde"), "Sepete ekleme onay mesajı görüntülenmedi");
    }

    @And("sepet simgesindeki ürün sayısı {string} olmalıdır")
    public void sepet_urun_sayisi_dogrulanir(String expectedCount) {
        Assertions.assertEquals(expectedCount, homePage.getCartItemCount(), "Sepetteki ürün sayısı beklenenle eşleşmiyor");
    }
}