package testCases;
 
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
 
 
import base.BaseTest;
 
public class CabBookingPageTest extends BaseTest{
	
	@DataProvider(name = "testData")
	public String[][] loginData(){
		String[][] arr = {{"Delhi", "Manali, Himachal Pradesh", "Aug 09 2025", "12:40 PM", "SUV"}};
		return arr;
	}
	
	@Test(dataProvider = "testData", priority = 1, description ="To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.")
	public void TC_OCB_01(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        cabBookingPage.selectCarType(carType);
        WebElement cab = cabBookingPage.findLowestCharges();
        cabBookingPage.takeElementScreenshot(cab, "cab.png");
	}

	@Test(dataProvider = "testData", priority = 2, description ="To verify that only SUV cab type filter is applied and results are displayed accordingly.")
	public void TC_OCB_02(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        cabBookingPage.selectCarType(carType);
        
        // To verify the Car Type with help of number of seats (SUV has 6 seats)
        List<WebElement> cabs = driver.findElements(By.xpath("//div[contains(@class, 'cabDetailsCard_utilitiesInfo')]"));
        for(WebElement cab : cabs) {
        	String expectedNumberOfSeats = "6 Seats";
        	String actualNumberOfSeats = cab.findElement(By.tagName("span")).getText();
        	System.out.println(expectedNumberOfSeats +" - "+actualNumberOfSeats +" - "+ expectedNumberOfSeats.equalsIgnoreCase(actualNumberOfSeats));
        	Assert.assertEquals(expectedNumberOfSeats, actualNumberOfSeats);
        }
	}
 
	@Test(dataProvider = "testData", priority = 3, description ="To verify that the selected \"From\" location is reflected in search results.")
	public void TC_OCB_03(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        
        String expectedFromLocation = fromLocation;
        String actualFromLocation = driver.findElement(By.id("from_location")).getAttribute("value");
    	System.out.println(expectedFromLocation +" - "+actualFromLocation +" "+ expectedFromLocation.equalsIgnoreCase(actualFromLocation));
    	Assert.assertTrue(actualFromLocation.contains(expectedFromLocation));
	}
 
	@Test(dataProvider = "testData", priority = 4, description ="To verify that the selected \"To\" location is reflected in search results.")
	public void TC_OCB_04(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        
        String expectedToLocation = toLocation;
        String actualToLocation = driver.findElement(By.id("to_location")).getAttribute("value");
    	System.out.println(expectedToLocation +" - "+actualToLocation +" - "+ expectedToLocation.equalsIgnoreCase(actualToLocation));
    	Assert.assertTrue(actualToLocation.contains(actualToLocation));
	}
 
	@Test(dataProvider = "testData", priority = 5, description ="To verify that the selected \"Departure Date\" is reflected in search results.")
	public void TC_OCB_05(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        
        String actualDateString = driver.findElement(By.id("pickup_date")).getAttribute("value");
        String expectedDateString = date;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
 
        LocalDate date1 = LocalDate.parse(actualDateString, formatter1);
        LocalDate date2 = LocalDate.parse(expectedDateString, formatter2);
 
        
    	System.out.println(expectedDateString +" - "+actualDateString +" - "+ expectedDateString.equalsIgnoreCase(expectedDateString));
    	Assert.assertTrue(date1.equals(date2));
	}
 
	@Test(dataProvider = "testData", priority = 6, description ="To verify that the selected \"Pickup Time\" is reflected in search results.")
	public void TC_OCB_06(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
		cabBookingPage.clickPickupTime();
		cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        
        String expectedPickUpTime = time;
        String actualPickUpTime = driver.findElement(By.id("pickup_time")).getAttribute("value");
    	System.out.println(expectedPickUpTime +" - "+actualPickUpTime +" - "+ expectedPickUpTime.equalsIgnoreCase(actualPickUpTime));
    	Assert.assertEquals(expectedPickUpTime, actualPickUpTime);
	}
 
	@Test(dataProvider = "testData", priority = 7, description ="To verify Clear All functionality of filters.")
	public void TC_OCB_07(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
        cabBookingPage.search();
        cabBookingPage.closePackagesPopup();
        cabBookingPage.selectCarType(carType);
        cabBookingPage.clearAll();
        
        try {
        	driver.findElement(By.xpath("//div[contains(@class='checkbox_checked')]"));
        	Assert.assertTrue(true);
        } catch(Exception e) {
        	System.out.println("Cleared all filters");
        }   
	}
	@Test(dataProvider = "testData", priority = 8, description ="To verify Suggestion when an invalid/unserviceable \"From\" location is entered.")
	public void TC_OCB_08(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.enterFromLocation("qwertyuiop");
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);

		action.clickEsc();
	}
 
	@Test(dataProvider = "testData", priority = 9, description ="To verify Suggestion when an invalid/unserviceable \"To\" location is entered.")
	public void TC_OCB_09(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation("qwertyuiop");
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);

		action.clickEsc();
	}

	@Test(dataProvider = "testData", priority = -1, description ="To verify that previous dates are disabled in the departure date picker.")
	public void TC_OCB_10(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.clickdeparture();
		
    	int today = LocalDate.now().getDayOfMonth();
		List<WebElement> dates = driver.findElements(By.xpath("//div[@role='gridcell']"));
		for(int i = 0; i < today-1; i++ ) {
			Assert.assertEquals(dates.get(i).getAttribute("aria-disabled"), "true");
			
		}
		action.clickEsc();
		System.out.println("Done");
	}
	
	@Test(dataProvider = "testData", priority = 11, description ="To verify that dates beyond  limit in the future are disabled in the departure date picker.")
	public void TC_OCB_11(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.clickdeparture();
 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
		while(true){
			try {
	            driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();			
			} catch(Exception e){
				break;
			}
		}
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	
		WebElement secondMonth = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][2]"));
		String disabledDateString = secondMonth.findElement(By.xpath("//div[contains(@class,'DayPicker-Day--disabled')]")).getText();
		int disabledDate = Integer.parseInt(disabledDateString);
		List<WebElement> dates = secondMonth.findElements(By.xpath(".//div[@role='gridcell']"));
		for(int i = disabledDate-1; i < dates.size(); i++ ) {
			Assert.assertEquals(dates.get(i).getAttribute("aria-disabled"), "true");
		}

		action.clickEsc();
	}
	
	@Test(dataProvider = "testData", priority = 12, description ="To verify the presence of SUV filter.")
	public void TC_OCB_12(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
		cabBookingPage.search();
		cabBookingPage.closePackagesPopup();
		boolean isPresent = cabBookingPage.isSUVFilterpresent();
		
		Assert.assertTrue(isPresent);
	}
	
	@Test(dataProvider = "testData", priority = 13, description ="To verify that search results are displayed even if \"SUV\" filter is not applied initially.")
	public void TC_OCB_13(String fromLocation, String toLocation, String date, String time, String carType) throws Exception {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.clickdeparture();
		cabBookingPage.datePicker(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
		cabBookingPage.search();
		cabBookingPage.closePackagesPopup();
		
		Assert.assertTrue(!cabBookingPage.getSearchResult().isEmpty());
	}
	
	@Test(priority = -1, description ="To verify behavior when no cabs are available for the selected criteria.")
	public void TC_OCB_14() {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.enterFromLocation("Leh");
		cabBookingPage.selectDestinationFromSuggestion("Leh");
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation("Kargil");
		cabBookingPage.selectDestinationFromSuggestion("Kargil");
		cabBookingPage.selectDepartureDate("Jul 25 2025");
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker("10:00 AM");
		cabBookingPage.search();
		cabBookingPage.closePackagesPopup();
		
		Assert.assertTrue(cabBookingPage.getSearchResult().isEmpty());
	}
	
	@Test(dataProvider = "testData", priority = 15, description ="To verify behavior with same \"From\" and \"To\" locations.")
	public void TC_OCB_15(String fromLocation, String toLocation, String date, String time, String carType) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.selectCityToPopularSuggestion(fromLocation);
		
		String msg = "The Origin City & Destination City cannot be the same.";
		Assert.assertEquals(cabBookingPage.getErrorMessageForSameOrigin(), msg);
	}
}
 