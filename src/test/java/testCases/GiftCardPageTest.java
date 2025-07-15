package testCases;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.GiftCardPage;

public class GiftCardPageTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(GiftCardPageTest.class);
    private GiftCardPage giftCardPage;

    @BeforeClass
    public void initGiftCardPage() {
        logger.info("Initializing GiftCardPage object.");
        giftCardPage = new GiftCardPage(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToGiftCardPage() {
        logger.info("Navigating to Gift Card page.");
        driver.get("https://www.makemytrip.com/gift-cards/");
        giftCardPage.openBestWishesGiftCardInNewTab();
        logger.debug("Opened Best Wishes Gift Card in a new tab.");
    }

    @Test(priority = 3, description = "TestCase 1: Invalid sender email")
    public void TC_GC_01() {
        logger.info("Executing TC_GC_01: Invalid sender email test.");
        giftCardPage.scrollDown();
        giftCardPage.fillSenderDetails("Sender Name", "9876543210", "invalid-email");
        giftCardPage.clickSubmit();

        String error = giftCardPage.getErrorMessage();
        logger.error("Sender Email Error: {}", error);

        Assert.assertEquals(error.trim(), "SENDER'S E-MAIL ID", "Expected sender email error not shown.");
        logger.info("TC_GC_01 completed.");
    }

    @Test(priority = 1, description = "TestCase 2: Fill multiple recipients and capture error")
    public void TC_GC_02() {
        logger.info("Executing TC_GC_02: Multiple recipients error test.");
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
            logger.warn("Form submission blocked due to multiple recipient error.");
        } else {
            logger.info("Form submitted successfully.");
        }

        Assert.assertTrue(formStillVisible, "Form should not submit with invalid/multiple recipient details.");
        logger.info("TC_GC_02 completed.");
    }

    @Test(priority = 2, description = "TestCase 3: Fill recipient form and capture error")
    public void TC_GC_03() {
        logger.info("Executing TC_GC_03: Recipient form error test.");
        giftCardPage.selectEmailDelivery();
        giftCardPage.clickCountryCodeSelector();
        giftCardPage.scrollDown();
        giftCardPage.fillRecipientForm("Alice", "4567891230", "234QWE122@GMAIL.COM", "Happy Birthday!");
        giftCardPage.clickSubmit();

        boolean formStillVisible = giftCardPage.isSenderFormVisible();
        if (formStillVisible) {
            logger.warn("Form submission blocked due to recipient form invalid email error.");
        } else {
            logger.info("Form submitted successfully.");
        }

        Assert.assertTrue(formStillVisible, "Form should not submit with invalid recipient form.");
        logger.info("TC_GC_03 completed.");
    }

    @AfterClass
    public void tearDownTest() {
        logger.info("Tearing down test.");
        tearDown();
    }
}
