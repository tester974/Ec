package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    public String baseUrl = "https://www.ezlifehealthcare.com";

    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    // 🔥 reusable navigation method
    public void navigateTo(String endpoint) {
        driver.get(baseUrl + endpoint);
    }

    public void tearDown() {
        driver.quit();
    }
}