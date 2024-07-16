package TESTS;

import UTILITIES.DriverSetup;
import org.openqa.selenium.WebDriver;

public class BaseTest {

  public String registerURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/register";
  public String loginURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/login";
  public String homeURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/main";
  public String passwordRecoveryURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/passwordRecovery";
  public String successfulLoginURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/profile/";
  DriverSetup ds;
  public WebDriver driver;

  public WebDriver initializeDriver(String browser) {
    if (browser.equals("chrome"))
      ds = new DriverSetup(DriverSetup.BROWSER.CHROME);
    else if (browser.equals("firefox"))
      ds = new DriverSetup(DriverSetup.BROWSER.FIREFOX);
    else if (browser.equals("edge"))
      ds = new DriverSetup(DriverSetup.BROWSER.EDGE);
    else if (browser.equals("safari"))
      ds = new DriverSetup(DriverSetup.BROWSER.SAFARI);
    return ds.wd;
  }
}
