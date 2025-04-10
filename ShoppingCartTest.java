package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

import java.util.List;

public class ShoppingCartTest extends BaseTest {

    @Test
    public void testShoppingCartFunctionality() {
        driver.get("https://tutorialsninja.com/demo/index.php?route=common/home");

        // Step 2: Add two MacBooks to the cart
        driver.findElement(By.xpath("//a[text()='Laptops & Notebooks']")).click();
        driver.findElement(By.linkText("Show All Laptops & Notebooks")).click();

        // Add first MacBook
        driver.findElement(By.xpath("//a[text()='MacBook']/ancestor::div[@class='caption']/following-sibling::div//button")).click();

        // Validate success message for first MacBook
        WebElement successMsg1 = driver.findElement(By.cssSelector(".alert-success"));
        Assert.assertTrue(successMsg1.getText().contains("Success"), "First success message not displayed");

        // Add second MacBook Air
        driver.findElement(By.xpath("//a[text()='MacBook Air']/ancestor::div[@class='caption']/following-sibling::div//button")).click();

        // Validate success message for second MacBook
        WebElement successMsg2 = driver.findElement(By.cssSelector(".alert-success"));
        Assert.assertTrue(successMsg2.getText().contains("Success"), "Second success message not displayed");

        // Step 4: Open shopping cart
        driver.findElement(By.cssSelector("#top-links a[title='Shopping Cart']")).click();

        // Step 5: Validate URL navigation
        String expectedURL = "https://tutorialsninja.com/demo/index.php?route=checkout/cart";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "Shopping cart URL mismatch");

        // Step 6: Validate pricing
        List<WebElement> rows = driver.findElements(By.cssSelector(".table-responsive tbody tr"));
        double calculatedTotal = 0;

        for (WebElement row : rows) {
            String unitPriceText = row.findElement(By.cssSelector("td:nth-child(5)")).getText().replace("$", "").trim();
            String totalPriceText = row.findElement(By.cssSelector("td:nth-child(6)")).getText().replace("$", "").trim();

            double unitPrice = Double.parseDouble(unitPriceText);
            double totalPrice = Double.parseDouble(totalPriceText);
            int quantity = Integer.parseInt(row.findElement(By.cssSelector("input")).getAttribute("value"));

            double expectedTotal = unitPrice * quantity;
            calculatedTotal += expectedTotal;

            Assert.assertEquals(totalPrice, expectedTotal, "Price calculation mismatch for item.");
        }

        System.out.println("Total cart price validated: $" + calculatedTotal);
    }
}
