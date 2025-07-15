package testCases;
 
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.GiftCardPage;
 
public class GiftCardPageTest extends BaseTest {
    private GiftCardPage giftCardPage;
 
    @BeforeClass
    public void initGiftCardPage() {
        giftCardPage = new GiftCardPage(driver);
    }
 
    @BeforeMethod(alwaysRun = true)
    public void navigateToGiftCardPage() {
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab(); // Opens the specific card in a new tab
    }
 
    @Test(priority = 3, description = "TestCase 1: Invalid sender email")
    public void TC_GC_01() {
        //giftCardPage.scrollDown();
        //giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.scrollDown();
        giftCardPage.fillSenderDetails("Sender Name", "9876543210", "invalid-email");
        giftCardPage.clickSubmit();
 
        String error = giftCardPage.getErrorMessage();
        System.err.println("\u001B[31mSender Email Error: " + error + "\u001B[0m");
 
        Assert.assertEquals(error.trim(), "SENDER'S E-MAIL ID", "Expected sender email error not shown.");
    }
 
 
    @Test(priority = 1, description = "TestCase 2: Fill multiple recipients and capture error")
    public void TC_GC_02() {
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.toggleSwitch();
        giftCardPage.scrollDown();
 
        giftCardPage.fillRecipientDetails(1, "Recipient1", "1234567890", "r1gmail.com");
        giftCardPage.fillRecipientDetails(2, "Recipient2", "2345678901", "R2#gmail.com");
        giftCardPage.fillRecipientDetails(3, "abhinav", "9502377742", "abhinav@gm.com");
 
        giftCardPage.clickSubmit();
 
        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        if (formStillVisible) {
            System.err.println("\u001B[31mForm submission blocked due to multiple recipient error.\u001B[0m");
        } else {
            System.out.println("✅ Form submitted successfully.");
        }
 
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid/multiple recipient details.");
    }
 
    @Test(priority = 2, description = "TestCase 3: Fill recipient form and capture error")
    public void TC_GC_03() {
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.scrollDown();
        giftCardPage.fillRecipientForm("Alice", "4567891230", "234QWE122@GMAIL.COM", "Happy Birthday!");
        giftCardPage.clickSubmit();
 
        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        if (formStillVisible) {
            System.err.println("\u001B[31mForm submission blocked due to recipient form invalid email error.\u001B[0m");
        } else {
            System.out.println("✅ Form submitted successfully.");
        }
 
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
    }
 
    @AfterClass
    public void tearDownTest() {
        tearDown();
    }
}
 