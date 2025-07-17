package testCases;
 
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.DataProviders;
 
public class CabBookingPageTest extends BaseTest{
		
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 1, description ="To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.")
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

	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 2, description ="To verify that only SUV cab type filter is applied and results are displayed accordingly.")
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
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 3, description ="To verify that the selected \"From\" location is reflected in search results.")
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
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 4, description ="To verify that the selected \"To\" location is reflected in search results.")
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
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 5, description ="To verify that the selected \"Departure Date\" is reflected in search results.")
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
	
	public boolean compareTimes(String uiTime, String excelTime) {
	    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("h:mm a");
	    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("hh:mm a");

	    try {
	        LocalTime timeFromExcel = LocalTime.parse(excelTime.trim(), inputFormat);
	        String formattedExcelTime = timeFromExcel.format(outputFormat);

	        return formattedExcelTime.equalsIgnoreCase(uiTime.trim());
	    } catch (DateTimeParseException e) {
	        System.out.println("Invalid time format: " + e.getMessage());
	        return false;
	    }
	}
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 6, description ="To verify that the selected \"Pickup Time\" is reflected in search results.")
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
        
        String actualPickUpTime = driver.findElement(By.id("pickup_time")).getAttribute("value").toLowerCase();
    	Assert.assertTrue(compareTimes(actualPickUpTime, time));
	}
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 7, description ="To verify Clear All functionality of filters.")
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
	
	@Test(priority = 8, description ="To verify Suggestion when an invalid/unserviceable \"From\" location is entered.")
	public void TC_OCB_08() {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.enterFromLocation(randomString());
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);

		action.clickEsc();
	}
 
	@Test(priority = 9, description ="To verify Suggestion when an invalid/unserviceable \"To\" location is entered.")
	public void TC_OCB_09() {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(randomString());
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);

		action.clickEsc();
	}

	@Test(priority = -1, description ="To verify that previous dates are disabled in the departure date picker.")
	public void TC_OCB_10() {
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
	
	@Test(priority = 11, description ="To verify that dates beyond  limit in the future are disabled in the departure date picker.")
	public void TC_OCB_11() {
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
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 12, description ="To verify the presence of SUV filter.")
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
		boolean isPresent = false;
		if(cabBookingPage.getSUVFilter().isDisplayed()) {
			isPresent = true;
		}
		
		Assert.assertTrue(isPresent);
	}
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 13, description ="To verify that search results are displayed even if \"SUV\" filter is not applied initially.")
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
	
	@Test(dataProvider = "NoCabsData", dataProviderClass = DataProviders.class,priority = -1, description ="To verify behavior when no cabs are available for the selected criteria.")
	public void TC_OCB_14(String fromLocation, String toLocation, String date, String time) {
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.enterFromLocation(fromLocation);
		cabBookingPage.selectDestinationFromSuggestion(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation(toLocation);
		cabBookingPage.selectDestinationFromSuggestion(toLocation);
		cabBookingPage.selectDepartureDate(date);
        cabBookingPage.clickPickupTime();
        cabBookingPage.timePicker(time);
		cabBookingPage.search();
		cabBookingPage.closePackagesPopup();
		
		Assert.assertTrue(cabBookingPage.getSearchResult().isEmpty());
	}
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 15, description ="To verify behavior with same \"From\" and \"To\" locations.")
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
 