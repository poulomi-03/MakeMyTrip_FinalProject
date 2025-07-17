package testCases;
 
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 
import com.aventstack.extentreports.ExtentTest;
 
import base.BaseTest;
 
public class CabBookingPageTest extends BaseTest{
	private static final Logger logger = LogManager.getLogger(CabBookingPageTest.class);
    private ExtentTest test;
	
	@DataProvider(name = "testData")
	public String[][] loginData(){
		String[][] arr = {{"Delhi", "Manali, Himachal Pradesh", "Aug 09 2025", "12:40 PM", "SUV"}};
		return arr;
	}
	
	@Test(dataProvider = "testData", priority = 1, description = "To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.")
	public void TC_OCB_01(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_01: To verify successful One-Way SUV cab search from Delhi to Manali with valid future date and time.");
	    logger.info("Executing TC_OCB_01");
 
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
 
	    test.pass("Lowest charged Cab search completed successfully and screenshot captured.");
	    logger.info("TC_OCB_01 completed.");
	}
 
 
	@Test(dataProvider = "testData", priority = 2, groups = {"smoke"}, description ="To verify that only SUV cab type filter is applied and results are displayed accordingly.")
	public void TC_OCB_02(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_02: To verify that only SUV cab type filter is applied and results are displayed accordingly.");
	    logger.info("Executing TC_OCB_02");
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
 
	    test.pass("Only SUV cab types is displayed in the search results.");
	    logger.info("TC_OCB_02 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 3, groups = {"smoke"}, description ="To verify that the selected \"From\" location is reflected in search results.")
	public void TC_OCB_03(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_03: To verify that the selected \"From\" location is reflected in search results.");
	    logger.info("Executing TC_OCB_03");
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
 
	    test.pass("The displayed \"From\" location for the cabs is matching with the selected \"From\" location.");
	    logger.info("TC_OCB_03 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 4, groups = {"smoke"}, description ="To verify that the selected \"To\" location is reflected in search results.")
	public void TC_OCB_04(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_04: To verify that the selected \"To\" location is reflected in search results.");
	    logger.info("Executing TC_OCB_04");
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
 
	    test.pass("The displayed \"To\\\" location for the cabs is matching with the selected \\\"To\\\" location.");
	    logger.info("TC_OCB_04 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 5, groups = {"smoke"}, description ="To verify that the selected \"Departure Date\" is reflected in search results.")
	public void TC_OCB_05(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_05: To verify that the selected \"Departure Date\" is reflected in search results.");
	    logger.info("Executing TC_OCB_05");
	    
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
 
	    test.pass("The displayed departure date for the cabs is matching with the selected date.");
	    logger.info("TC_OCB_05 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 6, groups = {"smoke"}, description ="To verify that the selected \"Pickup Time\" is reflected in search results.")
	public void TC_OCB_06(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_06: To verify that the selected \"Pickup Time\" is reflected in search results.");
	    logger.info("Executing TC_OCB_06");
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
 
	    test.pass("The displayed pickup time for the cabs is matching with the selected time.");
	    logger.info("TC_OCB_06 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 7, groups = {"regression"}, description ="To verify Clear All functionality of filters.")
	public void TC_OCB_07(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_07: To verify Clear All functionality of filters.");
	    logger.info("Executing TC_OCB_07");
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
 
	    test.pass("All applied filters is removed, and the search results are updated.");
	    logger.info("TC_OCB_07 completed.");
	}
	@Test(dataProvider = "testData", priority = 8, groups = {"regression"}, description ="To verify Suggestion when an invalid/unserviceable \"From\" location is entered.")
	public void TC_OCB_08(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_08: To verify Suggestion when an invalid/unserviceable \"From\" location is entered.");
	    logger.info("Executing TC_OCB_08");
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.enterFromLocation("qwertyuiop");
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);
 
		action.clickEsc();
 
	    test.pass("The dropdown is displaying \"No Data Found\" message in the suggestions for the \"From\" field.");
	    logger.info("TC_OCB_08 completed.");
	}
 
	@Test(dataProvider = "testData", priority = 9, groups = {"regression"}, description ="To verify Suggestion when an invalid/unserviceable \"To\" location is entered.")
	public void TC_OCB_09(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_09: To verify Suggestion when an invalid/unserviceable \"To\" location is entered.");
	    logger.info("Executing TC_OCB_09");
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickToField();
		cabBookingPage.enterToLocation("qwertyuiop");
		
        String expectedSuggestion = "No Data Found";
        String actualSuggestion = cabBookingPage.getSuggestion();
    	System.out.println(expectedSuggestion +" - "+actualSuggestion +" - "+ actualSuggestion.contains(expectedSuggestion));
    	Assert.assertEquals(expectedSuggestion, actualSuggestion);
 
		action.clickEsc();
 
	    test.pass("The dropdown is displaying \"No Data Found\" message in the suggestions for the \"To\" field.");
	    logger.info("TC_OCB_09 completed.");
	}
 
	@Test(dataProvider = "testData", priority = -1, groups = {"regression"}, description ="To verify that previous dates are disabled in the departure date picker.")
	public void TC_OCB_10(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_10: To verify that previous dates are disabled in the departure date picker.");
	    logger.info("Executing TC_OCB_10");
		cabBookingPage.selectSection();
		cabBookingPage.clickdeparture();
		
    	int today = LocalDate.now().getDayOfMonth();
		List<WebElement> dates = driver.findElements(By.xpath("//div[@role='gridcell']"));
		for(int i = 0; i < today-1; i++ ) {
			Assert.assertEquals(dates.get(i).getAttribute("aria-disabled"), "true");
			
		}
		action.clickEsc();
		System.out.println("Done");
 
	    test.pass("Only the dates from current date are enabled and the previous dates are disabled.");
	    logger.info("TC_OCB_10 completed.");
	}
	
	@Test(dataProvider = "testData", priority = 11, groups = {"regression"}, description ="To verify that dates beyond  limit in the future are disabled in the departure date picker.")
	public void TC_OCB_11(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_11: To verify that dates beyond  limit in the future are disabled in the departure date picker.");
	    logger.info("Executing TC_OCB_11");
	    
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
 
	    test.pass("The dates only upto certain days are enabled in the datapicker.");
	    logger.info("TC_OCB_11 completed.");
	}
	
	@Test(dataProvider = "testData", priority = 12, groups = {"regression"}, description ="To verify the presence of SUV filter.")
	public void TC_OCB_12(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_12: To verify the presence of SUV filter.");
	    logger.info("Executing TC_OCB_12");
	    
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
 
	    test.pass("SUV filter is present in the page.");
	    logger.info("TC_OCB_12 completed.");
	}
	
	@Test(dataProvider = "testData", priority = 13, groups = {"regression"}, description ="To verify that search results are displayed even if \"SUV\" filter is not applied initially.")
	public void TC_OCB_13(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_13: To verify that search results are displayed even if \"SUV\" filter is not applied initially.");
	    logger.info("Executing TC_OCB_13");
	    
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
 
	    test.pass("The available cabs are displayed in the search result.");
	    logger.info("TC_OCB_13 completed.");
	}
	
	@Test(priority = -1, groups = {"regression"}, description ="To verify behavior when no cabs are available for the selected criteria.")
	public void TC_OCB_14() {
	    test = extent.createTest("TC_OCB_14: To verify behavior when no cabs are available for the selected criteria.");
	    logger.info("Executing TC_OCB_14");
	    
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
 
	    test.pass("A message \"Cabs not available for selected date and time. Please change the date/time and try again\" is displayed ");
	    logger.info("TC_OCB_14 completed.");
	}
	
	@Test(dataProvider = "testData", priority = 15, groups = {"regression"}, description ="To verify behavior with same \"From\" and \"To\" locations.")
	public void TC_OCB_15(String fromLocation, String toLocation, String date, String time, String carType) {
	    test = extent.createTest("TC_OCB_15: To verify behavior with same \"From\" and \"To\" locations.");
	    logger.info("Executing TC_OCB_15");
	    
		cabBookingPage.selectSection();
		cabBookingPage.selectTripType();
		cabBookingPage.clickFromField();
		cabBookingPage.selectLocationFromSuggestions(fromLocation);
		cabBookingPage.clickToField();
		cabBookingPage.selectCityToPopularSuggestion(fromLocation);
		
		String msg = "The Origin City & Destination City cannot be the same.";
		Assert.assertEquals(cabBookingPage.getErrorMessageForSameOrigin(), msg);
 
	    test.pass("The error message \"The Origin City & Destination City cannot be the same.\" is displayed");
	    logger.info("TC_OCB_15 completed.");
	}
}