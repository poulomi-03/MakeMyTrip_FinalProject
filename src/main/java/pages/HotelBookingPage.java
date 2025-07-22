package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

public class HotelBookingPage extends BasePage{
	
	public HotelBookingPage(WebDriver driver) {
		super(driver);
	}
	
	/* Page Objects */
	
	//Button to disable the Login pop-up
	@FindBy(xpath="//span[@class='commonModal__close']") WebElement disablePopUpBtn;
	@FindBy(xpath="//a[contains(@href,'/hotels/')]") WebElement hotelsTabSelector;
	@FindBy(id="guest") WebElement roomsAndGuestsOptionSelector;
	@FindBy(xpath = "//p[contains(text(), 'Adults')]/following::span[@class='gstSlct__text'][1]") WebElement adultsDropdownSelector;
	@FindBy(xpath = "//ul[@class='gstSlct__list']/li") List<WebElement> adultsCountList;
	@FindBy(xpath = "//button[normalize-space()='APPLY']") WebElement applyButtonRoomsAndGuests;
	@FindBy(xpath = "//span[@class='appendRight10']") WebElement roomsAndGuestsDisplayField;
	
	
	/* Action methods */
	
	public void disableLoginPopUp() {
		disablePopUpBtn.click();
	}
	
	public void clickOnHotelsTab() {
		hotelsTabSelector.click();
	}
	
	public WebElement getRoomsAndGuestsSelector() {
		return roomsAndGuestsOptionSelector;
	}
	public void clickOnRoomsAndGuestsOption() {
		roomsAndGuestsOptionSelector.click();
	}
	
	public void clickOnAdultsDropdown() {
		adultsDropdownSelector.click();
	}
	
	public List<WebElement> getListOfCountElement() {
		return adultsCountList;
	}
	
	public String getDefaultValueOfAdultDropdown() {
		return adultsDropdownSelector.getText();
	}
	public List<Integer> getListOfCountsInAdultDropdown() {
		List<Integer> listOfAdultCounts = new ArrayList<Integer>();
		for(WebElement countEle: adultsCountList) {
			listOfAdultCounts.add(Integer.parseInt(countEle.getText()));
		}
		
		return listOfAdultCounts;
	}
	
	public void clickApplyButton() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", applyButtonRoomsAndGuests);
	}
	
	public String getRoomsAndGuestsDispalyedText() {
		if(roomsAndGuestsDisplayField.isDisplayed()) {
			return roomsAndGuestsDisplayField.getText();
		}else {
			System.out.println("Rooms and Guests details is not displayed");
			return null;
		}
	}
}
