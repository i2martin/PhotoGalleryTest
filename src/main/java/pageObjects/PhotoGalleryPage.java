package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PhotoGalleryPage {

    WebDriver driver;

    @FindBy(xpath = "//button[text()='Create Album']")
    public WebElement createAlbumBtn;

    @FindBy(css = "h3")
    public WebElement createAlbumTitle;

    @FindBy(id = "albumName")
    public WebElement albumName;

    @FindBy(id = "albumDescription")
    public WebElement albumDescription;

    @FindBy(css = "div.alert")
    public WebElement albumNameError;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveAlbumBtn;

    @FindBy(css = "div.placeholder")
    public WebElement uploadImageLink;

    @FindBy(id = "photoInput")
    public WebElement uploadImage;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement uploadImageBtn;

    @FindBy(css = "p.alert")
    public WebElement uploadImageAlert;

    @FindBy(id = "photoName")
    public WebElement photoName;

    @FindBy(css = "p.alert")
    public WebElement albumCreationAlert;

    @FindBys(@FindBy(css = "div.thumbnail__img"))
    public List<WebElement> albumCoverImageContainer;

    @FindBys(@FindBy(css = "span strong"))
    public List<WebElement> albumNameTitles;

    @FindBy(xpath = "//p[@class='type--sml spc--bottom--med spc--top--tny']/span[2]")
    public WebElement albumNameDescription;

    @FindBy(css = "div.push button")
    public WebElement uploadPhotoToAlbumBtn;

    @FindBy(xpath = "//button[text()='Delete']")
    public WebElement deleteBtn;

     @FindBy(css = "div.modal button.btn--warning")
    public WebElement popUpDeleteBtn;

    @FindBy(css = "div h3")
    public WebElement albumTitle;

    @FindBy(css = "div div.thumbnail__img")
    public WebElement albumImage;

    @FindBy(xpath = "//button[text()='Go back to albums']")
    public WebElement backToAlbumsBtn;

    @FindBys(@FindBy(css = "div.thumbnail__delete button"))
    public List<WebElement> deleteAlbumBtns;

    public PhotoGalleryPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }



  public WebElement getAlbumByAlbumName(String albumName)
  {
       for (int i = 0; i < albumNameTitles.size(); i++)
        {
            if (albumNameTitles.get(i).getText().equals(albumName))
            {
                return albumCoverImageContainer.get(i);
            }
        }
      return null;
  }

  public WebElement getDeleteButtonByAlbumName(String albumName)
  {
        for (int i = 0; i < albumNameTitles.size(); i++) {
            if (albumNameTitles.get(i).getText().equals(albumName)) {
                return deleteAlbumBtns.get(i);
            }
        }
    return null;
}

}
