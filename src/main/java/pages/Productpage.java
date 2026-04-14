package pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

public class Productpage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ✅ Only FIRST category icon (no list needed anymore)
    private By categoryIcon = By.xpath(
        "((//*[local-name()='svg' and contains(@class,'transition-transform')])[1]"
    );

    public Productpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Open Product Listing Page")
    public void openProductpage(String baseUrl) {
        driver.get(baseUrl + "/products");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Handle popup if present")
    public void handlePopupIfPresent() {
        try {
            By popupClose = By.xpath("//button[@aria-label='Close']");
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            shortWait.until(ExpectedConditions.elementToBeClickable(popupClose)).click();
        } catch (Exception ignored) {}
    }

    @Step("Click on first product")
    public void clickFirstProduct() {

        handlePopupIfPresent();

        By firstProduct = By.xpath("(//img[@alt])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
    }

    @Step("Wait for product detail page")
    public void waitForProductPage() {
        wait.until(ExpectedConditions.urlContains("products"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
    }

    // ✅ ONLY ONE CATEGORY CLICK (final version)
    @Step("Click first category dropdown icon")
    public void clickCategoryIcon() {

        WebElement icon = wait.until(
            ExpectedConditions.presenceOfElementLocated(categoryIcon));

        wait.until(ExpectedConditions.elementToBeClickable(icon)).click();
    }
}