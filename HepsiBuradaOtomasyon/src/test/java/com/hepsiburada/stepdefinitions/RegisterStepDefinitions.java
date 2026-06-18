package com.hepsiburada.stepdefinitions;

import com.hepsiburada.pages.HomePage;
import com.hepsiburada.pages.RegisterPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class RegisterStepDefinitions {

    private final HomePage homePage = new HomePage();
    private final RegisterPage registerPage = new RegisterPage();

    @When("kullanıcı üye ol bağlantısına tıklar")
    public void kullanici_uye_ol_tiklar() {
        homePage.clickRegisterLink();
    }

    @Then("üye ol sayfası açılmalıdır")
    public void uye_ol_sayfasi_acilir() {
        Assertions.assertTrue(registerPage.isRegisterPageOpened(),
                "Üye ol sayfası açılmadı");
    }

    @And("kullanıcı email girmeden devam eder")
    public void kullanici_email_girmeden_devam_eder() {
        registerPage.clickContinue();
    }

    @Then("email zorunluluk uyarısı görüntülenmelidir")
    public void email_zorunluluk_uyarisi_goruntulenir() {
        String validation = registerPage.getEmailValidationMessage();
        Assertions.assertFalse(validation.isEmpty(), "Email zorunluluk uyarısı görüntülenmedi");
    }
}