package testCases;
 
import base.BaseTest;
import utils.DataProviders;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
 
public class GiftCardPageTest extends BaseTest {
    
    @Test(groups= {"Smoke"}, dataProvider = "GiftCardTest1", dataProviderClass = DataProviders.class, priority = 1, description = "Invalid sender email")
    public void TC_GC_01(String senderName, String mobile, String email, ITestContext result) throws InterruptedException {
        logger.info("Executing TC_GC_01: Invalid sender email test.");
        try {
        	driver.get("https://www.makemytrip.com/gift-cards/");
            giftCardPage.openBestWishesGiftCardInNewTab();
            jsUtil.scrollBy(500);
            giftCardPage.fillSenderDetails(senderName, mobile, email);
            giftCardPage.clickSubmit();
     
            String error = giftCardPage.getErrorMessage();
            logger.error("Sender Email Error: {}", error);
            Assert.assertEquals(error.trim(), "Please enter a valid Email id.", "Expected sender email error not shown.");
            result.setAttribute("message", "Validation message matched expected error.");

        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        }
        logger.info("TC_GC_01 completed.");
    }

    @Test(groups= {"Regression"}, dataProvider = "GiftCardTest2", dataProviderClass = DataProviders.class, priority = 2, description = "Fill multiple recipients and capture error")
    public void TC_GC_02(String sender1, String mob1, String email1, String sender2, String mob2, String email2, String sender3, String mob3, String email3, ITestContext result) {
        logger.info("Executing TC_GC_02: Multiple recipients error test.");
        try {
        	driver.get("https://www.makemytrip.com/gift-cards/");
            giftCardPage.openBestWishesGiftCardInNewTab();
            giftCardPage.selectEmailDelivery();
            giftCardPage.clickCountryCodeSelector();
            giftCardPage.toggleSwitch();
            
            jsUtil.scrollBy(500);

            giftCardPage.fillRecipientDetails(1, sender1, mob1, email1);
            giftCardPage.fillRecipientDetails(2, sender2, mob2, email2);
            giftCardPage.fillRecipientDetails(3, sender3, mob3, email3);
            giftCardPage.clickSubmit();
     
            boolean formStillVisible = giftCardPage.isSenderFormVisible();
            Assert.assertTrue(formStillVisible, "Form should not submit with invalid/multiple recipient details.");
            result.setAttribute("message", "Validation message matched expected error.");
        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        }
        logger.info("TC_GC_02 completed.");
    }

    @Test(groups= {"Regression"}, dataProvider = "GiftCardTest3", dataProviderClass = DataProviders.class, priority = 3, description = "Fill recipient form and capture error")
    public void TC_GC_03(String sender, String mob, String email, String msg, ITestContext result) {
        logger.info("Executing TC_GC_03: Recipient form error test.");
        try {
        	driver.get("https://www.makemytrip.com/gift-cards/");
            giftCardPage.openBestWishesGiftCardInNewTab();
            giftCardPage.selectEmailDelivery();
            giftCardPage.clickCountryCodeSelector();
            jsUtil.scrollBy(500);
            giftCardPage.fillRecipientForm(sender, mob, email, msg);
            giftCardPage.clickSubmit();
     
            boolean formStillVisible = giftCardPage.isSenderFormVisible();
            Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
            result.setAttribute("message", "Form did not submit with invalid recipient form.");
        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        }
        logger.info("TC_GC_03 completed.");
    }
}
 
 