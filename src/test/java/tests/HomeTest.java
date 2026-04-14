package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;

import java.util.ArrayList;
import java.util.List;

public class HomeTest extends BaseTest {

    HomePage home;

    @BeforeClass
    public void start() {
        setup();
        driver.get(baseUrl);
        home = new HomePage(driver);
        home.handleInitialPopup();
    }

    @Test(priority = 1)
    @Description("Verify that the EZLife website loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyWebsiteOpen() {

        String url = home.getCurrentUrl();

        // Checking for generic "ezlife" to match other assertions in the suite
        Assert.assertTrue(url.contains("ezlife"),
                "Website did not load or unexpected URL: " + url);
    }

    @Test(priority = 2, dependsOnMethods = "verifyWebsiteOpen")
    @Description("Verify all navigation menus hover and dropdown visibility")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAllMenusHover() throws InterruptedException {

        List<WebElement> menus = home.getNavMenus();

        for (WebElement menu : menus) {

            String menuName = menu.getText().trim();

            if (!menuName.isEmpty()) {

                home.hoverOnMenu(menu);

                boolean isVisible = home.isDropdownVisible(menuName);
                Assert.assertTrue(isVisible, "Dropdown missing for " + menuName);

                Thread.sleep(800);
            }
        }
    }

    @Test(priority = 3, dependsOnMethods = "verifyAllMenusHover")
    @Description("Verify clicking all menus redirects properly")
    @Severity(SeverityLevel.NORMAL)
    public void testAllMenusClick() throws InterruptedException {

        List<WebElement> menuElements = home.getNavMenus();
        List<String> menuNames = new ArrayList<>();

        for (WebElement e : menuElements) {
            if (!e.getText().trim().isEmpty()) {
                menuNames.add(e.getText().trim());
            }
        }

        for (String name : menuNames) {

            home.clickMenu(name);

            Thread.sleep(1500);

            Assert.assertTrue(driver.getCurrentUrl().contains("ezlife"),
                    "URL failed for " + name);

            driver.navigate().back();
            Thread.sleep(1000);

            home.handleInitialPopup();
        }
    }

    @Test(priority = 4)
    @Description("Verify Facebook icon is visible after scrolling to footer")
    @Severity(SeverityLevel.NORMAL)
    public void verifyFacebookIconVisibleAfterScroll() {

        // Ensure we're on base URL
        driver.get(baseUrl);

        // Scroll until Facebook icon visible
        boolean isVisible = home.scrollUntilFacebookIconVisible();

        // Assertion
        Assert.assertTrue(isVisible,
                "Facebook icon is NOT visible after scrolling");

        System.out.println("Test Passed: Facebook icon visible after scrolling");
    }

    @AfterClass
    public void end() {
        tearDown();
    }
}