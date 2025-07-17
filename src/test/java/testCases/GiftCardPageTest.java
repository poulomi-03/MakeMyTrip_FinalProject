package testCases;
 
import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.*;
 
public class GiftCardPageTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(GiftCardPageTest.class);
    private ExtentTest test;
 
    @Test(priority = 1)
    public void TC_GC_01() throws InterruptedException {
        test = extent.createTest("TC_GC_01: Invalid sender email");
        logger.info("Executing TC_GC_01");
 
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.scrollDown();
        giftCardPage.fillSenderDetails("Sender Name", "9876543210", "invalid-email");
        giftCardPage.clickSubmit();
 
        String error = giftCardPage.getErrorMessage();
        logger.error("Sender Email Error: {}", error);
        test.info("Captured error message: " + error);
 
        Assert.assertEquals(error.trim(), "Please enter a valid Email id.", "Expected sender email error not shown.");
        test.pass("Validation message matched expected error.");
    }
 
    @Test(priority = 2)
    public void TC_GC_02() {
        test = extent.createTest("TC_GC_02: Multiple recipients error");
        logger.info("Executing TC_GC_02");
 
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.toggleSwitch();
        giftCardPage.scrollDown();
 
        giftCardPage.fillRecipientDetails(1, "Recipient1", "1234567890", "r1gmail.com");
        giftCardPage.fillRecipientDetails(2, "Recipient2", "2345678901", "R2#gmail.com");
        giftCardPage.fillRecipientDetails(3, "Abhinav", "9502377742", "abhinav@gm.com");
 
        giftCardPage.clickSubmit();
 
        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid/multiple recipient details.");
        test.pass("Form did not submit with invalid/multiple recipient details.");
    }
 
    @Test(priority = 3)
    public void TC_GC_03() {
        test = extent.createTest("TC_GC_03: Recipient form error");
        logger.info("Executing TC_GC_03");
 
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.scrollDown();
        giftCardPage.fillRecipientForm("Alice", "4567891230", "234QWE122@GMAIL.COM", "Happy Birthday!");
        giftCardPage.clickSubmit();
 
        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
        test.pass("Form did not submit with invalid recipient form.");
    }
}
 
 