package pages;
 
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
 
import base.BaseTest;
 
import java.time.Duration;
 
public class GiftCardPage extends BaseTest{
 
    private WebDriver driver;
    private WebDriverWait wait;
 
    // Locators
    @FindBy(xpath = "//span[normalize-space()='Gift Cards']")
    private WebElement giftCardsLink;
 
    @FindBy(xpath = "//span[text()='E-Mail']")
    private WebElement emailDeliveryOption;
 
    @FindBy(xpath = "//div[@class='calc_wrap']/span[3]")
    private WebElement countryCodeSelector;
 
    @FindBy(xpath = "//span[@class='slider round']")
    private WebElement toggleSwitch;
 
    @FindBy(name = "senderName")
    private WebElement senderName;
 
    @FindBy(name = "senderMobileNo")
    private WebElement senderMobileNo;
 
    @FindBy(name = "senderEmailId")
    private WebElement senderEmailId;
 
    @FindBy(name = "Recipient 1")
    private WebElement recipient1;
 
    @FindBy(name = "Recipient 2")
    private WebElement recipient2;
 
    @FindBy(name = "Recipient 3")
    private WebElement recipient3;
 
    @FindBy(name = "name")
    private WebElement recipientName;
 
    @FindBy(name = "mobileNo")
    private WebElement recipientMobileNo;
 
    @FindBy(name = "emailId")
    private WebElement recipientEmailId;
 
    @FindBy(xpath = "//textarea[@placeholder='COMPOSE A MESSAGE (0/250)']")
    private WebElement composeMessage;
 
    @FindBy(xpath = "//button[contains(@class,'prime__btn')]")
    private WebElement submitButton;
 
    @FindBy(xpath = "//div[contains(@class, 'error')]")
    private WebElement errorMessage;
 
    public GiftCardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
 
    private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
    }
 
    private void safeClick(WebElement element) {
        try {
            scrollToElement(element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
 
    public void closeLoginPopupIfPresent() {
        try {
            WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-cy='closeModal']")));
            closePopup.click();
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }
 
    public void navigateToGiftCards() {
        safeClick(giftCardsLink);
    }
 
    public void openBestWishesGiftCardInNewTab() {
        String bestWishesUrl = "https://www.makemytrip.com/gift-cards/details/?gcid=41&productId=44";
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", bestWishesUrl);
 
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
 
        wait.until(ExpectedConditions.urlContains("gcid=41&productId=44"));
    }
 
    public void selectEmailDelivery() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(emailDeliveryOption));
        safeClick(emailDeliveryOption);
    }
 
 
    public void clickCountryCodeSelector() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
 
        for (int i = 0; i < 2; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(countryCodeSelector));
            safeClick(countryCodeSelector);
        try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
 
 
public void toggleSwitch() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(toggleSwitch));
    safeClick(toggleSwitch);
}
 
 
    public void fillSenderDetails(String name, String mobile, String email) {
        scrollToElement(senderName);
        senderName.clear();
        senderName.sendKeys(name);
        senderMobileNo.clear();
        senderMobileNo.sendKeys(mobile);
        senderEmailId.clear();
        senderEmailId.sendKeys(email);
 
        if (!isValidEmailFormat(email)) {
            System.err.println("\u001B[31mInvalid Email Format: " + email + " (Only abc@gmail.com or abc123@gmail.com allowed)\u001B[0m");
        } else {
            System.out.println("✅ Valid Email Format: " + email);
        }
    }
 
    public boolean isValidEmailFormat(String email) {
        return email.matches("^(abc|abc123)@gmail\\.com$");
    }
 
    public void fillRecipientDetails(int recipientNumber, String name, String mobile, String email) {
        WebElement recipientField = null;
        switch (recipientNumber) {
            case 1:
                recipientField = recipient1;
                break;
            case 2:
                recipientField = recipient2;
                break;
            case 3:
                recipientField = recipient3;
                break;
            default:
                throw new IllegalArgumentException("Invalid recipient number: " + recipientNumber);
        }
 
        scrollToElement(recipientField);
        wait.until(ExpectedConditions.visibilityOf(recipientField));
        recipientField.clear();
        recipientField.sendKeys(name + " " + mobile + " " + email);
    }
 
    public void fillRecipientForm(String name, String mobile, String email, String message) {
        scrollToElement(recipientName);
        recipientName.clear();
        recipientName.sendKeys(name);
        recipientMobileNo.clear();
        recipientMobileNo.sendKeys(mobile);
        recipientEmailId.clear();
        recipientEmailId.sendKeys(email);
        composeMessage.clear();
        composeMessage.sendKeys(message);
    }
 
    public void fillSenderAndRecipientDetails(String senderNameVal, String senderMobileVal, String senderEmailVal,
                                              String recipientNameVal, String recipientMobileVal, String recipientEmailVal,
                                              String message) {
        scrollDown();
        fillSenderDetails(senderNameVal, senderMobileVal, senderEmailVal);
        fillRecipientForm(recipientNameVal, recipientMobileVal, recipientEmailVal, message);
    }
 
    public void clickSubmit() {
        safeClick(submitButton);
    }
 
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }
 
    public void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
    }
 
    public void scrollUp() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -500);");
    }
 
    public boolean isSenderFormVisible() {
    try {
	  return senderEmailId.isDisplayed();
	   } catch (NoSuchElementException | TimeoutException e) {
	    return false;
	   }
    }
}