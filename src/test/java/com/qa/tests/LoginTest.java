package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.CartPage;
import com.qa.pages.LoginPage;
import com.qa.pages.InventoryPage;
import com.qa.pages.AddToCartPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

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

        // -----------------------------
        // 4️⃣ FILTER & SEARCH TEST (SEPARATE METHOD)
        // -----------------------------
    }
    @Test(priority = 4)
    public void testFilterAndSearch() {

        loginPage = new LoginPage(driver);

        // Login
        loginPage.login("standard_user", "secret_sauce");

        // Sort dropdown
        Select sort = new Select(
                driver.findElement(By.className("product_sort_container"))
        );
        sort.selectByVisibleText("Name (Z to A)");

        // Read product names
        List<WebElement> productNames =
                driver.findElements(By.className("inventory_item_name"));

        for (WebElement name : productNames) {
            System.out.println(name.getText());
            }



        }
    @Test(priority = 5)
    public void verifyRemoveItemsIfMoreThanFive() {

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        AddToCartPage addToCartPage = new AddToCartPage(driver);
        CartPage cartPage = new CartPage(driver);

        // ✅ Step 1: Login
        loginPage.login("standard_user", "secret_sauce");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

        Assert.assertTrue(inventoryPage.isInventoryPageLoaded(),
                "❌ Inventory page not loaded");

        // ✅ Step 2: Add more than 5 items
        addToCartPage.addProducts(6);

        // ✅ Step 3: Open cart
        addToCartPage.openCart();

        // ✅ Step 4: Apply business rule
        cartPage.ensureMaxItems(5);

        // ✅ Step 5: Assertion
        Assert.assertEquals(cartPage.getCartItemCount(), 5,
                "❌ Cart should contain max 5 items only");
    }

}
