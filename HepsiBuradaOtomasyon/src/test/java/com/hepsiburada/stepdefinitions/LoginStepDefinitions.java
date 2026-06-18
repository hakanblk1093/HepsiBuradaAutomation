package com.hepsiburada.stepdefinitions;
import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.LoginPage;
import com.hepsiburada.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;

public class LoginStepDefinitions {

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();

    @Given("kullanıcı Hepsiburada ana sayfasındadır")
    public void kullanici_ana_sayfadadir() {
        DriverManager.getDriver().get("https://www.hepsiburada.com");
        homePage.acceptCookiesIfPresent();
    }

    @And("kullanıcı giriş yap bağlantısına tıklar")
    public void kullanici_giris_yap_tiklar() {
        homePage.clickLoginLink();
    }

    @When("kullanıcı geçersiz {string} ve {string} ile giriş yapar")
    public void kullanici_gecersiz_giris_yapar(String email, String password) {
        loginPage.login(email, password);
    }

    @Then("bir hata mesajı görüntülenmelidir")
    public void hata_mesaji_goruntulenir() {
        String error = loginPage.getErrorMessage();
        Assertions.assertFalse(error.isEmpty(), "Hata mesajı görüntülenmedi");
    }

    @When("kullanıcı geçersiz formatta {string} ile giriş yapar")
    public void kullanici_gecersiz_formatta_giris_yapar(String email) {
        loginPage.enterInvalidEmailAndBlur(email);
    }

    @Then("geçerli email uyarısı görüntülenmelidir")
    public void gecerli_email_uyarisi_goruntulenir() {
        String validation = loginPage.getEmailValidationMessage();
        Assertions.assertFalse(validation.isEmpty(), "Email validasyon mesajı görüntülenmedi");
    }


    @When("kullanıcı şifremi unuttum bağlantısına tıklar")
    public void kullanici_sifremi_unuttum_tiklar() {
        loginPage.clickForgotPassword();
    }

    @Then("şifre yenileme sayfası açılmalıdır")
    public void sifre_yenileme_sayfasi_acilir() {
        String title = loginPage.getForgotPasswordTitle();
        Assertions.assertTrue(title.contains("yenileme"), "Şifre yenileme sayfası açılmadı");
    }
}