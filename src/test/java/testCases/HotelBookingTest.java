package testCases;
 
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
 
import java.util.stream.IntStream;
 
import base.BaseTest;
import com.aventstack.extentreports.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class HotelBookingTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(HotelBookingTest.class);
    private ExtentTest test;

    @Test(priority = 1)
    public void TC_HB_01() {
        test = extent.createTest("TC_HB_01: Verify adult dropdown count range");
        logger.info("Executing TC_HB_01");
 
        hotelObj.clickOnHotelsTab();
        hotelObj.clickOnRoomsAndGuestsOption();
        hotelObj.clickOnAdultsDropdown();
 
        int[] countsArr = IntStream.rangeClosed(1, 40).toArray();
        int i = 0;
        boolean correctCount = true;
 
        for (int num : hotelObj.getListOfCountsInAdultDropdown()) {
            if (num != countsArr[i]) {
                correctCount = false;
            }
            i++;
        }
 
        action.clickEsc();
        Assert.assertTrue(correctCount, "Incorrect count of numbers in dropdown");
        test.pass("Adult dropdown contains correct range from 1 to 40.");
    }
 
    @Test(priority = 2)
    public void TC_HB_02() {
        test = extent.createTest("TC_HB_02: Verify clicking on specific adult counts");
        logger.info("Executing TC_HB_02");
 
        hotelObj.clickOnHotelsTab();
        hotelObj.clickOnRoomsAndGuestsOption();
 
        WebElement count = null;
        boolean clicked = true;
 
        for (int i = 0; i < 3; i++) {
            hotelObj.clickOnAdultsDropdown();
            for (WebElement countEle : hotelObj.getListOfCountElement()) {
                String text = countEle.getText().trim();
                if (text.equals("03") || text.equals("05") || text.equals("40")) {
                    count = countEle;
                }
            }
            try {
                count.click();
            } catch (Exception e) {
                logger.error("Failed to click on count element", e);
                clicked = false;
            }
        }
 
        action.clickEsc();
        Assert.assertTrue(clicked);
        test.pass("Successfully clicked on adult counts: 03, 05, 40.");
    }
 
    @Test(priority = 3)
    public void TC_HB_03() {
        test = extent.createTest("TC_HB_03: Verify RoomsAndGuests selector is enabled");
        logger.info("Executing TC_HB_03");
 
        hotelObj.clickOnHotelsTab();
        hotelObj.clickOnRoomsAndGuestsOption();
 
        action.clickEsc();
        Assert.assertTrue(hotelObj.isRoomsAndGuestsSelectorEnabled(), "RoomsAndGuests selector option is not present");
        test.pass("RoomsAndGuests selector is enabled and visible.");
    }
 
    @Test(priority = 4)
    public void TC_HB_04() {
        test = extent.createTest("TC_HB_04: Verify default value of adult dropdown");
        logger.info("Executing TC_HB_04");
 
        hotelObj.clickOnHotelsTab();
        hotelObj.clickOnRoomsAndGuestsOption();
        String value = hotelObj.getDefaultValueOfAdultDropdown();
 
        action.clickEsc();
        Assert.assertEquals(value, "2");
        test.pass("Default value of adult dropdown is 2.");
    }
 
    @Test(priority = 5)
    public void TC_HB_05() {
        test = extent.createTest("TC_HB_05: Verify selected value is reflected after applying");
        logger.info("Executing TC_HB_05");
 
        hotelObj.clickOnHotelsTab();
        hotelObj.clickOnRoomsAndGuestsOption();
        hotelObj.clickOnAdultsDropdown();
 
        for (WebElement countEle : hotelObj.getListOfCountElement()) {
            String text = countEle.getText();
            if (text.equals("04")) {
                countEle.click();
                break;
            }
        }
 
        hotelObj.clickApplyButton();
        String text = hotelObj.getRoomsAndGuestsDispalyedText();
 
        action.clickEsc();
        Assert.assertEquals(text, "1 Rooms 4Adults");
        test.pass("Displayed text after selection is correct: '1 Rooms 4Adults'.");
    }
}
 
 