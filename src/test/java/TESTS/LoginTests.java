package TESTS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pageObjects.LoginPage;

import java.time.Duration;
import java.util.List;

public class LoginTests extends BaseTest {
    LoginPage loginPage;
    WebDriverWait wait;

    @Test
    public void testEmptyCredentials() {
        driver.get(loginURL);
        //complete login process and verify that correct error message is displayed
        loginPage.logIn("", "");
        Assert.assertEquals(loginPage.loginError.getText(), "Username and password required.", "Error message with empty credentials isn't displayed or shows incorrect error message.");
    }

    @Test
    public void testIncorrectCredentials() {
        driver.get(loginURL);
        //complete login process and verify that correct error message is displayed
        loginPage.logIn("43ktwlf0", "93240wa");
        Assert.assertEquals(loginPage.loginError.getText(), "Invalid email, username or password", "Error message with incorrect credentials isn't displayed or shows incorrect error message.");
    }

    @Test
    public void testLogIn() {
        driver.get(loginURL);
        //complete login process and verify that user is successfully logged in
        loginPage.logIn("i2martin", "1.Jelena");
        //try to access one of the elements on the website to verify successful login
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("baasic-album-list div button"))));
        Assert.assertEquals(driver.findElement(By.cssSelector("baasic-album-list div button")).isDisplayed(), true, "Elements missing on the login page or user wasn't able to log in.");
    }

    @Test(dependsOnMethods = {"testLogIn"})
    public void testLogOut() {
        driver.get(homeURL);
        //loginPage.logIn("i2martin", "1.Jelena");
        wait.until(ExpectedConditions.visibilityOf(loginPage.menuContainer));
        Actions actions = new Actions(driver);
        actions.moveToElement(loginPage.menuContainer).build().perform();
        wait.until(ExpectedConditions.visibilityOf(loginPage.menu));
        loginPage.menu.click();
        wait.until(ExpectedConditions.visibilityOf(loginPage.logOutLink));
        loginPage.logOutLink.click();
        try{
            //wait for one of the elements to appear on login page and verify that it's on proper URL
            wait.until(ExpectedConditions.visibilityOf(loginPage.usernameInput));
            Assert.assertEquals(driver.getCurrentUrl(), loginURL , "Logging out is not working or user's redirected to wrong place." );
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("User is redirected to wrong URL.");
        }

    }

    @Test
    public void testSocialLoginLinks() {
        SoftAssert softAssert = new SoftAssert();
        driver.get(loginURL);
        List<WebElement> socialLoginLinks = loginPage.socialLoginLinks;
        for (int i = 0; i < socialLoginLinks.size(); i++) {
            WebElement link = wait.until(ExpectedConditions.visibilityOf(socialLoginLinks.get(i)));
            link.click();
            //verify that each social login link displays proper error message since they're not implemented
            softAssert.assertEquals(loginPage.socialLoginError.getText(), "undefined: Social login configuration not found.", "Social login link's not working or missing configuration.");
            driver.navigate().refresh();
            //links need to be refetched because the page is being refreshed --> stale element
            socialLoginLinks = loginPage.socialLoginLinks;
        }
        softAssert.assertAll();
    }

    @Test()
    public void testPasswordRecoveryLink(){
        driver.get(loginURL);
        loginPage.recoverPasswordLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), passwordRecoveryURL, "Link for password recovery isn't working.");
    }

    @Test()
    public void testPasswordRecoveryIncorrectInput(){
        driver.get(loginURL);
        loginPage.recoverPasswordLink.click();
        SoftAssert softAssert = new SoftAssert();
        //verify that recovery isn't possible with empty email field
        loginPage.recoverPasswordInput.sendKeys("");
        softAssert.assertEquals(loginPage.recoverPasswordBtn.getAttribute("disabled").isEmpty(), false, "Recover password button seems to be enabled with empty email field.");

        //verify that recovery isn't possible with incorrect email field
        loginPage.recoverPasswordInput.sendKeys("892w");
        softAssert.assertEquals(loginPage.recoverPasswordBtn.getAttribute("disabled").isEmpty(), false, "Recover password button seems to be enabled with empty email field.");

        softAssert.assertAll();
    }

    @Test()
    public void testPasswordRecoveryCorrectInput(){
        SoftAssert softAssert = new SoftAssert();
        driver.get(loginURL);
        loginPage.recoverPasswordLink.click();
        //verify that unknown user gets the error message
        loginPage.recoverPasswordInput.sendKeys("ivan@ivan.com");
        loginPage.recoverPasswordBtn.click();
        softAssert.assertEquals(loginPage.recoverPasswordMessage.getText(), "Unknown user.", "Link for password recovery isn't working.");

        //refresh the page so that error message gets cleared
        driver.navigate().refresh();

        //verify that already registered user gets proper message
        loginPage.recoverPasswordInput.sendKeys("ivan.martinovic1990@gmail.com");
        loginPage.recoverPasswordBtn.click();
        softAssert.assertEquals(loginPage.recoverPasswordMessage.getText(), "Please check your email in order to finish password recovery process.", "Link for password recovery isn't working.");

        softAssert.assertAll();
    }

    @BeforeTest(alwaysRun = true)
    @Parameters("browser")
    public void initialize(String browser) {
        driver = initializeDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        loginPage = new LoginPage(driver);

    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
