package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

public class Productpage {

    private WebDriver driver;
    private WebDriverWait wait;

    // 🔹 Constructor
    public Productpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 🔹 Open Product Page
    @Step("Open Product Listing Page")
    public void openProductpage(String baseUrl) {
        driver.get(baseUrl + "/products?category=Health+and+Wellness&categorySlug=health-and-wellness");
    }

    // 🔹 Get Current URL (NEW ✅)
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // 🔹 Validate Page Opened
    public boolean isProductPageDisplayed() {
        return getCurrentUrl().contains("products");
    }

    // 🔹 Handle Popup
    @Step("Handle popup if present")
    public void handlePopupIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            By popupClose = By.xpath("//button[@aria-label='Close']");
            shortWait.until(ExpectedConditions.elementToBeClickable(popupClose)).click();

            System.out.println("✅ Popup closed");

        } catch (Exception e) {
            System.out.println("⚠️ Popup not present");
        }
    }

    // 🔹 Click Product
    @Step("Click on first product")
    public void clickFirstProduct() {

        handlePopupIfPresent();

        By firstProduct = By.xpath("//img[@alt='Safety Blood Lancets PA2']");

        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
    }

    // 🔹 Wait for Product Detail Page
    @Step("Wait for product detail page to load")
    public void waitForProductPage() {

        wait.until(ExpectedConditions.urlContains("products"));

        By productTitle = By.xpath("//h1");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
    }
}