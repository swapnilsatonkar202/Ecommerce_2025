package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.InventoryPage;
import com.qa.pages.AddToCartPage;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    LoginPage loginPage;
    InventoryPage inventoryPage;
    AddToCartPage cartPage;

    // -----------------------------
    // 1️⃣ VALID LOGIN TEST
    // -----------------------------
    @Test(priority = 1)
    public void verifyValidLogin() {
        System.out.println("=== Starting Valid Login Test ===");
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        // Wait until inventory page loads
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        Assert.assertTrue(inventoryPage.isInventoryPageLoaded(), "❌ Inventory page not loaded after login!");
        System.out.println("✅ Valid login test passed.");
    }

    // -----------------------------
    // 2️⃣ INVALID LOGIN TEST
    // -----------------------------
    @Test(priority = 2)
    public void verifyInvalidLogin() {
        System.out.println("=== Starting Invalid Login Test ===");
        loginPage = new LoginPage(driver);

        loginPage.login("invalid_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "❌ Error message not displayed for invalid login!");
        System.out.println("✅ Invalid login test passed (User not allowed).");
    }

    // -----------------------------
    // 3️⃣ ADD TO CART TEST
    // -----------------------------
    @Test(priority = 3)
    public void verifyAddToCart() {
        System.out.println("=== Starting Add To Cart Test ===");
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new AddToCartPage(driver);

        // 1️⃣ Login successfully
        loginPage.login("standard_user", "secret_sauce");

        // Step 2: Wait until inventory page is ready
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        Assert.assertTrue(inventoryPage.isInventoryPageLoaded(), "❌ Inventory page did not load!");
        System.out.println("✅ Inventory page loaded successfully.");

        // Step 3: Add multiple items
        cartPage.addProducts(2);

        // Step 4: Verify cart count
        int actualCount = cartPage.getCartItemCount();
        Assert.assertEquals(actualCount, 2, "❌ Cart item count mismatch!");
        System.out.println("✅ Added products verified in cart successfully.");
    }
}
