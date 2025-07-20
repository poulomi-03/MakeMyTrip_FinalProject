package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.JavascriptExecutorUtil;

public class BasePage {
	
	public WebDriver driver;
	
	public JavascriptExecutorUtil jsUtil;
	public WebDriverWait wait;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		
		jsUtil = new JavascriptExecutorUtil(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		PageFactory.initElements(driver, this);
	}
}
