import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class _01_OpenCartWithoutLoginTest extends OpenCartDriver {

    @Test(dataProvider = "searchTermDataProvider")
    void searchTestCase(String searchTerm){
        System.out.println("initail searchterm: "+searchTerm);
        // type iphone in search bar
        methods.clearAndSendKeys(By.cssSelector("#search > input"), searchTerm);
        page.searchButton.click();
        // validate that result have iphone in their product title
        methods.verifyAllContainsText(page.products, searchTerm);
    }

    @DataProvider(name = "searchTermDataProvider")
    public Object[][] data2() {
        return new Object[][]{
                {"iphone"},
                {"MacBook"},
                {"apple cinema"},
                {"Canon"}
        };
    }

    // task 5
    @Test
    public void contactUsTest() {
        // go to contact us page
        page.contactUsLink.click();
        wait.until(ExpectedConditions.titleIs("Contact Us"));
        // fill in form click submit
        page.nameInput.sendKeys("AYSEGUL");
        page.emailInput.sendKeys("aa@gmail.com");
        page.contactUsTextArea.sendKeys("blabla bla blabla");
        page.submitButton.click();
        // verify "Your enquiry has been successfully sent to the store owner!"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content>p")));
        Assert.assertEquals("Your enquiry has been successfully sent to the store owner!", page.contactUsResult.getText());
    }
}
