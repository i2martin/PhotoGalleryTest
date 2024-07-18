package TESTS;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.LoginPage;
import pageObjects.PhotoGalleryPage;

import java.time.Duration;
import java.util.Random;

public class PhotoGalleryTests extends BaseTest{
    PhotoGalleryPage photoGallery;
    LoginPage loginPage;
    WebDriverWait wait;
    WebDriverWait waitImageUpload;
    String albumName;
    String albumDescription = "description";
    String photoName;
    Random random;
    int randomNumber;
    String currentURL;

   @Test
    public void testCreateAlbumButton()
    {
        driver.get(loginURL);
        //log in and wait for the create album button to be visible
        loginPage.logIn("i2martin", "1.Jelena");
        wait.until(ExpectedConditions.visibilityOf(photoGallery.createAlbumBtn));
        //verify that create new album menu is opened
        photoGallery.createAlbumBtn.click();
        Assert.assertTrue(photoGallery.createAlbumTitle.isDisplayed(), "Create new album menu isn't opening.");
    }

    @Test(dependsOnMethods = {"testCreateAlbumButton"})
    public void testIncorrectAlbumName()
    {
        //verify that correct alert message is displayed if album name is blank
        photoGallery.albumName.sendKeys("");
        photoGallery.albumName.sendKeys(Keys.TAB);
        wait.until(ExpectedConditions.visibilityOf(photoGallery.albumNameError));
        Assert.assertEquals(photoGallery.albumNameError.getText().trim(), "Name is required.", "Album name was blank but error message wasn't displayed.");
    }

    @Test(dependsOnMethods = {"testIncorrectAlbumName"})
    public void testCorrectAlbumName()
    {
        randomNumber = random.nextInt(1000, 10000);
        //randomize album name cause tests run in parallel
        albumName += "-album-" + randomNumber;
        driver.navigate().refresh();
        //fill in album name and description with information and save album
        photoGallery.albumName.sendKeys(albumName);
        photoGallery.albumDescription.sendKeys(albumDescription);
        photoGallery.saveAlbumBtn.click();
        //verify the next step is reached by locating alert message stating that cover image is required
        Assert.assertEquals(photoGallery.albumCreationAlert.getText(),"Album is not created until you upload a cover image.", "Cover image required message is missing or create album button isn't working.");
    }

    @Test(dependsOnMethods = {"testCorrectAlbumName"})
    public void testCoverImageUpload()
    {
        SoftAssert softAssert = new SoftAssert();
        //click on the "Upload image" link
        photoGallery.uploadImageLink.click();
        //open the upload image popup and upload an image
        photoGallery.uploadImage.sendKeys(uploadFileWrongFormat.getAbsolutePath());
        Assert.assertEquals(photoGallery.uploadImageAlert.getText(), "Allowed file types are: .jpeg / .jpg", "Wrong file format was used, but error message wasn't displayed");
        photoGallery.uploadImage.sendKeys(uploadFileCorrectFormat.getAbsolutePath());
        //randomize photo names because tests run in parallel
        photoName = "test" + randomNumber;
        photoGallery.photoName.clear();
        photoGallery.photoName.sendKeys(photoName);
        photoGallery.uploadImageBtn.click();

        //wait for up to a minute for image to upload and verify that redirect was successful
        try{
            wait.until(ExpectedConditions.visibilityOf(photoGallery.createAlbumBtn));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail("Uploading an image failed or user has slow internet connection.");
        }
    }

    @Test(dependsOnMethods = {"testCoverImageUpload"})
    public void testSuccessfulAlbumCreation()
    {
        boolean testPassed = false;
        //loop through all albums and check if any one of them contains same album name
        for (WebElement album: photoGallery.albumNameTitles)
        {
            if (album.getText().equals(albumName))
            {
                testPassed = true;
                break;
            }
        }
        Assert.assertEquals(testPassed, true, "There's an error with album creation. Album wasn't created or the album's name doesn't match.");
    }

    @Test(dependsOnMethods = {"testSuccessfulAlbumCreation"})
    public void testAlbumDetails()
    {
       //open an album and verify its description
        WebElement album = photoGallery.getAlbumByAlbumName(albumName);
        album.click();
        Assert.assertEquals(photoGallery.albumNameDescription.getText(), albumDescription, "Description of the album doesn't match the one used when creating an album.");
    }

    @Test(dependsOnMethods = {"testAlbumDetails"})
    public void testUploadPhotoToAlbum()
    {
        SoftAssert softAssert = new SoftAssert();
        //test uploading photo to an album - again with randomized name due to tests being run in parallel
        photoGallery.uploadPhotoToAlbumBtn.click();
        photoGallery.uploadImage.sendKeys(uploadFileCorrectFormat.getAbsolutePath());
        photoName = "test" + random.nextInt(1000,10000);
        photoGallery.photoName.clear();
        photoGallery.photoName.sendKeys(photoName);
        photoGallery.uploadImageBtn.click();
        //verify image was uploaded --> wait for up to a minute to verify URL
        try{
            waitImageUpload.until(ExpectedConditions.visibilityOf(photoGallery.albumTitle));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail("Uploading an image failed or user has slow internet connection.");
        }
        currentURL = driver.getCurrentUrl();
        //check if the title's updated with correct number of photos
        softAssert.assertEquals(photoGallery.albumTitle.getText().equals(albumName + " - 1 photos"), true, "Number of photos in an album wasn't updated correctly");
        photoGallery.albumImage.click();
        //open an image link --> URL should be anything besided page Missing URL
        softAssert.assertFalse(driver.getCurrentUrl() != pageMissingURL, "Image in an album can't be opened - broken link.");
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testUploadPhotoToAlbum"}, alwaysRun = true)
    public void testImageDeletion()
    {
        Actions actions = new Actions(driver);
        SoftAssert softAssert = new SoftAssert();
        driver.get(currentURL);
        //delete an image and verify it's deleted
        actions.moveToElement(photoGallery.albumImage).build().perform();
        photoGallery.deleteBtn.click();
        wait.until(ExpectedConditions.visibilityOf(photoGallery.popUpDeleteBtn));
        photoGallery.popUpDeleteBtn.click();
        softAssert.assertEquals(photoGallery.albumTitle.getText().equals(albumName + " - 0 photos"), true, "Number of photos in an album wasn't updated correctly after image deletion.");
        try{
            softAssert.assertFalse(photoGallery.albumImage.isDisplayed(), "Image seems to be visible after deletion.");
        }
        catch (Exception e)
        {
            //if element is missing it was sucessfully deleted
            softAssert.assertTrue(true);
        }
        softAssert.assertAll();

    }

    @Test(dependsOnMethods = {"testImageDeletion"}, alwaysRun = true)
    public void testDeleteAlbum()
    {
        //return to albums list/profile
        photoGallery.backToAlbumsBtn.click();
        //wait for the album to be visible
        wait.until(ExpectedConditions.visibilityOf(photoGallery.albumNameTitles.get(0)));
        Actions actions = new Actions(driver);
        WebElement album = photoGallery.getAlbumByAlbumName(albumName);
        //hover over album and delete it
        actions.moveToElement(album).build().perform();
        //photoGallery.deleteBtn.click();
        photoGallery.getDeleteButtonByAlbumName(albumName).click();
        wait.until(ExpectedConditions.visibilityOf(photoGallery.popUpDeleteBtn));
        photoGallery.popUpDeleteBtn.click();
        //try to get the album again
        album = photoGallery.getAlbumByAlbumName(albumName);
        Assert.assertTrue(album == null, "Album wasn't deleted even though delete action was performed.");
    }

    @BeforeTest(alwaysRun = true)
    @Parameters("browser")
    public void initialize(String browser) {
        driver = initializeDriver(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitImageUpload = new WebDriverWait(driver, Duration.ofSeconds(60));
        loginPage = new LoginPage(driver);
        photoGallery = new PhotoGalleryPage(driver);
        random = new Random();
        albumName = browser;
    }

    @AfterTest
    public void closeDriver() {
        driver.quit();
    }
}
