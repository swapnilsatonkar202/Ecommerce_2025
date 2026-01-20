package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {

    WebDriver driver;

    // 🛒 Cart Icon
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Inventory page validation
    public boolean isInventoryPageLoaded() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    // ✅ Open Cart Page
    public void openCart() {
        cartIcon.click();
    }
}
