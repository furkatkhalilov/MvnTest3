
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OpenCartGeneralPage {
    private WebDriver driver;

    public OpenCartGeneralPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(className = "fa-search")
    public WebElement searchButton;

    @FindBy(css = ".product-thumb h4")
    public List<WebElement> products;

    @FindBy(css = "a[href*=contact]")
    public WebElement contactUsLink;

    @FindBy(css = "textarea[name='enquiry']")
    public WebElement contactUsTextArea;

    @FindBy(css = "#content>p")
    public WebElement contactUsResult;

    @FindBy(id = "input-name")
    public WebElement nameInput;

    @FindBy(id = "input-email")
    public WebElement emailInput;

    @FindBy(id ="input-password" )
    public WebElement passwordInput;

    @FindBy(css ="input[value='Login']" )
    public WebElement loginButton;

    @FindBy(css = "input[value='Submit']")
    public WebElement submitButton;

    @FindBy(id = "input-firstname")
    public WebElement firstNameInput;

    @FindBy(id = "input-lastname")
    public WebElement lastNameInput;

    @FindBy(id = "input-telephone")
    public WebElement phoneInput;

    @FindBy(id = "input-address-1")
    public WebElement addressInput;

    @FindBy(id = "input-city")
    public WebElement cityInput;

    @FindBy(id = "input-country")
    public WebElement countryInput;

    @FindBy(id = "input-zone")
    public WebElement zoneInput;

    @FindBy(id = "input-postcode")
    public WebElement zipCodeInput;

    @FindBy(id = "input-confirm")
    public WebElement passwordConfirmInput;

    @FindBy(css ="[name='agree']" )
    public WebElement agreeCheckbox;

    @FindBy(css ="[value='Continue']" )
    public WebElement continueButton;

    @FindBy(css =".pull-right .btn-primary" )
    public WebElement primaryButton;

    @FindBy(css ="a.list-group-item[href*=logout]" )
    public WebElement logOutLink;

    @FindBy(css = "[onclick=\"cart.add('43');\"]")
    public WebElement macBookAddToCart;

    @FindBy(css = ".alert-success")
    public WebElement successAlert;

    @FindBy(className = "img-thumbnail")
    public WebElement productThumbnail;

    @FindBy(css = "tbody > tr > td.text-left")
    public List<WebElement> productProperties;

    @FindBy(css = "tbody > tr > td.text-right")
    public List<WebElement> productPrices;
}
