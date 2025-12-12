package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddToCartPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "shopping_cart_link")
    WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    WebElement cartCount;

    public AddToCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ⭐ FINAL WORKING METHOD
    public void addProducts(int numberOfProducts) {

        for (int i = 0; i < numberOfProducts; i++) {

            // Fetch fresh buttons list each time
            List<WebElement> buttons =
                    driver.findElements(By.cssSelector("button.btn_inventory"));

            wait.until(ExpectedConditions.elementToBeClickable(buttons.get(i))).click();
        }
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartCount));
            return Integer.parseInt(cartCount.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        cartIcon.click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }
}
