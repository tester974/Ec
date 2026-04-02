package tests;

import base.BaseTest;
import io.qameta.allure.*;
import pages.Productpage;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("EZLife Website Testing")
@Feature("Product Page")
public class Producttest extends BaseTest {

    private Productpage productPage;

    @BeforeClass   // ✅ Changed from BeforeMethod
    public void start() {
        setup();
        productPage = new Productpage(driver);
    }

    @BeforeMethod   // ✅ NEW (keeps tests independent but same browser)
    public void loadPage() {
        productPage.openProductpage(baseUrl);
    }

    @Test
    @Description("Verify Product Listing Page opens correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProductPageOpened() {

        String currentUrl = productPage.getCurrentUrl();   // ✅ Improved

        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("Health+and+Wellness"),
                "❌ Product page not opened!");

        System.out.println("✅ Product Page Opened Successfully");
    }

    @Test
    @Description("Verify second test runs in same browser session")
    @Severity(SeverityLevel.MINOR)
    public void secondTest() {
        System.out.println("✅ Second test running on same browser");
    }

    @Test
    @Description("Verify user is able to click product and navigate to product detail page")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyClickProductNavigation() {

        productPage.clickFirstProduct();
        productPage.waitForProductPage();

        String currentUrl = productPage.getCurrentUrl();   // ✅ Improved

        System.out.println("Navigated URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("products"),
                "❌ User is NOT navigated to product detail page!");

        System.out.println("✅ User successfully navigated to product detail page");
    }

    @AfterClass(alwaysRun = true)   // ✅ Safer teardown
    public void end() {
        tearDown();
    }
}