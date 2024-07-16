package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
  WebDriver driver;

  public static String baseUrl = "https://demo.baasic.com/angular/starterkit-photo-gallery/main";
  public String homepageTitle = "baasic-starterkit-angular-blog";

  @FindBy(css = ".menu__title")
  public WebElement menu;

  @FindBy(css = "div.menu")
  public WebElement menuContainer;

  @FindBy(css = ".hero__title")
  public WebElement centralTitle;

  @FindBy(css = "svg.scroll__icon")
  public WebElement btnPhotoGallery;

  @FindBy(css = "baasic-photo-list")
  public WebElement photoGalleryContainer;

  @FindBy(xpath = "//li/span[text()='Home']")
  public WebElement homeLink;

  @FindBy(xpath = "//li/span[text()='Register']")
  public WebElement registerLink;

  @FindBy(xpath = "//li/span[text()='Login']")
  public WebElement loginLink;

  public HomePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void goTo() {
    driver.get(baseUrl);
  }

}
