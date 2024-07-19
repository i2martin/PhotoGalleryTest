package TESTS;

import UTILITIES.DriverSetup;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class BaseTest {

  public String registerURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/register";
  public String loginURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/login";
  public String homeURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/main";
  public String passwordRecoveryURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/passwordRecovery";
  public String pageMissingURL = "https://demo.baasic.com/angular/starterkit-photo-gallery/**";
  public File uploadFileWrongFormat = new File("src/test/java/RESOURCES/test.PNG");
  public File uploadFileCorrectFormat = new File("src/test/java/RESOURCES/test.jpg");

  DriverSetup ds;
  public WebDriver driver;

  public WebDriver initializeDriver(String browser) {
      switch (browser) {
          case "chrome" -> ds = new DriverSetup(DriverSetup.BROWSER.CHROME);
          case "firefox" -> ds = new DriverSetup(DriverSetup.BROWSER.FIREFOX);
          case "edge" -> ds = new DriverSetup(DriverSetup.BROWSER.EDGE);
          case "safari" -> ds = new DriverSetup(DriverSetup.BROWSER.SAFARI);
      }
    return ds.wd;
  }
}
