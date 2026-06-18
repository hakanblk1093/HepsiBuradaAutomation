package com.hepsiburada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private final By emailInput = By.id("txtUserName");
    private final By clickContinue = By.id("btnSignUpSubmit");
    private final By emailValidationMessage = By.xpath("(//div[contains(.,'girmelisiniz')])[last()]");


    public void clickContinue(){
        jsClick(clickContinue);
    }

    public boolean isRegisterPageOpened() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).isDisplayed();
    }

    public String getEmailValidationMessage() {
        return getText(emailValidationMessage);
    }
}
