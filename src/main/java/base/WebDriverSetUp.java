package base;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
public class WebDriverSetUp {
public static WebDriver driver;
	public static WebDriver setupDriver(String browser) {
    	try {
			switch(browser.toLowerCase()) {
			case "chrome": 
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default: System.out.println("Invalid browser name");
			}
    	}
    	catch(Exception e){
    		System.out.println("Remarks: Faied to open "+browser+" browser.\n"+e);
    	}
		return driver;
	}
}