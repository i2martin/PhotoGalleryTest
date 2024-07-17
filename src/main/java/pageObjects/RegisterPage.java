package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    WebDriver driver;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(xpath = "//div[text()='Email is required.']")
    public WebElement emailRequiredAlert;

    @FindBy(xpath = "//div[text()='Please enter the correct email address!']")
    public WebElement incorrectEmailAlert;

    @FindBy(id = "userName")
    public WebElement usernameInput;

    @FindBy(xpath = "//div[text()='Username is required.']")
    public WebElement usernameRequiredAlert;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(xpath = "//div[text()='Password is required.']")
    public WebElement passwordRequiredAlert;

    @FindBy(xpath = "//div[text()='Password too short.']")
    public WebElement passwordTooShortAlert;

    @FindBy(xpath = "//div[text()='Passwords do not match.']")
    public WebElement passwordMismatchAlert;

    @FindBy(id = "confirmPassword")
    public WebElement confirmPasswordInput;

    @FindBy(xpath = "//div[text()='Confirm Password is required.']")
    public WebElement confirmPasswordRequiredAlert;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement registerBtn;

    @FindBy(xpath = "//p[text()='Username taken!']")
    public WebElement usernameTakenAlert;

    @FindBy(xpath = "//p[text()='This email is already registered!']")
    public WebElement emailTakenAlert;

    @FindBy(css = "form div p")
    public WebElement registrationSuccessfulAlert;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
  }
}
