package testCases;

import base.BaseTest;
import utils.DataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
 
public class GiftCardPageTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(GiftCardPageTest.class);
    
    @Test(dataProvider = "GiftCardTest1", dataProviderClass = DataProviders.class, priority = 3, description = "TestCase 1: Invalid sender email")
    public void TC_GC_01(String senderName, String mobile, String email) throws InterruptedException {
        logger.info("Executing TC_GC_01: Invalid sender email test.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.scrollDown();
        giftCardPage.fillSenderDetails(senderName, mobile, email);
        giftCardPage.clickSubmit();

        String error = giftCardPage.getErrorMessage();
        logger.error("Sender Email Error: {}", error);
        
        Assert.assertEquals(error.trim(), "Please enter a valid Email id.", "Expected sender email error not shown.");
        logger.info("TC_GC_01 completed.");
    }

    @Test(dataProvider = "GiftCardTest2", dataProviderClass = DataProviders.class, priority = 1, description = "TestCase 2: Fill multiple recipients and capture error")
    public void TC_GC_02(String sender1, String mob1, String email1, String sender2, String mob2, String email2, String sender3, String mob3, String email3) {
        logger.info("Executing TC_GC_02: Multiple recipients error test.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();

        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.toggleSwitch();
        giftCardPage.scrollDown();

        giftCardPage.fillRecipientDetails(1, sender1, mob1, email1);
        giftCardPage.fillRecipientDetails(2, sender2, mob2, email2);
        giftCardPage.fillRecipientDetails(3, sender3, mob3, email3);
        giftCardPage.clickSubmit();

        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid/multiple recipient details.");
//        logger.info("TC_GC_02 completed.");
    }

    @Test(dataProvider = "GiftCardTest3", dataProviderClass = DataProviders.class, priority = 2, description = "TestCase 3: Fill recipient form and capture error")
    public void TC_GC_03(String sender, String mob, String email, String msg) {
        logger.info("Executing TC_GC_03: Recipient form error test.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.scrollDown();
        giftCardPage.fillRecipientForm(sender, mob, email, msg);
        giftCardPage.clickSubmit();

        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
//        logger.info("TC_GC_03 completed.");
    }
}
