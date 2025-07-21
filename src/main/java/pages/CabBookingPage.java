package pages;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BasePage;
 
public class CabBookingPage extends BasePage {
	
	public CabBookingPage(WebDriver driver) {
		super(driver);
	}
	
	/* Page Objects */
	
	//Button to disable the Login pop-up
	@FindBy(xpath="//span[@class='commonModal__close']") WebElement closePopUpBtn;
	@FindBy(xpath="//a[contains(@href,'/cabs/')]") WebElement cabsSection;
	@FindBy(xpath="//li[text()='Outstation One-Way']") WebElement tripTypeButton;
	@FindBy(xpath="//label[@for='fromCity']") WebElement fromCityField;
	@FindBy(xpath="//input[@placeholder='From']") WebElement fromLocationInput;
	@FindBy(xpath="//ul[@role='listbox']/li[@role='option']") List<WebElement> suggestions;
	@FindBy(xpath="//ul[@role='listbox']/li[@role='option']") WebElement suggestion;
	@FindBy(xpath="//label[@for='toCity']") WebElement toCityField;
	@FindBy(xpath="//input[@placeholder='To']") WebElement toLocationInput;
	@FindBy(xpath="//label[@for='departure']") WebElement departureDateField;
	@FindBy(xpath="//span[@aria-label='Next Month']") WebElement nextMonthButton;
	@FindBy(xpath="//label[@for='pickupTime']") WebElement pickUpTimeField;
	@FindBy(className="hrSlotItemParent") List<WebElement> hrDropdown;
	@FindBy(className="minSlotItemParent") List<WebElement> minDropdown;
	@FindBy(className="meridianSlotItemParent") List<WebElement> meridianDropdown;
	@FindBy(xpath="//div[@class='applyBtn']") WebElement applyTimeButton;
	@FindBy(xpath="//p[contains(@data-cy, 'Search')]/a") WebElement searchButton;
	@FindBy(xpath="//img[@alt='Close'") WebElement suggestedPackagePopup;
	@FindBy(xpath="//span[text()='SUV']") WebElement cabTypeFilter;
	@FindBy(xpath="//span[contains(@class, 'cabDetailsCard') and contains(text(), '₹')]") List<WebElement> baseAndOtherCharges;
	@FindBy(xpath="//span[text()='CLEAR ALL']") WebElement clearAllButton;
	@FindBy(xpath="//div[contains(@data-testid,'CAB_CARD')]") List<WebElement> cabs;
	@FindBy(xpath = "//div[@class='checkbox_container__lcPRN']//span[@class='filterSection_title__vHRpx' and text()='SUV']") WebElement SUVCheckbox;		//Added
	@FindBy(xpath = "//div[@class='cabDetailsCard_cabDetailsContainer__x_vxw ']") List<WebElement> searchResultCards;
	@FindBy(xpath = "//div[@id='react-autowhatever-1']/div//li//span") List<WebElement> listOfPopularCitiesFromSuggestions;
	@FindBy(xpath = "//div[@class='DayPicker-Caption']/div") WebElement monthYearElement;		//Added
	@FindBy(xpath = "//div[@class='DayPicker-Day']") List<WebElement> datesListElements;
	@FindBy(xpath="//div[contains(@class, 'cabDetailsCard_utilitiesInfo')]") List<WebElement> listOfCabs;
	@FindBy(id="from_location") WebElement fromLocationResult;
	@FindBy(id="to_location") WebElement toLocationResult;
	@FindBy(id="pickup_date") WebElement pickupDateResult;
	@FindBy(id="pickup_time") WebElement pickupTimeResult;
	@FindBy(xpath="//div[@role='gridcell']") List<WebElement> dates;
	@FindBy(xpath="//p[contains(@class,'cabNotFound')]") WebElement cabNotFound;
	
	By errorMessageSameOrigin = By.xpath("//span[@class='redText errorMsgText']");
	
	
	/* Action methods */
	
	public void disableLoginPopUp() {
		scrollClick(closePopUpBtn);
	}

	public void selectSection() {
		cabsSection.click();		
	}
	
	public void selectTripType() {
		tripTypeButton.click();
	}
	
	public void clickFromField() {
        fromCityField.click();
	}

	public void enterFromLocation(String location) {
		fromLocationInput.sendKeys(location);
		waitForSuggestionToLoad(location);
	}

	public void selectCityToPopularSuggestion(String city) {
		wait.until(ExpectedConditions.visibilityOfAllElements(listOfPopularCitiesFromSuggestions));
        for(WebElement citySuggestion:listOfPopularCitiesFromSuggestions) {
        	String text = citySuggestion.getText();
        	if(text.equalsIgnoreCase(city)) {
        		citySuggestion.click();
        		break;
        	}
        }
	}
	
	public void clickToField() {
		try {
	        toCityField.click();			
		} catch(Exception e) {}
	}
	
	public void enterToLocation(String location) {
		toLocationInput.sendKeys(location);
		waitForSuggestionToLoad(location);
	}
	
	public void waitForSuggestionToLoad(String destination) {
        By updatedSuggestionLocator = By.xpath("//li[@role='option']"); //locator for 1st suggestion
        try {
            // 1. Wait until the element with the specified locator contains the desired text
            wait.until(ExpectedConditions.textToBePresentInElementLocated(updatedSuggestionLocator, destination));        	
        } catch (Exception e) {
        }
	}
	
	public String getSuggestion() {
		return suggestion.getText();
	}
	
	public void selectLocationFromSuggestions(String location) {
		for(WebElement ele : suggestions) {
			if(ele.getText().contains(location)) {
				ele.click();
				return;
			}
		}
	}
	
	public void selectDestinationFromSuggestion(String destination) {
        By manaliSuggestionLocator = By.xpath("//li[@role='option']"); //locator for 1st suggestion
        // 1. Wait until the element with the specified locator contains the desired text
        wait.until(ExpectedConditions.textToBePresentInElementLocated(manaliSuggestionLocator, destination));
        // click 1st suggestion
        driver.findElement(manaliSuggestionLocator).click();
	}
	
	public void clickDepartureDate(){	
        // departure date field
        scrollClick(departureDateField);;
	}
	
	public List<WebElement> getDates(){
		return dates;
	}
	
	public void clickNextMonth() {
		nextMonthButton.click();
	}
	
	public void clickPickupTime() {
        // click pickUp-Time
        pickUpTimeField.click();	
	}
	
	public void search() {
        // Click Search
        searchButton.click();	
	}
	
	public void closePackagesPopup() {
        // close popup
        try {
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Close']"))).click();
        	driver.findElement(By.xpath("//img[@alt='Close']")).click();
        } 
        catch (Exception e) {
        }	
	}
	
	public void selectCarType(String carType) {
        // Click required car type
        driver.findElement(By.xpath("//div[@role='checkbox']//span[text()='"+carType+"']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(clearAllButton));	
	}

	public List<WebElement> getListOfCabs(){
		return listOfCabs;
	}
	
	public void clearAll() {
		clearAllButton.click();
	}
	
	public boolean checkClearAllFilters() {
        try {
        	driver.findElement(By.xpath("//div[contains(@class,'checkbox_checked')]"));
        	return false;
        } catch(Exception e) {
        	return true;
        }
	}
	

	public WebElement getSUVFilter() {
		return SUVCheckbox;
	}
	
	public List<WebElement> getSearchResult(){
		return searchResultCards;
	}
	
	public String getFromLocationResult() {
		return fromLocationResult.getAttribute("value");
	}
	
	public String getToLocationResult() {
		return toLocationResult.getAttribute("value");
	}
	
	public String getPickupDateResult() {
		return pickupDateResult.getAttribute("value");
	}
	
	public String getPickupTimeResult() {
		return pickupTimeResult.getAttribute("value");
	}
	
	public String getErrorMessageForSameOrigin() {
		WebElement errorMsg = wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageSameOrigin));
		return errorMsg.getText();
	}
	
	public String getCabNotFoundErrorMessage() {
		return cabNotFound.getText();
	}
	
    public void scrollClick(WebElement ele){
        try{
            jsUtil.jsClick(ele);;
        } catch (ElementClickInterceptedException e){
        	jsUtil.scrollToElement(ele);
            jsUtil.jsClick(ele);
        }
    }

    public void datePicker(String date){
//    	if (isDateSelected(date)) {
//    		action.clickEsc();
//    		return;
//    	}
    	
//    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        while(true){
            try{
                scrollClick(driver.findElement(By.xpath("//div[contains(@aria-label, '"+date+"')]")));
                break;
            } catch (Exception e) {
                try{
                clickNextMonth();
                } catch (Exception ex) {
                    System.out.println("Reached end of booking availability. Cannot book for " + date);
                    break;
                }
            }
        }
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    
//    public boolean isDateSelected(String requiredDate) {
//    	String selectedDate = driver.findElement(By.xpath("//span[contains(@class,'selectedDateField')]")).getText();
//    	return compareDate(selectedDate, "d MMM yy", requiredDate, "MMM dd yyyy");
//    }

    public boolean compareDate(String date1, String format1, String date2, String format2) {
	    try {
	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(format1);
	        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(format2);

            LocalDate d1 = LocalDate.parse(date1, formatter1);
            LocalDate d2 = LocalDate.parse(date2, formatter2);
            return d1.equals(d2);

	    } catch (Exception e) {
	        System.out.println("Error comparing values: " + e.getMessage());
	        return false;
	    }
    }

    public boolean compareTime(String time1, String format1, String time2, String format2) {
	    try {
	        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(format1);
	        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(format2);

            LocalTime t1 = LocalTime.parse(time1.toLowerCase(), formatter1);
            LocalTime t2 = LocalTime.parse(time2.toLowerCase(), formatter2);
            return t1.equals(t2);

	    } catch (Exception e) {
	        System.out.println("Error comparing values: " + e.getMessage());
	        return false;
	    }
    }
    
//    public boolean isTimeSelected(String requiredTime) {
//    	String hour = driver.findElement(By.className("selectedItemHighlight")).getText();
//    	String minute = driver.findElement(By.className("selectedItemDefault")).getText();
//    	String period = driver.findElements(By.className("selectedItemDefault")).get(1).getText();
//
//    	String selectedTime = hour + ":" + minute + " " + period;
//    	return compareTime(selectedTime, "hh:mm a", requiredTime, "h:mm a");
//    }

	public void selectDepartureDate(String departDate) {
		clickDepartureDate();
		
		String month = departDate.split(" ")[0];
		boolean dateSelected = false;
		while(!dateSelected) {
			if(monthYearElement.getText().contains(month)) {
				for(WebElement date: datesListElements) {
					if(date.getAttribute("aria-label").contains(departDate)) {
						try {
							if(!date.isSelected()) {
								scrollClick(date);
								dateSelected = true;
								break;
							}else {
								break;
							}	
						}catch(Exception e) {}	
					}
				}
			}else {
				try {
					clickNextMonth();;
				}catch (Exception e) {
				}
			}
		}
	}

    public void timePicker(String timeString){
//    	if (isTimeSelected(timeString)) {
//    		action.clickEsc();
//    		return;
//    	}
    	
    	timeString = timeString.replace(":", " ");
        String time [] = timeString.split(" ");
        int hr = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        String mer = time[2];
        // select hours
        // if 12hrs then select 1st element i.e. element from dropdown list at index 0
        if(hr==12){
            scrollClick(hrDropdown.get(0));
        }
        // else nth element i.e. element from dropdown list at index n, for e.g. 3rd element is at index 0 in dropdown list
        else{
            scrollClick(hrDropdown.get(hr));
        }

        // We have time only in multiples of 5, [0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55]
        min = min/5; // we find index by dividing min/5, for e.g. 33/5 = 6 so element at 6th index is 30
        scrollClick(minDropdown.get(min));

        // If meridian is 'am' then click 0th element which is 'AM'
        if(mer.equalsIgnoreCase("am")){
            scrollClick(meridianDropdown.get(0));
        }
        // If meridian is 'pm' then click 1st element which is 'PM'
        else if (mer.equalsIgnoreCase("pm")) {
            scrollClick(meridianDropdown.get(1));
        }

        //click apply
        applyTimeButton.click();
    }

    public WebElement findLowestCharges(){
        int min = Integer.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i< baseAndOtherCharges.size(); i+=2){
            String baseCharges = baseAndOtherCharges.get(i).getText().replaceAll("[^0-9]", "");
            String additionalCharges = baseAndOtherCharges.get(i+1).getText().replaceAll("[^0-9]", "");
            int totalPrice = Integer.parseInt(baseCharges) + Integer.parseInt(additionalCharges);
            if(totalPrice < min){
                min = totalPrice;
                idx = i/2;
            }
        }
        if(idx<0){
            return null;
        }
        return cabs.get(idx);
    }

	public String takeElementScreenshot(WebElement ele, String name) {
		jsUtil.scrollToElement(ele);
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + name +"\\"+ timeStamp + ".png";
		
        File sourceFile = ele.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(targetFilePath); // Use a variable for filename

        try {
            FileUtils.copyFile(sourceFile, targetFile);

        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	return targetFilePath;
        }
    }
}
 
 