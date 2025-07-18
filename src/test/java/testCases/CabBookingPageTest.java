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

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import utils.DataProviders;
 
public class CabBookingPageTest extends BaseTest{
    private ExtentTest test;	
		
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 1, description ="To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.")
	public void TC_OCB_01(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_01: To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.");
	    logger.info("Executing TC_OCB_01");
	    
	    try {
	    	cabBookingPage.selectSection();
		    cabBookingPage.selectTripType();
		    cabBookingPage.clickFromField();
		    cabBookingPage.selectLocationFromSuggestions(fromLocation);
		    cabBookingPage.clickToField();
		    cabBookingPage.enterToLocation(toLocation);
		    cabBookingPage.selectDestinationFromSuggestion(toLocation);
		    cabBookingPage.clickDepartureDate();
		    cabBookingPage.datePicker(date);
		    cabBookingPage.clickPickupTime();
		    cabBookingPage.timePicker(time);
		    cabBookingPage.search();
		    cabBookingPage.closePackagesPopup();
		    cabBookingPage.selectCarType(carType);
	 
		    WebElement cab = cabBookingPage.findLowestCharges();
		    cabBookingPage.takeElementScreenshot(cab, "cab.png");
		    
		    Assert.assertTrue(true);
		    test.pass("Lowest charged Cab search completed successfully and screenshot captured.");
		    
	    }catch(Exception e){
	    	Assert.fail(e.getMessage());
	    	test.fail("Lowest charged Cab search failed");
	    }
	    logger.info("TC_OCB_01 completed");
	}

	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 2, description ="To verify that only SUV cab type filter is applied and results are displayed accordingly.")
	public void TC_OCB_02(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_02: To verify that only SUV cab type filter is applied and results are displayed accordingly.");
	    logger.info("Executing TC_OCB_02");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        cabBookingPage.selectCarType(carType);
	        
	        // To verify the Car Type with help of number of seats (SUV has 6 seats)
	        List<WebElement> cabs = cabBookingPage.getListOfCabs();
	        for(WebElement cab : cabs) {
	        	String expectedNumberOfSeats = "6 Seats";
	        	String actualNumberOfSeats = cab.findElement(By.tagName("span")).getText();
	        	System.out.println(expectedNumberOfSeats +" - "+actualNumberOfSeats +" - "+ expectedNumberOfSeats.equalsIgnoreCase(actualNumberOfSeats));
	        	Assert.assertEquals(expectedNumberOfSeats, actualNumberOfSeats);
	        }
	 
		    test.pass("Only SUV cab type is displayed in the search results.");
		    

	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("Only SUV cab type is not displayed in the search results.");
	    }
	    logger.info("TC_OCB_02 completed.");
	}

	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 3, description ="To verify that the selected \"From\" location is reflected in search results.")
	public void TC_OCB_03(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_03: To verify that the selected \"From\" location is reflected in search results.");
	    logger.info("Executing TC_OCB_03");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        
	        String expectedFromLocation = fromLocation;
	        String actualFromLocation = cabBookingPage.getFromLocationResult();
	    	System.out.println(expectedFromLocation +" - "+actualFromLocation +" "+ expectedFromLocation.equalsIgnoreCase(actualFromLocation));
	    	Assert.assertTrue(actualFromLocation.contains(expectedFromLocation));
	 
		    test.pass("The displayed \"From\" location for the cabs is matching with the selected \"From\" location.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The displayed \\\"From\\\" location for the cabs is not matching with the selected \\\"From\\\" location.");
	    }
	    logger.info("TC_OCB_03 completed.");
	}
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 4, description ="To verify that the selected \"To\" location is reflected in search results.")
	public void TC_OCB_04(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_04: To verify that the selected \"To\" location is reflected in search results.");
	    logger.info("Executing TC_OCB_04");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        
	        String expectedToLocation = toLocation;
	        String actualToLocation = cabBookingPage.getToLocationResult();
	    	System.out.println(expectedToLocation +" - "+actualToLocation +" - "+ expectedToLocation.equalsIgnoreCase(actualToLocation));
	    	Assert.assertTrue(actualToLocation.contains(actualToLocation));
	 
		    test.pass("The displayed \"To\" location for the cabs is matching with the selected \"To\" location.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The displayed \"To\" location for the cabs is not matching with the selected \"To\" location.");
	    }
	    logger.info("TC_OCB_04 completed.");
	}
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 5, description ="To verify that the selected \"Departure Date\" is reflected in search results.")
	public void TC_OCB_05(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_05: To verify that the selected \"Departure Date\" is reflected in search results.");
	    logger.info("Executing TC_OCB_05");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        
	        String actualDateString = cabBookingPage.getPickupDateResult();
	        String expectedDateString = date;
	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy", Locale.ENGLISH);
	        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
	 
	        LocalDate date1 = LocalDate.parse(actualDateString, formatter1);
	        LocalDate date2 = LocalDate.parse(expectedDateString, formatter2);
	 
	    	System.out.println(expectedDateString +" - "+actualDateString +" - "+ expectedDateString.equalsIgnoreCase(expectedDateString));
	    	Assert.assertTrue(date1.equals(date2));
	 
		    test.pass("The displayed departure date for the cabs is matching with the selected date.");
		    
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The displayed departure date for the cabs is not matching with the selected date.");
	    }
	    logger.info("TC_OCB_05 completed.");
	}
	 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 6, description ="To verify that the selected \"Pickup Time\" is reflected in search results.")
	public void TC_OCB_06(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_06: To verify that the selected \"Pickup Time\" is reflected in search results.");
	    logger.info("Executing TC_OCB_06");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
			cabBookingPage.clickPickupTime();
			cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        
	        String expectedPickUpTime = time;
	        String actualPickUpTime = cabBookingPage.getPickupTimeResult();
	        Assert.assertTrue(cabBookingPage.compareTime(actualPickUpTime, "hh:mm a", expectedPickUpTime, "h:mm a"));
	 
		    test.pass("The displayed pickup time for the cabs is matching with the selected time.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The displayed pickup time for the cabs is not matching with the selected time.");
	    }
	    logger.info("TC_OCB_06 completed.");
	}
 
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 7, description ="To verify Clear All functionality of filters.")
	public void TC_OCB_07(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_07: To verify Clear All functionality of filters.");
	    logger.info("Executing TC_OCB_07");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
	        cabBookingPage.search();
	        cabBookingPage.closePackagesPopup();
	        cabBookingPage.selectCarType(carType);
	        cabBookingPage.clearAll();
	        
	        Assert.assertTrue(cabBookingPage.checkClearAllFilters());

		    test.pass("All applied filters is removed, and the search results are updated.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("All applied filters is removed.");
	    }
		
	    logger.info("TC_OCB_07 completed.");
	}
	
	@Test(priority = 8, description ="To verify Suggestion when an invalid/unserviceable \"From\" location is entered.")
	public void TC_OCB_08() {
	    test = extent.createTest("TC_OCB_08: To verify Suggestion when an invalid/unserviceable \"From\" location is entered.");
	    logger.info("Executing TC_OCB_08");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.enterFromLocation(randomString());
			
	        String expectedSuggestion = "No Data Found";
	        String actualSuggestion = cabBookingPage.getSuggestion();
	    	action.clickEsc();
	    	
	    	Assert.assertEquals(expectedSuggestion, actualSuggestion);
	 
		    test.pass("The dropdown is displaying \"No Data Found\" message in the suggestions for the \"From\" field.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The dropdown is not displaying \"No Data Found\" message in the suggestions for the \"From\" field.");
	    }
		
	    logger.info("TC_OCB_08 completed.");
	}
 
	@Test(priority = 9, description ="To verify Suggestion when an invalid/unserviceable \"To\" location is entered.")
	public void TC_OCB_09() {
	    test = extent.createTest("TC_OCB_09: To verify Suggestion when an invalid/unserviceable \"To\" location is entered.");
	    logger.info("Executing TC_OCB_09");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(randomString());
			
	        String expectedSuggestion = "No Data Found";
	        String actualSuggestion = cabBookingPage.getSuggestion();
	    	action.clickEsc();
	    	
	    	Assert.assertEquals(expectedSuggestion, actualSuggestion);
	 
		    test.pass("The dropdown is displaying \"No Data Found\" message in the suggestions for the \"To\" field.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The dropdown is not displaying \"No Data Found\" message in the suggestions for the \"To\" field.");
	    }
		
	    logger.info("TC_OCB_09 completed.");
	}

	@Test(priority = -1, description ="To verify that previous dates are disabled in the departure date picker.")
	public void TC_OCB_10() {
	    test = extent.createTest("TC_OCB_10: To verify that previous dates are disabled in the departure date picker.");
	    logger.info("Executing TC_OCB_10");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.clickDepartureDate();
			
	    	int today = LocalDate.now().getDayOfMonth();
			List<WebElement> dates = cabBookingPage.getDates();
			for(int i = 0; i < today-1; i++ ) {
				Assert.assertEquals(dates.get(i).getAttribute("aria-disabled"), "true");
				
			}
			action.clickEsc();
	 
		    test.pass("Only the dates from current date are enabled and the previous dates are disabled.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The previous dates are not disabled.");
	    }
		
	    logger.info("TC_OCB_10 completed.");
	}
	
	@Test(priority = 11, description ="To verify that dates beyond  limit in the future are disabled in the departure date picker.")
	public void TC_OCB_11() {
	    test = extent.createTest("TC_OCB_11: To verify that dates beyond  limit in the future are disabled in the departure date picker.");
	    logger.info("Executing TC_OCB_11");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.clickDepartureDate();
	 
			while(true){
				try {
		            cabBookingPage.clickNextMonth();			
				} catch(Exception e){
					break;
				}
			}
		
			WebElement secondMonth = driver.findElement(By.xpath("//div[@class='DayPicker-Month'][2]"));
			String disabledDateString = secondMonth.findElement(By.xpath("//div[contains(@class,'DayPicker-Day--disabled')]")).getText();
			int disabledDate = Integer.parseInt(disabledDateString);
			List<WebElement> dates = secondMonth.findElements(By.xpath(".//div[@role='gridcell']"));
			for(int i = disabledDate-1; i < dates.size(); i++ ) {
				Assert.assertEquals(dates.get(i).getAttribute("aria-disabled"), "true");
			}

			action.clickEsc();
	 
		    test.pass("The dates only upto certain days are enabled in the datapicker.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The dates does not have limit for selection.");
	    }
		
	    logger.info("TC_OCB_11 completed.");
	}
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 12, description ="To verify the presence of SUV filter.")
	public void TC_OCB_12(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_12: To verify the presence of SUV filter.");
	    logger.info("Executing TC_OCB_12");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
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
	 
		    test.pass("SUV filter is present in the page.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("SUV filter is not present in the page.");
	    }
		
	    logger.info("TC_OCB_12 completed.");
	}
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 13, description ="To verify that search results are displayed even if \"SUV\" filter is not applied initially.")
	public void TC_OCB_13(String fromLocation, String toLocation, String date, String time, String carType) throws Exception {
	    test = extent.createTest("TC_OCB_13: To verify that search results are displayed even if \"SUV\" filter is not applied initially.");
	    logger.info("Executing TC_OCB_13");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
			cabBookingPage.search();
			cabBookingPage.closePackagesPopup();
			
			Assert.assertTrue(!cabBookingPage.getSearchResult().isEmpty());
	 
		    test.pass("The available cabs are displayed in the search result.");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The available cabs are not displayed in the search result.");
	    }
		
	    logger.info("TC_OCB_13 completed.");
	}
	
	@Test(dataProvider = "NoCabsData", dataProviderClass = DataProviders.class,priority = -1, description ="To verify behavior when no cabs are available for the selected criteria.")
	public void TC_OCB_14(String fromLocation, String toLocation, String date, String time) {
	    test = extent.createTest("TC_OCB_14: To verify behavior when no cabs are available for the selected criteria.");
	    logger.info("Executing TC_OCB_14");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.enterFromLocation(fromLocation);
			cabBookingPage.selectDestinationFromSuggestion(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.enterToLocation(toLocation);
			cabBookingPage.selectDestinationFromSuggestion(toLocation);
			cabBookingPage.clickDepartureDate();
			cabBookingPage.datePicker(date);
	        cabBookingPage.clickPickupTime();
	        cabBookingPage.timePicker(time);
			cabBookingPage.search();
			cabBookingPage.closePackagesPopup();
			
			Assert.assertTrue(cabBookingPage.getSearchResult().isEmpty());
	 
		    test.pass("A message is displayed ");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("A message is not displayed ");
	    }
		
	    logger.info("TC_OCB_14 completed.");
	}
	
	@Test(dataProvider = "CabBookingValidData",dataProviderClass = DataProviders.class, priority = 15, description ="To verify behavior with same \"From\" and \"To\" locations.")
	public void TC_OCB_15(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_15: To verify behavior with same \"From\" and \"To\" locations.");
	    logger.info("Executing TC_OCB_15");
	    try {
	    	cabBookingPage.selectSection();
			cabBookingPage.selectTripType();
			cabBookingPage.clickFromField();
			cabBookingPage.selectLocationFromSuggestions(fromLocation);
			cabBookingPage.clickToField();
			cabBookingPage.selectCityToPopularSuggestion(fromLocation);
			
			String msg = "The Origin City & Destination City cannot be the same.";
			Assert.assertEquals(cabBookingPage.getErrorMessageForSameOrigin(), msg);
	 
		    test.pass("The error message is displayed");
	    }catch(Exception e) {
	    	Assert.fail(e.getMessage());
	    	test.fail("The error message is not displayed");
	    }
		
	    logger.info("TC_OCB_15 completed.");
	}
}
 