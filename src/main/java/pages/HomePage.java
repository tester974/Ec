package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    // 🔹 Locators
    private By menuButtons = By.xpath("//button[contains(@class,'flex') and contains(@class,'cursor-pointer')]");
    private By popupCloseBtn = By.xpath("//button[@aria-label='Close']");
    private By facebookIcon = By.xpath("//a[contains(@href, 'facebook.com')]");

    // 🔹 Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    // 🔹 Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // 🔥 Popup handle
    public void handleInitialPopup() {
        try {
            List<WebElement> popup = driver.findElements(popupCloseBtn);

            if (!popup.isEmpty()) {
                popup.get(0).click();
                System.out.println("Popup closed.");
            }
        } catch (Exception e) {
            System.out.println("No popup found.");
        }
    }

    // 🔹 Get menus
    public List<WebElement> getNavMenus() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(menuButtons));
    }

    // 🔹 Hover
    public void hoverOnMenu(WebElement element) {
        actions.moveToElement(element).perform();
    }

    // 🔹 Dropdown check
    public boolean isDropdownVisible(String menuName) {
        try {
            By dropdownXPath = By.xpath("//button[contains(.,'" + menuName + "')]/following-sibling::div");
            WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownXPath));
            return dropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // 🔹 Click menu
    public void clickMenu(String menuName) {
        By menuXpath = By.xpath("//button[normalize-space(text())='" + menuName + "']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(menuXpath));

        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    // 🔥 Scroll until Facebook icon visible
    public boolean scrollUntilFacebookIconVisible() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        int maxScrolls = 10;

        for (int i = 0; i < maxScrolls; i++) {

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(facebookIcon));
                System.out.println("Facebook icon visible");
                return true;
            } catch (Exception e) {
                js.executeScript("window.scrollBy(0,500)");
            }
        }

        return false;
    }
}