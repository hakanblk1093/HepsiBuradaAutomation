package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.CartPage;
import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.ProductDetailPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class CartStepDefinitions {

    private final ProductDetailPage productDetailPage = new ProductDetailPage();
    private final HomePage homePage = new HomePage();
    private final CartPage cartPage = new CartPage();

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

    @And("kullanıcı sepete git bağlantısına tıklar")
    public void kullanici_sepete_git_tiklar() {
        productDetailPage.clickGoToCartLink();
    }

    @And("kullanıcı sepetten ürünü çıkarır")
    public void kullanici_sepetten_urunu_cikarir() {
        cartPage.clickRemoveButton();
    }

    @Then("sepetin boş olduğu mesajı görüntülenmelidir")
    public void sepetin_bos_oldugu_mesaji_goruntulenir() {
        String message = cartPage.getEmptyCartMessage();
        Assertions.assertTrue(message.contains("boş"), "Sepetin boş olduğu mesajı görüntülenmedi");
    }

    @And("kullanıcı sepette ürün adedini arttırır")
    public void kullanici_urun_adedini_arttirir() {
        cartPage.clickIncreaseQuantityButton();
    }

    @Then("sepetteki ürün adedi {string} olmalıdır")
    public void sepetteki_urun_adedi_dogrulanir(String expectedQuantity) {
        cartPage.waitForQuantityValue(expectedQuantity);
        Assertions.assertEquals(expectedQuantity, cartPage.getQuantityValue(), "Sepetteki ürün adedi beklenenle eşleşmiyor");
    }
}