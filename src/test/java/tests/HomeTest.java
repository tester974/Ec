package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;

import java.util.ArrayList;
import java.util.List;

@Epic("EZLife Website Testing")
@Feature("Home Page")
public class HomeTest extends BaseTest {

    HomePage home;

    @BeforeClass
    public void start() {
        setup();

        // Open Home Page
        driver.get(baseUrl);

        home = new HomePage(driver);

        // Handle popup
        home.handleInitialPopup();
    }

    @Test(priority = 1)
    @Description("Verify that the EZLife website loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyWebsiteOpen() {

        String url = home.getCurrentUrl();
        System.out.println("Current URL: " + url);

        Assert.assertTrue(url.contains("ezlifehealthcare"),
                "Website load nahi hui!");
    }

    @Test(priority = 2, dependsOnMethods = "verifyWebsiteOpen")
    @Description("Verify all navigation menus hover and dropdown visibility")
    @Severity(SeverityLevel.NORMAL)
    public void verifyAllMenusHover() throws InterruptedException {

        List<WebElement> menus = home.getNavMenus();
        System.out.println("Total menus found: " + menus.size());

        for (WebElement menu : menus) {

            String menuName = menu.getText().trim();

            if (!menuName.isEmpty()) {

                home.hoverOnMenu(menu);
                System.out.println("Hover: " + menuName);

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

            System.out.println("Clicking: " + name);

            home.clickMenu(name);

            Thread.sleep(1500);

            Assert.assertTrue(driver.getCurrentUrl().contains("ezlife"),
                    "URL failed for " + name);

            driver.navigate().back();
            Thread.sleep(1000);

            home.handleInitialPopup();
        }

        System.out.println("All menus clicked successfully.");
    }

    @AfterClass
    public void end() {
        tearDown();
    }
}