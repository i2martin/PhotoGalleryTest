package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
  WebDriver driver;

  @FindBy(xpath = "//input[@placeholder='Enter your email or username']")
  public WebElement usernameInput;

  @FindBy(xpath = "//input[@type='password']")
  public WebElement passwordInput;

  @FindBy(xpath = "//button[@type='submit']")
  public WebElement loginBtn;

  @FindBy(css = "span.alert")
  public WebElement loginError;

  @FindBy(css = "p.alert")
  public WebElement socialLoginError;

  @FindBy(css = "button.btn--social")
  public List<WebElement> socialLoginLinks;

  @FindBy(xpath = "//a[@routerlink='/passwordRecovery']")
  public WebElement recoverPasswordLink;

  @FindBy(id = "email")
  public WebElement recoverPasswordInput;

  @FindBy(xpath = "//button[@type='submit']")
  public WebElement recoverPasswordBtn;

  @FindBy(css = "form div p")
  public WebElement recoverPasswordMessage;

  public LoginPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void logIn(String username, String password)
  {
    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);
    loginBtn.click();
  }
}
