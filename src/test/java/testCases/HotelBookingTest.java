package testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.stream.IntStream;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.BaseTest;

public class HotelBookingTest extends BaseTest{
	
	@Test
	public void TC_HB_01() {
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
		assertTrue(correctCount, "Incorrect count of numbers in dropdown");
	}
	
	@Test
	public void TC_HB_02() {
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
		assertTrue(clicked);
	}
	
	@Test
	public void TC_HB_03() {
		hotelObj.clickOnHotelsTab();
		hotelObj.clickOnRoomsAndGuestsOption();
		
		action.clickEsc();
		assertTrue(hotelObj.isRoomsAndGuestsSelectorEnabled(), "RoomsAndGuests selector option is not present");
	}
	
	@Test
	public void TC_HB_04() {
		hotelObj.clickOnHotelsTab();
		hotelObj.clickOnRoomsAndGuestsOption();
		String value = hotelObj.getDefaultValueOfAdultDropdown();
		action.clickEsc();
		assertEquals(value, "2");
	}
	
	@Test
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
		action.clickEsc();
		assertEquals(hotelObj.getRoomsAndGuestsDispalyedText(), "1 Rooms 4Adults");
	}
}
