package TESTS;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.RegisterPage;

import java.time.Duration;

public class RegisterTests extends BaseTest{
    RegisterPage registerPage;
    WebDriverWait wait;

    @Test
    public void testRegistrationWithIncorrectInfo() {
        driver.get(registerURL);
        registerPage.emailInput.sendKeys("i@g");
        registerPage.usernameInput.sendKeys("t");
        registerPage.passwordInput.sendKeys("1");
        registerPage.confirmPasswordInput.sendKeys("12");
        //Unfocus the element after filling it in
        registerPage.confirmPasswordInput.sendKeys(Keys.TAB);
        //verify that the button is disabled when incorrect information is presented
        Assert.assertEquals(registerPage.registerBtn.isEnabled(), false, "Registration button is enabled even though incorrect information was provided");
    }

    @Test
    public void testIncorrectPasswordInfo()
    {
        driver.get(registerURL);
        registerPage.passwordInput.sendKeys("1");
        registerPage.confirmPasswordInput.sendKeys("12");
        //Unfocus the element after filling it in
        registerPage.confirmPasswordInput.sendKeys(Keys.TAB);
        //verify that the password messages are shown
        Assert.assertEquals(registerPage.passwordTooShortAlert.isDisplayed(), true, "Password too short message wasn't displayed.");
        Assert.assertEquals(registerPage.passwordMismatchAlert.isDisplayed(), true, "Passwords do not match message wasn't displayed");
        //verify that proper password alert messages are shown
        Assert.assertEquals(registerPage.passwordTooShortAlert.getText(), "Password too short.", "Alert message was displayed but with incorrect information.");
        Assert.assertEquals(registerPage.passwordMismatchAlert.getText(), "Passwords do not match.", "Alert message was displayed but with incorrect information.");
    }

    @Test
    public void testIncorrectEmail()
    {
        driver.get(registerURL);
        registerPage.emailInput.sendKeys("email");
        //Unfocus the element after filling it in
        registerPage.emailInput.sendKeys(Keys.TAB);
        //verify that the alert message is displayed
        Assert.assertEquals(registerPage.incorrectEmailAlert.isDisplayed(), true, "Incorrect email alert is not displayed.");
        //verify that the alert message is showing expected text.
        Assert.assertEquals(registerPage.incorrectEmailAlert.getText(), "Please enter the correct email address!", "Incorrect email message doesn't match the expected one.");
    }

    @Test
    public void testRegistrationWithEmptyInfo()
    {
        driver.get(registerURL);
        SoftAssert softAssert = new SoftAssert();

        registerPage.emailInput.sendKeys("");
        //Unfocus the element after filling it in
        registerPage.emailInput.sendKeys(Keys.TAB);
        //verify that alert message is displayed
        softAssert.assertEquals(registerPage.emailRequiredAlert.isDisplayed(), true, "Email alert message is not displayed.");
        //verify that alert message is showing correct information
        softAssert.assertEquals(registerPage.emailRequiredAlert.getText(), "Email is required.", "Email error field is showing incorrect message.");

        registerPage.usernameInput.sendKeys("");
        //Unfocus the element after filling it in
        registerPage.usernameInput.sendKeys(Keys.TAB);
        //verify that alert message is displayed
        softAssert.assertEquals(registerPage.usernameRequiredAlert.isDisplayed(), true, "Username alert message is not displayed.");
        //verify that alert message is showing correct information
        softAssert.assertEquals(registerPage.usernameRequiredAlert.getText(), "Username is required.", "Username error field is showing incorrect message.");

        registerPage.passwordInput.sendKeys("");
        //Unfocus the element after filling it in
        registerPage.passwordInput.sendKeys(Keys.TAB);
        //verify that alert message is displayed
        softAssert.assertEquals(registerPage.passwordRequiredAlert.isDisplayed(), true, "Password alert message is not displayed.");
        //verify that alert message is showing correct information
        softAssert.assertEquals(registerPage.passwordRequiredAlert.getText(), "Password is required.", "Password error field is showing incorrect message.");

        registerPage.confirmPasswordInput.sendKeys("");
        //Unfocus the element after filling it in
        registerPage.confirmPasswordInput.sendKeys(Keys.TAB);
        //verify that alert message is displayed
        softAssert.assertEquals(registerPage.confirmPasswordRequiredAlert.isDisplayed(), true, "Confirm password alert message is not displayed.");
        //verify that alert message is showing correct information
        softAssert.assertEquals(registerPage.confirmPasswordRequiredAlert.getText(), "Confirm Password is required.", "Confirm password error field is showing incorrect message.");
        softAssert.assertAll();
    }

    @Test(priority = 1)
    public void testUserRegistrationWithCorrectInfo()
    {
        driver.get(registerURL);
        driver.get(registerURL);
        registerPage.emailInput.sendKeys("tabloid66@gmail.com");
        registerPage.usernameInput.sendKeys("testrltkwa");
        registerPage.passwordInput.sendKeys("1234567.");
        registerPage.confirmPasswordInput.sendKeys("1234567.");
        registerPage.registerBtn.click();
        try{
            //wait for the registration successful message to appear
            wait.until(ExpectedConditions.visibilityOf(registerPage.registrationSuccessfulAlert));
            //verify that the correct message is displayed
            Assert.assertEquals(registerPage.registrationSuccessfulAlert.getText(), "You have successfully registered, please check your email in order to finish registration process.", "Registration failed. The issue could be caused due to existing email or username.");
        }
        catch (Exception e)
        {
            //fail the test in case registration successful message didn't appear
            Assert.fail("Registration failed with correct information. The issue might be caused due to usage of existing email and/or username");
        }

    }

    @Test
    public void testRegistrationWithExistingUsername()
    {
        driver.get(registerURL);
        registerPage.emailInput.sendKeys("tabloid66@gmail.com");
        registerPage.usernameInput.sendKeys("test");
        registerPage.passwordInput.sendKeys("1234567");
        registerPage.confirmPasswordInput.sendKeys("1234567");
        //Unfocus the element after filling it in
        registerPage.confirmPasswordInput.sendKeys(Keys.TAB);
        registerPage.registerBtn.click();
        //wait for the information element to appear

        try{
            wait.until(ExpectedConditions.visibilityOf(registerPage.usernameTakenAlert));
            Assert.assertEquals(registerPage.usernameTakenAlert.getText(), "Username taken!", "Username taken message isn't showing proper information.");
        }
        catch (Exception e)
        {
            Assert.fail("Username taken alert message isn't showing.");
        }
    }

    @Test
    public void testRegistrationWithExistingEmail()
    {
        driver.get(registerURL);
        registerPage.emailInput.sendKeys("mail@mail.com");
        registerPage.usernameInput.sendKeys("kalf3qfgg");
        registerPage.passwordInput.sendKeys("1234567");
        registerPage.confirmPasswordInput.sendKeys("1234567");
        //Unfocus the element after filling it in
        registerPage.confirmPasswordInput.sendKeys(Keys.TAB);
        registerPage.registerBtn.click();
        //wait for the information element to appear
        try{
            wait.until(ExpectedConditions.visibilityOf(registerPage.emailTakenAlert));
            Assert.assertEquals(registerPage.emailTakenAlert.getText(), "This email is already registered!", "Email taken message isn't showing proper information.");
        }
        catch (Exception e)
        {
            Assert.fail("Email taken alert message isn't showing.");
        }
    }

    @BeforeTest(alwaysRun = true)
    @Parameters("browser")
    public void initialize(String browser) {
        driver = initializeDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        registerPage = new RegisterPage(driver);
    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
