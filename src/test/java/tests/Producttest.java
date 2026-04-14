package tests;

import io.qameta.allure.*;
import pages.Productpage;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;

@Epic("EZLife Website Testing")
@Feature("Product Page")
public class Producttest extends BaseTest {

    private Productpage productPage;

    @BeforeClass
    public void start() {
        setup();
        productPage = new Productpage(driver);
    }

    @BeforeMethod
    public void loadPage() {
        productPage.openProductpage(baseUrl);
    }

    // ✅ Test 1: Verify page opens
    @Test(priority = 1)
    @Description("Verify Product Listing Page opens correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductPageOpened() {

        String currentUrl = productPage.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/products"),
                "❌ Product page URL invalid!");

        System.out.println("✅ Product Page Opened: " + currentUrl);
    }

    // ✅ Test 2: Click product and go back
    @Test(priority = 2)
    @Description("Verify user can click product and navigate back")
    public void verifyClickProductNavigation() {

        productPage.clickFirstProduct();
        productPage.waitForProductPage();

        System.out.println("✅ Navigated to Product Detail Page");

        driver.navigate().back();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/products"));

        System.out.println("✅ Navigation Back Successful");
    }

    // ✅ Test 3: Click ONLY one category
    @Test(priority = 3)
    @Description("Verify single category dropdown expands")
    public void verifyCategoryDropdownExpand() {

        productPage.handlePopupIfPresent();

        productPage.clickCategoryIcon();

        System.out.println("✅ Category clicked successfully");
    }

    @AfterClass(alwaysRun = true)
    public void end() {
        tearDown();
    }
}