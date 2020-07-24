package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class BaseDriver {
    protected WebDriver driver;
    protected Actions builder;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    protected void setup(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "E:\\projects\\Selenium\\driver\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "E:\\projects\\Selenium\\driver\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        builder = new Actions(driver);
        wait = new WebDriverWait(driver, 10);
        js = (JavascriptExecutor) driver;
    }

    @AfterClass(alwaysRun = true)
    protected void closeDriver() {
//        driver.quit();
    }
}
