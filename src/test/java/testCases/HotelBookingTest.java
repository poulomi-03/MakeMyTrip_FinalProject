package testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.stream.IntStream;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HotelBookingPage;


public class HotelBookingTest extends BaseTest{
	HotelBookingPage hotelObj;
	@Test(priority = 1)
	public void TC_HB_01() {
		hotelObj = new HotelBookingPage(driver);
//		hotelObj.disableLoginPopUp();
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
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();
		assertTrue(correctCount, "Incorrect count of numbers in dropdown");
	}
	
	@Test(priority = 2)
	public void TC_HB_02() {
		hotelObj = new HotelBookingPage(driver);
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
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();
		assertTrue(clicked);
	}
	
	@Test(priority = 3)
	public void TC_HB_03() {
		hotelObj.clickOnHotelsTab();
		hotelObj.clickOnRoomsAndGuestsOption();
		
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();
		assertTrue(hotelObj.isRoomsAndGuestsSelectorEnabled(), "RoomsAndGuests selector option is not present");
	}
	
	@Test(priority = 4)
	public void TC_HB_04() {
		hotelObj.clickOnHotelsTab();
		hotelObj.clickOnRoomsAndGuestsOption();
		String value = hotelObj.getDefaultValueOfAdultDropdown();
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();
		assertEquals(value, "2");
	}
	
	@Test(priority = 5)
	public void TC_HB_05() {
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
		assertEquals(hotelObj.getRoomsAndGuestsDispalyedText(), "1 Rooms 4Adults");
	}
}
