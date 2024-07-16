package TESTS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pageObjects.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageTests extends BaseTest{

    HomePage homePage;
    WebDriverWait wait;
    @Test()
    public void testHomePageLoad(){
        SoftAssert softAssert = new SoftAssert();
        //verify browser's on an expected link
        softAssert.assertEquals(driver.getCurrentUrl(), homeURL,"User didn't reach expected url.");

        //verify page title
        softAssert.assertEquals(driver.getTitle(), homePage.homepageTitle, "Homepage title isn't showing properly.");

        //wait for the element to load (animation) and verify central title is present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(homePage.centralTitle));
        softAssert.assertEquals(homePage.centralTitle.getText().isEmpty(), false, "HomePage central title didn't load properly.");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void testNavigationToPhotoGallery() {
        homePage.goTo();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        //verify that photo gallery element appears
        homePage.btnPhotoGallery.click();
        wait.until(ExpectedConditions.visibilityOf(homePage.photoGalleryContainer));
        Assert.assertEquals(homePage.photoGalleryContainer.isDisplayed(), true, "Photo Gallery isn't showing properly");


    }

    @Test(priority = 2)
    public void testNavigationLinks()
    {
        homePage.goTo();
        List<WebElement> menuLinks = new ArrayList<>(Arrays.asList(homePage.homeLink, homePage.registerLink, homePage.loginLink));
        List<String> expectedURLs = new ArrayList<>(Arrays.asList(homeURL, registerURL, loginURL));
        SoftAssert softAssert = new SoftAssert();

        Actions actions = new Actions(driver);

        for (int i = 0; i < menuLinks.size(); i++)
        {
            //go to home page and wait for the menu container to be interactable
            homePage.goTo();
            wait.until(ExpectedConditions.visibilityOf(homePage.menuContainer));
            //perform a hover action over the menu
            actions.moveToElement(homePage.menuContainer).build().perform();
            //open the menu
            homePage.menu.click();
            //wait for the links to appear
            wait.until(ExpectedConditions.visibilityOf(menuLinks.get(i)));
            //click on a menu link and verify it's working
            menuLinks.get(i).click();
            softAssert.assertEquals(driver.getCurrentUrl(), expectedURLs.get(i), "Given menu link isn't working properly." );
        }
        softAssert.assertAll();

    }
    @BeforeTest(alwaysRun = true)
    @Parameters("browser")
    public void initialize(String browser) {
        driver = initializeDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        homePage = new HomePage(driver);
        homePage.goTo();

    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
