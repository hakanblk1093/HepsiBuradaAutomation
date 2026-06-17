package com.hepsiburada.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By emailInput = By.id("txtUserName");
    private final By passwordInput = By.id("txtPassword");
    private final By loginButton = By.id("btnLogin");
    private final By emailValidationMessage = By.xpath("(//div[contains(.,'e-posta adresi girmelisiniz')])[last()]");
    private final By errorMessage = By.cssSelector("[data-test-id='inline-alert-label']");    private final By forgotPasswordLink = By.xpath("//span[contains(text(),'ifremi unuttum')]");
    private final By forgotPasswordTitle = By.xpath("//h1[contains(text(),'ifre yenileme')]");
    private final By accountName = By.cssSelector("span.sf-OldMyAccount-sS_G2sunmDtZl9Tld5PR");

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLoginButton() {
        jsClick(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
    public String getEmailValidationMessage() 
    {
    return getText(emailValidationMessage);
    }
    public void enterInvalidEmailAndBlur(String email) {
        org.openqa.selenium.WebElement emailField =wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
        emailField.sendKeys(org.openqa.selenium.Keys.TAB);
    }

    public void clickForgotPassword() {
    jsClick(forgotPasswordLink);
    }

   public String getForgotPasswordTitle() {
    return getText(forgotPasswordTitle);
   }

}