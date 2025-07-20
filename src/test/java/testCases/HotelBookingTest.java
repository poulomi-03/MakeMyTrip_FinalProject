package testCases;
 
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
 
import java.util.stream.IntStream;
 
import base.BaseTest;
import com.aventstack.extentreports.*;
 
public class HotelBookingTest extends BaseTest{
	/*
	 * To check "Adult" dropdown from the "Rooms and Guests" field contains list of numbers.
	 */
	@Test(groups= {"Smoke"}, priority = 1, description = "Verify adult dropdown count range")
	public void TC_HB_01(ITestContext result) {
        logger.info("Executing TC_HB_01");
        
		try {
			hotelObj.clickOnHotelsTab();
			hotelObj.clickOnRoomsAndGuestsOption();
			hotelObj.clickOnAdultsDropdown();
			
			int[] countsArr = IntStream.rangeClosed(1,40).toArray();
			
			int i=0;
			boolean correctCount = true;
			for(int num : hotelObj.getListOfCountsInAdultDropdown()) {
				if(num != countsArr[i]) {
					correctCount = false;
				}
				i++;
			}
			action.clickEsc();
			Assert.assertTrue(correctCount, "Adult dropdown does not contain correct range from 1 to 40.");
			result.setAttribute("message", "Adult dropdown contains correct range from 1 to 40.");
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("TC_HB_01 Completed");
	}
	
	/*
	 * To check the ability to select different adult numbers from the dropdown.
	 */
	@Test(groups= {"Regression"}, priority = 2, description = "Verify clicking on specific adult counts")
	public void TC_HB_02(ITestContext result) {
        logger.info("Executing TC_HB_02");
        
		try {
			hotelObj.clickOnHotelsTab();
			hotelObj.clickOnRoomsAndGuestsOption();
			
			WebElement count = null;
			boolean clicked = true;
			for(int i = 0; i < 3; i++) {
				hotelObj.clickOnAdultsDropdown();
				for(WebElement countEle : hotelObj.getListOfCountElement()) {
					String text = countEle.getText().trim();
					if(text.equals("03")) {
						count=countEle;
					}else if(countEle.getText().equals("05")) {
						count=countEle;
					}else if(countEle.getText().equals("40")) {
						count=countEle;
					}
				}
				try {
					count.click();
				}catch(Exception e) {
					e.printStackTrace();
					clicked = false;
				}
			}
			action.clickEsc();
			Assert.assertTrue(clicked,"Can't click on adult counts: 03, 05, 40.");
			result.setAttribute("message", "Successfully clicked on adult counts: 03, 05, 40.");
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("TC_HB_02 Completed");
	}
	
	/*
	 * To check that "Rooms and Guests" selector is present on the Hotel booking page.
	 */
	@Test(groups= {"Smoke"}, priority = 3, description = "Verify RoomsAndGuests selector is enabled")
	public void TC_HB_03(ITestContext result) {
        logger.info("Executing TC_HB_03");
        
		try {
			hotelObj.clickOnHotelsTab();
			hotelObj.clickOnRoomsAndGuestsOption();
			boolean isPresent = false;
			if(hotelObj.getRoomsAndGuestsSelector().isEnabled()) {
				isPresent = true;
			}
			action.clickEsc();
			Assert.assertTrue(isPresent, "RoomsAndGuests selector option is not present");
			result.setAttribute("message", "RoomsAndGuests selector is enabled and visible.");
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("TC_HB_03 Completed");
	}
	
	/*
	 * To verify initial default value of adult numbers in the dropdown.
	 */
	@Test(groups= {"Regression"}, priority = 4, description = "Verify default value of adult dropdown")
	public void TC_HB_04(ITestContext result) { 
        logger.info("Executing TC_HB_04");
        
		try {
			hotelObj.clickOnHotelsTab();
			hotelObj.clickOnRoomsAndGuestsOption();
			String value = hotelObj.getDefaultValueOfAdultDropdown();
			action.clickEsc();
			Assert.assertEquals(value, "2", "Default value of adult dropdown is not 2.");
			result.setAttribute("message", "Default value of adult dropdown is 2.");
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("TC_HB_04 Completed");
	}
	
	/*
	 * To check that the selected number of Adults are displayed in the "Rooms and Guests" selector field.
	 */
	@Test(groups= {"Regression"}, priority = 5, description = "Verify selected value is reflected after applying")
	public void TC_HB_05(ITestContext result) {
        logger.info("Executing TC_HB_05");
        
		try {
			hotelObj.clickOnHotelsTab();
			hotelObj.clickOnRoomsAndGuestsOption();
			hotelObj.clickOnAdultsDropdown();
			
			for(WebElement countEle:hotelObj.getListOfCountElement()) {
				String text = countEle.getText();
				if(text.equals("04")) {
					countEle.click();
					break;
				}
			}
			hotelObj.clickApplyButton();
			String text = hotelObj.getRoomsAndGuestsDispalyedText();
			action.clickEsc();
			Assert.assertEquals(text, "1 Rooms 4Adults", "Displayed text after selection is incorrect.");
			result.setAttribute("message", "Displayed text after selection is correct: '1 Rooms 4Adults'.");
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
		logger.info("TC_HB_05 Completed");
	}
}
 
 