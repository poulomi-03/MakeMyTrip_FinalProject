package utils;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionUtil {
	private WebDriver driver;
	Actions act;
	
	public ActionUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
	}
	public void clickEsc() {		
		act.sendKeys(Keys.ESCAPE).perform();
	}
}
