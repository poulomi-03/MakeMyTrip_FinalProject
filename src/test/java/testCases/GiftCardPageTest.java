package testCases;

import base.BaseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
 
public class GiftCardPageTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(GiftCardPageTest.class);
    
    @Test(priority = 3, description = "TestCase 1: Invalid sender email")
    public void TC_GC_01() throws InterruptedException {
        logger.info("Executing TC_GC_01: Invalid sender email test.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.scrollDown();
        giftCardPage.fillSenderDetails("Sender Name", "9876543210", "invalid-email");
        giftCardPage.clickSubmit();

        String error = giftCardPage.getErrorMessage();
        logger.error("Sender Email Error: {}", error);
        
        Assert.assertEquals(error.trim(), "Please enter a valid Email id.", "Expected sender email error not shown.");
        logger.info("TC_GC_01 completed.");
    }

    @Test(priority = 1, description = "TestCase 2: Fill multiple recipients and capture error")
    public void TC_GC_02() {
        logger.info("Executing TC_GC_02: Multiple recipients error test.");
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
        logger.info("TC_GC_02 completed.");
    }

    @Test(priority = 2, description = "TestCase 3: Fill recipient form and capture error")
    public void TC_GC_03() {
        logger.info("Executing TC_GC_03: Recipient form error test.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.scrollDown();
        giftCardPage.fillRecipientForm("Alice", "4567891230", "234QWE122@GMAIL.COM", "Happy Birthday!");
        giftCardPage.clickSubmit();

        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
        logger.info("TC_GC_03 completed.");
    }
}
