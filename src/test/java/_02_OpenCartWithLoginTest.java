import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Random;

public class _02_OpenCartWithLoginTest extends OpenCartDriver {

    private String email;
    private String password;
    private boolean useRandom;
    private String editFirstName;
    private String randomlySelectedProductNameToAddToWishlist;

    // task1
    // make this credentials come from xml
    // if not supplied from xml, make sure to use default value
    // add additional boolean parameter, to use random generator or not, by default not
    @Parameters({"email", "password", "useRandom"})
    @BeforeClass(alwaysRun = true)
    void initCredentials(
            @Optional("test12345asda@test.com") String email,
            @Optional("someRassword123") String password,
            @Optional("false") String useRandom
    ) {
        if(useRandom.equals("true")){
            this.useRandom = true;
            this.email =  methods.randomWord(10) + "@test.com";
            this.password = methods.randomPassword(10);
        } else {
            this.useRandom = false;
            this.email = email;
            this.password = password;
        }
    }

    @Test(groups = "smoke", dependsOnMethods = "createAccountTest")
    void loginTestCase() {
        driver.navigate().to("https://opencart.abstracta.us/index.php?route=account/login");
        // test12345asd@test.com
        // qwerty12345

        //input the email
        page.emailInput.sendKeys(email);
        //input the password
        page.passwordInput.sendKeys(password);
        //click on login
        page.loginButton.click();
        //verify that you are logged in
        String title = driver.getTitle();
        Assert.assertEquals(title, "My Account");
    }

    @Test(alwaysRun = true)
    void createAccountTest() {
        if(this.useRandom) {
            driver.navigate().to("https://opencart.abstracta.us/index.php?route=account/register");


            page.firstNameInput.sendKeys("Name");
            page.lastNameInput.sendKeys("Last Name");
            page.emailInput.sendKeys(email);
            page.phoneInput.sendKeys("12345679812");
            page.addressInput.sendKeys("Last Name");
            page.cityInput.sendKeys("Last Name");
            page.zipCodeInput.sendKeys("123123");
            Select country = new Select(page.countryInput);
            country.selectByIndex(3);
            WebDriverWait wait = new WebDriverWait(driver, 12);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fa-spin")));
            Select region = new Select(page.zoneInput);
            region.selectByIndex(2);
            page.passwordInput.sendKeys(password);
            page.passwordConfirmInput.sendKeys(password);
            page.agreeCheckbox.click();
            page.continueButton.click();
            //verify that you are logged in
            String title = driver.getTitle();
            Assert.assertEquals(title, "Your Account Has Been Created!");

            page.logOutLink.click();
        } else {
            // cannot throw skip exception here
            System.out.println("Using existing account");
        }
    }

    @Test(dependsOnMethods = {"loginTestCase"}, groups = {"functional"})
    void subscribeTestCase() {
        driver.findElement(By.cssSelector("a[href*=newsletter]")).click();
        driver.findElement(By.cssSelector("input[name=\"newsletter\"][value=\"1\"]")).click();
        driver.findElement(By.cssSelector("input[value=\"Continue\"]")).click();

        methods.verifyOneContainsText(page.successAlert, "Success");
    }

    @Test(dependsOnMethods = {"loginTestCase"}, groups = {"functional"})
    void unSubscribeTestCase() {
        driver.findElement(By.cssSelector("a[href*=newsletter]")).click();
        driver.findElement(By.cssSelector("input[name=\"newsletter\"][value=\"0\"]")).click();
        driver.findElement(By.cssSelector("input[value=\"Continue\"]")).click();

        methods.verifyOneContainsText(page.successAlert, "Success");
    }

    @Test(dependsOnMethods = {"loginTestCase"}, groups = {"functional"})
    void editAccountTestCase() {
        driver.findElement(By.cssSelector("a[href*=edit]")).click();

        methods.clearAndSendKeys(By.id("input-firstname"), "new first name");
        methods.clearAndSendKeys(By.id("input-lastname"), "new last name");

        driver.findElement(By.cssSelector("input[value=\"Continue\"]")).click();

        methods.verifyOneContainsText(page.successAlert, "Success");
    }

    // task2
    // click on "Address book"
    // click on "New Address"
    // fill in the form
    // click on "Continue"
    // validate that new address was created
    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase"})
    void createAddressTest(){
        driver.navigate().to("https://opencart.abstracta.us/index.php?route=account/account");
        driver.findElement(By.cssSelector("#column-right a[href*='account/address']")).click();
        wait.until(ExpectedConditions.titleIs("Address Book"));
        page.primaryButton.click();
        String firstName = "Ayse";
        page.firstNameInput.sendKeys(firstName);
        page.lastNameInput.sendKeys("Ayse");
        page.addressInput.sendKeys("1102 Slade");
        page.cityInput.sendKeys("Columbus");
        page.zipCodeInput.sendKeys("42356");
        Select select= new Select(page.countryInput);
        select.selectByIndex(2); // TODO: use random
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fa-spin")));
        Select select1= new Select(page.zoneInput);
        select1.selectByIndex(2);  // TODO: use random
        page.primaryButton.click();
        Assert.assertEquals("Address Book Entries",driver.findElement(By.cssSelector("#content>h2")).getText());
        List<WebElement> elements = driver.findElements(By.cssSelector(".text-left"));
        methods.verifyAtLeastOneContainsText(elements, firstName);
    }

    // task3
    // edit the address
    // verify edition, by checking success message and verifyAtLeastOneContainsText()
    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase", "createAddressTest"})
    void editAddressTest(){
        driver.findElement(By.cssSelector("#column-right a[href*='account/address']")).click();
        wait.until(ExpectedConditions.titleIs("Address Book"));

        // .table-hover .btn-info gives a list
        List<WebElement> editButtons = driver.findElements(By.cssSelector(".table-hover .btn-info"));
        // select last element to edit,
        editButtons.get(editButtons.size() - 1).click();

        editFirstName = methods.randomWord(10);
        methods.clearAndSendKeys(By.id("input-firstname"), editFirstName);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".fa-spin")));

        page.primaryButton.click();

        methods.verifyOneContainsText(page.successAlert, "success");

        List<WebElement> elements = driver.findElements(By.cssSelector(".text-left"));
        methods.verifyAtLeastOneContainsText(elements, editFirstName);


    }

    // task4
    // delete the created address
    // verify it's deleted
    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase", "createAddressTest", "editAddressTest"})
    void deleteAddressTest(){
        driver.findElement(By.cssSelector("#column-right a[href*='account/address']")).click();
        wait.until(ExpectedConditions.titleIs("Address Book"));

        // .table-hover .btn-info gives a list
        List<WebElement> editButtons = driver.findElements(By.cssSelector(".table-hover .btn-danger"));
        // delete last element
        editButtons.get(editButtons.size() - 1).click();

        methods.verifyOneContainsText(page.successAlert, "delete");

        List<WebElement> elements = driver.findElements(By.cssSelector(".text-left"));
        methods.verifyNoneContainsTextAndNotEmpty(elements, editFirstName);
    }

    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase"})
    void addToCartTest(){
        driver.navigate().to("http://opencart.abstracta.us/index.php?route=common/home");
        wait.until(ExpectedConditions.titleIs("Your Store"));

        page.macBookAddToCart.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-success")));
        methods.verifyOneContainsText(page.successAlert, "success");

        driver.navigate().to("http://opencart.abstracta.us/index.php?route=checkout/cart");
        wait.until(ExpectedConditions.titleIs("Shopping Cart"));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table-responsive")));
        SoftAssert softAssert = new SoftAssert();

        String searchTerm = "macbook";
        softAssert.assertTrue(page.productThumbnail.getAttribute("src").contains(searchTerm), "Thumbnail needs to contain "+ searchTerm);

        softAssert.assertTrue(page.productProperties.get(1).getText().toLowerCase().contains(searchTerm), "Product Name needs to contain "+ searchTerm);
        String productModel = "Product 16".toLowerCase();
        softAssert.assertTrue(page.productProperties.get(2).getText().toLowerCase().contains(productModel), "Product model needs to contain "+ productModel);

        String productPrice = "500";
        softAssert.assertTrue(page.productPrices.get(0).getText().contains(productPrice), "Product price needs to contain "+ productPrice);

        softAssert.assertAll();

    }

    //day22,task2
    // create a test case for adding to wishlist on random item from homepage
    // add the item to wishlist and verify it's present inside the wishlist page
    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase"})
    void addToWishListTest(){
        driver.navigate().to("http://opencart.abstracta.us/index.php?route=common/home");
        // selecting random product to add to wishlist
        List<WebElement> listOfProducts = driver.findElements(By.className("product-layout"));
        int randomItemIndex = new Random().nextInt(listOfProducts.size());
        WebElement randomlySelectedProduct = listOfProducts.get(randomItemIndex);
        randomlySelectedProductNameToAddToWishlist = randomlySelectedProduct.findElement(By.cssSelector(".caption >h4")).getText();
        randomlySelectedProduct.findElement(By.cssSelector("button[data-original-title=\"Add to Wish List\"]")).click();

        // verifying success message that product was added to wishlist
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-success")));
        methods.verifyOneContainsText(page.successAlert, "success");

        // go to wishlist
        driver.navigate().to("http://opencart.abstracta.us/index.php?route=account/wishlist");
        //driver.findElement(By.cssSelector("#wishlist-total")).click();

        // verify that randomlySelectedProductName is present in the wishlist
        List<WebElement> wishlistProducts = driver.findElements(By.cssSelector("tbody > tr > td.text-left :first-of-type"));
        methods.verifyAtLeastOneContainsText(wishlistProducts, randomlySelectedProductNameToAddToWishlist);
    }

    //day22,task3
    // create a test case for removing item from wishlist
    // add the item to wishlist, delete it and verify it's not present inside the wishlist page
    @Test(dependsOnMethods = {"createAccountTest", "loginTestCase", "addToWishListTest"})
    void deleteWishListTest(){
        driver.navigate().to("http://opencart.abstracta.us/index.php?route=account/wishlist");
        // delete first item from wishlist
        driver.findElement(By.xpath("//a[contains(text(),'"+randomlySelectedProductNameToAddToWishlist+"')]/../..//a[@class='btn btn-danger']")).click();
        // verifying success message that product was added to wishlist
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alert-success")));
        methods.verifyOneContainsText(page.successAlert, "success");

        // verify it's not present inside the wishlist page
        List<WebElement> wishlistProducts = driver.findElements(By.cssSelector("tbody > tr > td.text-left :first-of-type"));
        methods.verifyNoneContainsText(wishlistProducts, randomlySelectedProductNameToAddToWishlist);
    }
}
