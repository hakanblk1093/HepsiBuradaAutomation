package com.hepsiburada.pages;

import com.hepsiburada.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    private static final String DEEP_TEXT_SEARCH_SCRIPT =
            "function deepFind(root, text) {\n" +
                    "  const stack = [root];\n" +
                    "  while (stack.length) {\n" +
                    "    const node = stack.pop();\n" +
                    "    if (node.shadowRoot) stack.push(node.shadowRoot);\n" +
                    "    const children = node.children || [];\n" +
                    "    for (let i = 0; i < children.length; i++) stack.push(children[i]);\n" +
                    "    if (node.nodeType === 1 && node.textContent &&\n" +
                    "        node.textContent.toLowerCase().includes(text.toLowerCase())) {\n" +
                    "      return node;\n" +
                    "    }\n" +
                    "  }\n" +
                    "  return null;\n" +
                    "}\n" +
                    "return deepFind(document.body, arguments[0]);";

    protected String waitForShadowDomText(String textFragment, Duration timeout) {
        try {
            WebElement el = new WebDriverWait(driver, timeout).until(d ->
                    (WebElement) ((JavascriptExecutor) d).executeScript(DEEP_TEXT_SEARCH_SCRIPT, textFragment));
            return el.getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected void hover(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        actions.moveToElement(element).perform();
    }

    protected void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}