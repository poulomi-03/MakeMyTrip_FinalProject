package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptExecutorUtil {
	JavascriptExecutor js;
	
	public JavascriptExecutorUtil(WebDriver driver) {
		js = (JavascriptExecutor) driver;
	}
	
	/*
	 * Click the specified WebElement
	 * @param element WebElement to be clicked
	 */
	public void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}
	
	/*
	 * Scroll the window until the specified WebElement is visible
	 * @param element WebElement to be viewed after scrolling
	 */
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollToView;", element);
	}
	
	 /*
     * Scrolls the window vertically by the specified number of pixels.
     * @param pixels Number of pixels to scroll by (positive = down, negative = up)
     */
	public void scrollBy(int pixels) {
		js.executeScript("window.scrollBy(0, arguments[0]);", pixels);
	}
	
	// Scroll to the bottom of the page
	public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

	}
}
