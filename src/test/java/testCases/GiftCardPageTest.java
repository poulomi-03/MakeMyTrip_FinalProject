package testCases;
 
import base.BaseTest;
import utils.DataProviders;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.*;
 
public class GiftCardPageTest extends BaseTest {
    private ExtentTest test;
    
    @Test(dataProvider = "GiftCardTest1", dataProviderClass = DataProviders.class, priority = 3, description = "TestCase 1: Invalid sender email")
    public void TC_GC_01(String senderName, String mobile, String email) throws InterruptedException {
	    test = extent.createTest("TC_GC_01: Invalid sender email");
        logger.info("Executing TC_GC_01: Invalid sender email test.");
        try {
        	driver.get("https://www.makemytrip.com/gift-cards/");
            giftCardPage.openBestWishesGiftCardInNewTab();
            jsUtil.scrollBy(500);
            giftCardPage.fillSenderDetails(senderName, mobile, email);
            giftCardPage.clickSubmit();
     
            String error = giftCardPage.getErrorMessage();
            logger.error("Sender Email Error: {}", error);
            test.info("Captured error message: " + error);
     
            Assert.assertEquals(error.trim(), "Please enter a valid Email id.", "Expected sender email error not shown.");
            test.pass("Validation message matched expected error.");
        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        	test.fail("Validation message did not matched expected error.");
        }
        logger.info("TC_GC_01 completed.");
    }

    @Test(dataProvider = "GiftCardTest2", dataProviderClass = DataProviders.class, priority = 1, description = "TestCase 2: Fill multiple recipients and capture error")
    public void TC_GC_02(String sender1, String mob1, String email1, String sender2, String mob2, String email2, String sender3, String mob3, String email3) {
		test = extent.createTest("TC_GC_02: Multiple recipients error");
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
            test.pass("Form did not submit with invalid/multiple recipient details.");
        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        	test.fail("Form submitted with invalid/multiple recipient details.");
        }
        logger.info("TC_GC_02 completed.");
    }

    @Test(dataProvider = "GiftCardTest3", dataProviderClass = DataProviders.class, priority = 2, description = "TestCase 3: Fill recipient form and capture error")
    public void TC_GC_03(String sender, String mob, String email, String msg) {
		test = extent.createTest("TC_GC_03: Recipient form error");
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
            test.pass("Form did not submit with invalid recipient form.");
        }catch(Exception e) {
        	Assert.fail(e.getMessage());
        	test.fail("Form got submitted with invalid recipient form.");
        }
        logger.info("TC_GC_03 completed.");
    }
}
 
 