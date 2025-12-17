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

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = ".cart_item")
    List<WebElement> cartItems;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Business Rule: Remove items if more than maxItems
    public void ensureMaxItems(int maxItems) {

        while (cartItems.size() > maxItems) {

            List<WebElement> removeButtons =
                    driver.findElements(By.cssSelector("button.cart_button"));

            wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();

            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                    By.cssSelector(".cart_item"), cartItems.size()));
        }
    }

    public int getCartItemCount() {
        return cartItems.size();
    }
}
