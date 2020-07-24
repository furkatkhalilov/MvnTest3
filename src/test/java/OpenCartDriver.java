
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import utils.ReusableMethods;

public class OpenCartDriver extends BaseDriver {

    protected ReusableMethods methods;
    protected OpenCartGeneralPage page;

    @BeforeClass(alwaysRun = true)
    protected void goToWebsite() {
        page = new OpenCartGeneralPage(driver);
        methods = new ReusableMethods(wait, driver, js);
        driver.get("https://opencart.abstracta.us/index.php");
        try {
            driver.findElement(By.id("details-button")).click();
            driver.findElement(By.id("proceed-link")).click();
        } catch (Exception e) {
            // this means there's no "Your connection is not private" page!
        }
    }
}
