package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("txtUserName");
    private final By passwordInput = By.id("txtPassword");
    private final By loginButton = By.id("btnLogin");
    private final By errorMessage = By.cssSelector("[data-test-id='inline-alert-label']");
    private final By emailValidationMessage = By.xpath("(//div[contains(.,'e-posta adresi girmelisiniz')])[last()]");
    private final By forgotPasswordLink = By.xpath("//span[contains(text(),'ifremi unuttum')]");
    private final By forgotPasswordTitle = By.xpath("//h1[contains(text(),'ifre yenileme')]");

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLoginButton() {
        jsClick(loginButton);
    }

    public boolean isLoginPageOpened() {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> {
            String url = d.getCurrentUrl();
            return url.contains("giris.hepsiburada.com")
                    || url.contains("oauth.hepsiburada.com")
                    || url.contains("/uyelik/giris");
        });
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void enterInvalidEmailAndBlur(String email) {
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
        emailField.sendKeys(Keys.TAB);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public String getEmailValidationMessage() {
        return getText(emailValidationMessage);
    }

    public void clickForgotPassword() {
        jsClick(forgotPasswordLink);
    }

    public String getForgotPasswordTitle() {
        return getText(forgotPasswordTitle);
    }
}